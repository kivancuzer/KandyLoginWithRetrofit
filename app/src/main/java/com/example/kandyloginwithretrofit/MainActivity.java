package com.example.kandyloginwithretrofit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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

        getToken();

    }

    private void getToken() {
        String username="";
        String password="";
        String clientId="";
        User user = new User(username, password, "password", clientId, "password&");
        Call<Token> call = jsonPlaceHolderApi.getToken(user);

        call.enqueue(new Callback<Token>() {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {
                if(!response.isSuccessful()){
                    textViewResult.setText(response.code());
                    return;
                }
                Token token = response.body();

                String content = "";
                content += "Access Token: " + token.getAccess_token() + "\n";
                content += "Id Token: " + token.getId_token() + "\n";

                textViewResult.append(content);

            }

            @Override
            public void onFailure(Call<Token> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });




    }
}