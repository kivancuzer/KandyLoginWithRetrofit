package com.example.kandyloginwithretrofit;

import com.google.gson.annotations.SerializedName;

public class Token {
   public String access_token;
   public Integer expires_in;
   public Integer refresh_expires_in;
   public String refresh_token;
   public String token_type;
   public String id_token;

   @SerializedName("not-before-policy")
   public Integer not_before_policy;

   public String session_state;
   public String scope;

   public Token(String access_token, Integer expires_in, Integer refresh_expires_in, String refresh_token, String token_type, String id_token, Integer not_before_policy, String session_state, String scope) {
      this.access_token = access_token;
      this.expires_in = expires_in;
      this.refresh_expires_in = refresh_expires_in;
      this.refresh_token = refresh_token;
      this.token_type = token_type;
      this.id_token = id_token;
      this.not_before_policy = not_before_policy;
      this.session_state = session_state;
      this.scope = scope;
   }

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
