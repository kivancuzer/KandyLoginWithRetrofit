package com.example.kandyloginwithretrofit;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface JsonPlaceHolderApi {

    @POST("auth/v1/token")
    Call<Token> getToken(@Body User user);

}
