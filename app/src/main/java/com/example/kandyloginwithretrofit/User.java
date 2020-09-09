package com.example.kandyloginwithretrofit;

public class User {
    private String username;
    private String password;
    private String grant_type;
    private String client_id;
    private String scope;

    public User(String username, String password, String grant_type, String client_id, String scope) {
        this.username = username;
        this.password = password;
        this.grant_type = grant_type;
        this.client_id = client_id;
        this.scope = scope;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getGrant_type() {
        return grant_type;
    }

    public String getClient_id() {
        return client_id;
    }

    public String getScope() {
        return scope;
    }
}
