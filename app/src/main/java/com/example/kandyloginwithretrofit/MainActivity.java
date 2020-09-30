package com.example.kandyloginwithretrofit;

import android.content.Context;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;

import com.example.kandyloginwithretrofit.ui.main.SectionsPagerAdapter;
import com.rbbn.cpaas.mobile.CPaaS;
import com.rbbn.cpaas.mobile.addressbook.model.Contact;
import com.rbbn.cpaas.mobile.authentication.api.DisconnectionCallback;
import com.rbbn.cpaas.mobile.utilities.Globals;
import com.rbbn.cpaas.mobile.utilities.exception.MobileError;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static CPaaS cpaas;
    public static List<Contact> contactList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //CPaaS Manager
        cpaas = CPaaSManager.getCpaas(this);
        contactList = new ArrayList<>();
        //getAddressBookService();

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());

        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);

        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        Context context = getApplicationContext();
        Globals.setApplicationContext(context);


    }

    public void disconnect(View view) {
        disconnectToCpass();
    }

    /**
     * Disconnect to WebSocket
     */
    private void disconnectToCpass() {
        try {
            cpaas.getAuthentication().disconnect(new DisconnectionCallback() {
                @Override
                public void onSuccess() {
                    Log.i("CPass.Disconnection", "Disconnected to websocket successfully");
                    //Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    //startActivity(intent);
                }

                @Override
                public void onFail(MobileError mobileError) {
                    Log.i("CPass.Disconnection", "Disconnection to websocket failed");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}