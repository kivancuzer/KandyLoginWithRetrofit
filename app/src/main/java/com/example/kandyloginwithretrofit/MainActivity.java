package com.example.kandyloginwithretrofit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rbbn.cpaas.mobile.CPaaS;
import com.rbbn.cpaas.mobile.authentication.api.ConnectionCallback;
import com.rbbn.cpaas.mobile.utilities.exception.MobileError;
import com.rbbn.cpaas.mobile.utilities.exception.MobileException;
import com.rbbn.cpaas.mobile.utilities.services.ServiceInfo;
import com.rbbn.cpaas.mobile.utilities.services.ServiceType;

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
    private TextView textViewResult;
    private JsonPlaceHolderApi jsonPlaceHolderApi;
    public DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Gson gson = new GsonBuilder().serializeNulls().create();

        /**
         * Log
         */
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();

        /**
         * Retrofit
         */
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://oauth-cpaas.att.com/cpaas/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build();

        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        //getToken();
    }

    private void getToken() {
        String username = "";
        String password = "";
        String clientId = "";
        User user = new User(username, password, "password", clientId, "openid");
        Call<Token> call = jsonPlaceHolderApi.getToken(user);
        call.enqueue(new Callback<Token>() {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {
                if (response.code() != 200 || response.body() == null) {
                    textViewResult.setText("Response: " + response.code());
                    return;
                }
                Token token = response.body();
                databaseHelper.addToken(token);
                Log.i("Database Operations: ", databaseHelper.getStatus());
                String accessToken = token.getAccess_token();
                String idToken = token.getId_token();
                connectToCpaas(accessToken, idToken);
            }

            @Override
            public void onFailure(Call<Token> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });


    }

    private void connectToCpaas(String accessToken, String idToken) {

        int lifetime = 3600;
        List<ServiceInfo> services = new ArrayList<>();

        services.add(new ServiceInfo(ServiceType.SMS, true));
        services.add(new ServiceInfo(ServiceType.CALL, true));

        CPaaS cpaas = new CPaaS(services);

        try {
            cpaas.getAuthentication().connect(idToken, accessToken, lifetime, new ConnectionCallback() {
                @Override
                public void onSuccess(String connectionToken) {
                    Log.i("CPaaS.Authentication", "Connected to websocket successfully");
                }

                @Override
                public void onFail(MobileError error) {
                    Log.i("CPaaS.Authentication", "Connection to websocket failed");
                }
            });
        } catch (MobileException e) {
            e.printStackTrace();
        }

    }
}