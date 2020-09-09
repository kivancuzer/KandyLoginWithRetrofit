package com.example.kandyloginwithretrofit;

public class Token {
   private String access_token;
   private Integer expires_in;
   private Integer refresh_expires_in;
   private String refresh_token;
   private String token_type;
   private String id_token;
   private Integer not_before_policy;
   private String session_state;
   private String scope;

   public String getAccess_token() {
      return access_token;
   }

   public Integer getExpires_in() {
      return expires_in;
   }

   public Integer getRefresh_expires_in() {
      return refresh_expires_in;
   }

   public String getRefresh_token() {
      return refresh_token;
   }

   public String getToken_type() {
      return token_type;
   }

   public String getId_token() {
      return id_token;
   }

   public Integer getNot_before_policy() {
      return not_before_policy;
   }

   public String getSession_state() {
      return session_state;
   }

   public String getScope() {
      return scope;
   }
}
