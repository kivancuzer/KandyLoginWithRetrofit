package com.example.kandyloginwithretrofit;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface JsonPlaceHolderApi {

    @FormUrlEncoded
    @POST("auth/v1/token")
    Call<Token> getToken(
            @Field("username") String username,
            @Field("password") String password,
            @Field("client_id") String client_id,
            @Field("scope") String scope,
            @Field("grant_type") String grant_type
    );

}
