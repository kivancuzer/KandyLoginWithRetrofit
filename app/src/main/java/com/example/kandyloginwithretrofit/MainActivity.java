package com.example.kandyloginwithretrofit;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.kandyloginwithretrofit.ui.main.SectionsPagerAdapter;
import com.rbbn.cpaas.mobile.CPaaS;
import com.rbbn.cpaas.mobile.authentication.api.DisconnectionCallback;
import com.rbbn.cpaas.mobile.utilities.Globals;
import com.rbbn.cpaas.mobile.utilities.exception.MobileError;

public class MainActivity extends AppCompatActivity {

    private CPaaS cpaas;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.options_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.settings) {

        } else if (item.getItemId() == R.id.logout) {
            disconnectToCpass();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cpaas = CPaaSManager.getCpaas();

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());

        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);

        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        Context context = getApplicationContext();
        Globals.setApplicationContext(context);


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
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
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