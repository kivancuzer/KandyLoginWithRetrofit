package com.example.kandyloginwithretrofit;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "table_user")
public class User {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "username")
    private String username;

    @NonNull
    @ColumnInfo(name = "password")
    private String password;

    @NonNull
    @ColumnInfo(name = "grant_type")
    private String grant_type;

    @NonNull
    @ColumnInfo(name = "client_id")
    private String client_id;

    @NonNull
    @ColumnInfo(name = "scope")
    private String scope;

    /**
     * Create User with id
     *
     * When User created with this constructor id won't be generated automatically.
     *
     * @param id is which will be set for User in db.
     * @param username is which will be set for User.
     * @param password is which will be set for User.
     * @param grant_type is which will be set for User.
     * @param client_id is which will be set for User.
     * @param scope is which will be set for User.
     */
    public User(int id, @NonNull String username, @NonNull String password, @NonNull String grant_type, @NonNull String client_id, @NonNull String scope) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.grant_type = grant_type;
        this.client_id = client_id;
        this.scope = scope;
    }

    /**
     * Create User Without id
     *
     * When User created with this constructor id will be generated automatically.
     *
     * @param username is which will be set for User.
     * @param password is which will be set for User.
     * @param grant_type is which will be set for User.
     * @param client_id is which will be set for User.
     * @param scope is which will be set for User.
     */
    public User(String username, String password, String grant_type, String client_id, String scope) {
        this.username = username;
        this.password = password;
        this.grant_type = grant_type;
        this.client_id = client_id;
        this.scope = scope;
    }

    public int getId() {
        return id;
    }

    @NonNull
    public String getUsername() {
        return username;
    }

    @NonNull
    public String getPassword() {
        return password;
    }

    @NonNull
    public String getGrant_type() {
        return grant_type;
    }

    @NonNull
    public String getClient_id() {
        return client_id;
    }

    @NonNull
    public String getScope() {
        return scope;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(@NonNull String username) {
        this.username = username;
    }

    public void setPassword(@NonNull String password) {
        this.password = password;
    }

    public void setGrant_type(@NonNull String grant_type) {
        this.grant_type = grant_type;
    }

    public void setClient_id(@NonNull String client_id) {
        this.client_id = client_id;
    }

    public void setScope(@NonNull String scope) {
        this.scope = scope;
    }
}
