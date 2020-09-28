package com.example.kandyloginwithretrofit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;

public class MainActivity2 extends AppCompatActivity {

    FragmentManager fragmentManager = getSupportFragmentManager();
    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
    MessagesFragment messagesFragment = new MessagesFragment();
    ContactsFragment contactsFragment = new ContactsFragment();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
    }

    public void goToMessages(View view){

        //fragmentTransaction.add(R.id.frame_layout,messagesFragment).commit();
        fragmentTransaction.replace(R.id.frame_layout,messagesFragment).commit();
    }

    public void goToContacts(View view){
        fragmentTransaction.replace(R.id.frame_layout,contactsFragment).commit();
    }


}