package com.example.kandyloginwithretrofit.ui.main;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kandyloginwithretrofit.AddContactActivity;
import com.example.kandyloginwithretrofit.CPaaSManager;
import com.example.kandyloginwithretrofit.ContactRecyclerAdapter;
import com.example.kandyloginwithretrofit.MainActivity;
import com.example.kandyloginwithretrofit.R;
import com.rbbn.cpaas.mobile.CPaaS;
import com.rbbn.cpaas.mobile.addressbook.api.RetrieveContactsCallback;
import com.rbbn.cpaas.mobile.addressbook.model.Contact;
import com.rbbn.cpaas.mobile.utilities.exception.MobileError;

import java.util.ArrayList;
import java.util.List;

public class ContactsFragment extends Fragment {

    RecyclerView recyclerView;
    List<Contact> contactList;
    CPaaS cpaas;
    TextView txtAddNewContact;
    ImageView imgInfo;

    public static ContactsFragment newInstance() {
        return new ContactsFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cpaas = CPaaSManager.getCpaas();
        getAddressBookService();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_contacts, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        recyclerView = view.findViewById(R.id.recyclerViewContacts);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        txtAddNewContact = view.findViewById(R.id.txtAddNewContact);
        txtAddNewContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), AddContactActivity.class);
                startActivity(intent);
            }
        });
        super.onViewCreated(view, savedInstanceState);
    }

    /**
     * Get Contacts
     */
    public void getAddressBookService() {
        try {
            cpaas.getAddressBookService().retrieveContactList("default", new RetrieveContactsCallback() {
                @Override
                public void onSuccess(List<Contact> contacts) {
                    contactList = new ArrayList<>(contacts);
                    recyclerView.setAdapter(new ContactRecyclerAdapter(contactList));
                }

                @Override
                public void onFail(MobileError error) {
                    System.out.println("Address Book Failed! : " + error);

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}