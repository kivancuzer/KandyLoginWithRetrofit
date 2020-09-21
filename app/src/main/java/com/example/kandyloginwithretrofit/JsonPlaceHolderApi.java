package com.example.kandyloginwithretrofit;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface JsonPlaceHolderApi {

    /**
     * Post User
     *
     * @param user which will be need Token.
     * @return Response Token
     */
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @POST("auth/v1/token")
    Call<Token> getToken(@Body User user);

}
