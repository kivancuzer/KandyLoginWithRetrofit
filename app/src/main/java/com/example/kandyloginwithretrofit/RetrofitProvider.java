package com.example.kandyloginwithretrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitProvider {

    private static RetrofitProvider instance;
    private Retrofit retrofit;

    private RetrofitProvider() {
    }

    /**
     * Thread Sage Singleton
     *
     * @return instance of RetrofitProvider
     */
    public synchronized static RetrofitProvider getInstance() {
        if (instance == null) {
            instance = new RetrofitProvider();
        }
        return instance;
    }

    public Retrofit getRetrofit() {
        if (retrofit == null) {
            Gson gson = new GsonBuilder().serializeNulls().create();

            //Log
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(loggingInterceptor)
                    .build();

            //Retrofit
            retrofit = new Retrofit.Builder()
                    .baseUrl("https://nvs-cpaas-oauth.kandy.io/cpaas/")
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(okHttpClient)
                    .build();

        }
        return retrofit;
    }

}
