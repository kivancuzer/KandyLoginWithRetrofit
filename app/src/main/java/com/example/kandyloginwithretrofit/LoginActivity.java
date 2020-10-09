package com.example.kandyloginwithretrofit;

import androidx.appcompat.app.AppCompatActivity;

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
import com.rbbn.cpaas.mobile.utilities.exception.MobileError;
import com.rbbn.cpaas.mobile.utilities.exception.MobileException;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {
    private Button btnLogin;
    private EditText txtUsername;
    private EditText txtPassword;
    private JsonPlaceHolderApi jsonPlaceHolderApi;
    private KandyRoomDatabase kandyRoomDatabase;
    private CPaaS cpaas;
    private static String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //CPaaS
        cpaas = CPaaSManager.getCpaas();

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
                .baseUrl("https://nvs-cpaas-oauth.kandy.io/cpaas/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build();

        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        btnLogin = findViewById(R.id.btnLogin);
        txtUsername = findViewById(R.id.editTextUsername);
        txtPassword = findViewById(R.id.editTextPassword);

    }

    /**
     * Get Token Method
     * <p>
     * Account Information =>
     * username = "sahin1@cpaas.com";
     * password = "Kandy-1234";
     */
    public void getToken(String username, String password) {
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
                connectToCpaas(accessToken, idToken);
            }

            @Override
            public void onFailure(Call<Token> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });

    }

    /**
     * Connect To Websocket
     *
     * @param accessToken which is required for CPaaS
     * @param idToken     which is required for CPaaS
     */
    private void connectToCpaas(String accessToken, String idToken) {
        int lifetime = 600;

        try {
            cpaas.getAuthentication().setToken(accessToken);
            cpaas.getAuthentication().connect(idToken, lifetime, new ConnectionCallback() {
                public void onSuccess(String connectionToken) {
                    Log.i("CPaaS.Authentication", "Connected to websocket successfully");
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();

                }

                public void onFail(MobileError error) {
                    Log.i("CPaaS.Authentication", "Connection to websocket failed");
                }
            });
        } catch (MobileException e) {
            e.printStackTrace();
        }
    }

    public void login(View view) {
        if (txtUsername.getText().toString().isEmpty()) {
            Log.i(TAG, "Username is Empty");
        } else if (txtPassword.getText().toString().isEmpty()) {
            Log.i(TAG, "Password is Empty");
        } else {
            String username = txtUsername.getText().toString();
            String password = txtPassword.getText().toString();
            getToken(username, password);
        }
    }

    /**
     * Print Token for control
     *
     * @param token which token will be printed.
     */
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