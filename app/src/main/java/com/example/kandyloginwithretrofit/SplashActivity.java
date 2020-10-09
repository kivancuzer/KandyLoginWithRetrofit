package com.example.kandyloginwithretrofit;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rbbn.cpaas.mobile.CPaaS;
import com.rbbn.cpaas.mobile.authentication.api.ConnectionCallback;
import com.rbbn.cpaas.mobile.utilities.Configuration;
import com.rbbn.cpaas.mobile.utilities.exception.MobileError;
import com.rbbn.cpaas.mobile.utilities.exception.MobileException;
import com.rbbn.cpaas.mobile.utilities.webrtc.ICEServers;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

public class SplashActivity extends AppCompatActivity {
    private KandyRoomDatabase kandyRoomDatabase;
    private CPaaS cpaas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Configurations
        Configuration configuration = Configuration.getInstance();
        configuration.setUseSecureConnection(true);
        configuration.setRestServerUrl("nvs-cpaas-oauth.kandy.io");

        // Setting ICE Servers
        ICEServers iceServers = new ICEServers();
        iceServers.addICEServer("turns:turn-nds-1.genband.com:443?transport=tcp");
        iceServers.addICEServer("turn:turn-nds-1.genband.com:3478?transport=udp");
        configuration.setICEServers(iceServers);

        //CPaaS
        cpaas = CPaaSManager.getCpaas();

        kandyRoomDatabase = KandyRoomDatabase.getInstance(this);

        Gson gson = new GsonBuilder().serializeNulls().create();

        //Log
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();
        
        //Auto Login
        try {
            //Get Last Token
            Token token = kandyRoomDatabase.userDao().getLastToken();
            if(token == null){
                Intent intent = new Intent(SplashActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }else{
                printToken(token);
                //Connect to Cpass
                connectToCpaas(token.getAccess_token(), token.getId_token());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void connectToCpaas(String accessToken, String idToken) {
        int lifetime = 600;

        try {
            cpaas.getAuthentication().setToken(accessToken);
            cpaas.getAuthentication().connect(idToken, 600, new ConnectionCallback() {
                public void onSuccess(String connectionToken) {
                    Log.i("CPaaS.Authentication", "Connected to websocket successfully");
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }

                public void onFail(MobileError error) {
                    Log.i("CPaaS.Authentication", "Connection to websocket failed");
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
        } catch (MobileException e) {
            e.printStackTrace();
        }
    }

    /**
     * Print Token
     *
     * When checking the token
     *
     * @param token which will be printed.
     */
    public void printToken(Token token) {
        System.out.println("ID: " + token.getId());
        System.out.println("Access Token: " + token.getAccess_token());
        System.out.println("Expires In: " + token.getExpires_in());
        System.out.println("Refresh Token: " + token.getRefresh_token());
        System.out.println("Token Type: " + token.getToken_type());
        System.out.println("Id Token: " + token.getId_token());
        System.out.println("Not Before Policy: " + token.getNot_before_policy());
        System.out.println("Session State: " + token.getSession_state());
        System.out.println("Scope: " + token.getScope());
    }

}
