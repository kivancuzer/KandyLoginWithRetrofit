package com.example.kandyloginwithretrofit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rbbn.cpaas.mobile.CPaaS;
import com.rbbn.cpaas.mobile.authentication.api.ConnectionCallback;
import com.rbbn.cpaas.mobile.authentication.api.DisconnectionCallback;
import com.rbbn.cpaas.mobile.utilities.Configuration;
import com.rbbn.cpaas.mobile.utilities.Globals;
import com.rbbn.cpaas.mobile.utilities.exception.MobileError;
import com.rbbn.cpaas.mobile.utilities.exception.MobileException;
import com.rbbn.cpaas.mobile.utilities.services.ServiceInfo;
import com.rbbn.cpaas.mobile.utilities.services.ServiceType;
import com.rbbn.cpaas.mobile.utilities.webrtc.ICEServers;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private Button btnLogin;
    private EditText editTextUsername;
    private EditText editTextPassword;
    private JsonPlaceHolderApi jsonPlaceHolderApi;
    public KandyRoomDatabase kandyRoomDatabase;
    public CPaaS cpaas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Context context = getApplicationContext();
        Globals.setApplicationContext(context);

        //Configurations
        Configuration configuration = Configuration.getInstance();
        configuration.setUseSecureConnection(true);
        configuration.setRestServerUrl("nvs-cpaas-oauth.kandy.io");

        // Setting ICE Servers
        ICEServers iceServers = new ICEServers();
        iceServers.addICEServer("turns:turn-nds-1.genband.com:443?transport=tcp");
        iceServers.addICEServer("turn:turn-nds-1.genband.com:3478?transport=udp");
        configuration.setICEServers(iceServers);

        //Services
        List<ServiceInfo> services = new ArrayList<>();
        services.add(new ServiceInfo(ServiceType.SMS, true));
        services.add(new ServiceInfo(ServiceType.CALL, true));
        cpaas = new CPaaS(services);

        kandyRoomDatabase = KandyRoomDatabase.getInstance(this);

        Gson gson = new GsonBuilder().serializeNulls().create();

        //Log
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();

        //Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://nvs-cpaas-oauth.kandy.io/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build();

        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        btnLogin = findViewById(R.id.btnLogin);
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextUsername);

        //Get Last Token
        Token token = kandyRoomDatabase.userDao().getLastToken();
        printToken(token);
        //Connect to Cpass
        connectToCpaas(token.getAccess_token(), token.getId_token());
    }

    /**
     * Get Token Method
     */
    public void getToken() {
        String username = editTextUsername.getText().toString();
        String password = editTextPassword.getText().toString();
        //Account Information
        username = "sahin1@cpaas.com";
        password = "Kandy-1234";
        String clientId = "7ae2e26f-178a-4cac-9dcf-4cecd1bbadc6";

        User user = new User(username, password, "password", clientId, "openid");
        Call<Token> call = jsonPlaceHolderApi.getToken(user.getUsername(), user.getPassword(), user.getClient_id(), "openid", "password");
        call.enqueue(new Callback<Token>() {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {
                if (response.code() != 200 || response.body() == null) {
                    System.out.println("Response: " + response.code());
                    return;
                }
                Token token = response.body();
                kandyRoomDatabase.userDao().addToken(token);
                String accessToken = token.getAccess_token();
                String idToken = token.getId_token();

                printToken(token);

                //Connect to Cpass Method
                connectToCpaas(accessToken, idToken);
            }

            @Override
            public void onFailure(Call<Token> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });


    }

    private void connectToCpaas(String accessToken, String idToken) {

        int lifetime = 600;

        try {
            cpaas.getAuthentication().setToken(accessToken);
            cpaas.getAuthentication().connect(idToken, 600, new ConnectionCallback() {
                public void onSuccess(String connectionToken) {
                    Log.i("CPaaS.Authentication", "Connected to websocket successfully");
                    //Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                    //startActivity(intent);
                    //finish();
                }

                public void onFail(MobileError error) {
                    Log.i("CPaaS.Authentication", "Connection to websocket failed");
                }
            });
        } catch (MobileException e) {
            e.printStackTrace();
        }
    }

    /**
     * Disconnect
     */
    private void disconnectToCpass() {
        try {
            cpaas.getAuthentication().disconnect(new DisconnectionCallback() {
                @Override
                public void onSuccess() {
                    Log.i("CPass.Disconnection", "Disconnected to websocket successfully");
                }

                @Override
                public void onFail(MobileError mobileError) {
                    Log.i("CPass.Disconnection", "Disconnection to websocket failed");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Login With Last Token
     * <p>
     * When btnLoginWithLastToken clicked
     *
     * @param view
     */
    public void loginWithLastToken(View view) {
        //Get last token in db
        Token token = kandyRoomDatabase.userDao().getLastToken();
        System.out.println(token.getAccess_token());
        System.out.println(token.getId_token());

        //Control token
        if (token == null) {
            System.out.println("Last token couldn't find");
        } else {
            //Connect To Cpass
            connectToCpaas(kandyRoomDatabase.userDao().getLastToken().getAccess_token(),
                    kandyRoomDatabase.userDao().getLastToken().getId_token());
        }
    }

    public void login(View view) {
        getToken();
    }

    public void logout(View view) {
        disconnectToCpass();
    }

    public void printToken(Token token) {
        System.out.println("ID: " + token.getId());
        System.out.println("Access Token: " + token.getAccess_token());
        System.out.println("Expires In: " + token.getExpires_in());
        System.out.println("Refresh Token: " + token.getRefresh_token());
        System.out.println("Token Type: " + token.getToken_type());
        System.out.println("Id Token: " + token.getId_token());
        System.out.println("Not Before Policy: " + token.getNot_before_policy());
        System.out.println("Session State: " + token.getSession_state());
        System.out.println("Scope: " + token.getScope());
    }

}

