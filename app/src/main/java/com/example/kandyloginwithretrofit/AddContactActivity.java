package com.example.kandyloginwithretrofit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.rbbn.cpaas.mobile.CPaaS;
import com.rbbn.cpaas.mobile.addressbook.api.AddContactCallback;
import com.rbbn.cpaas.mobile.addressbook.api.UpdateContactCallback;
import com.rbbn.cpaas.mobile.addressbook.model.Contact;
import com.rbbn.cpaas.mobile.utilities.exception.MobileError;

public class AddContactActivity extends AppCompatActivity {

    CPaaS cpaas;
    TextView txtPrimaryContact;
    TextView txtFirstName;
    TextView txtLastName;
    TextView txtEmail;
    TextView txtBusinessPhone;
    TextView txtHomePhone;
    TextView txtMobilePhone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        cpaas = CPaaSManager.getCpaas();
        txtPrimaryContact = findViewById(R.id.txtPrimaryContact);
        txtFirstName = findViewById(R.id.txtFirstName);
        txtLastName = findViewById(R.id.txtLastName);
        txtEmail = findViewById(R.id.txtEmail);
        txtBusinessPhone = findViewById(R.id.txtBusinessPhone);
        txtHomePhone = findViewById(R.id.txtHomePhone);
        txtMobilePhone = findViewById(R.id.txtMobilePhone);
    }

    public void saveContact(View view) {
        addNewContact();
    }

    public void createContact() {
        Contact contact = new Contact();
        contact.setPrimaryContact(txtPrimaryContact.getText().toString());
        contact.setFirstName(txtFirstName.getText().toString());
        contact.setLastName(txtLastName.getText().toString());
        contact.setEmailAddress(txtEmail.getText().toString());
        contact.setBusinessPhoneNumber(txtBusinessPhone.getText().toString());
        contact.setHomePhoneNumber(txtHomePhone.getText().toString());
        contact.setMobilePhoneNumber(txtMobilePhone.getText().toString());
        contact.setBuddy(true);
    }

    public void addNewContact() {
        Contact contact = new Contact();
        contact.setPrimaryContact(txtPrimaryContact.getText().toString());
        contact.setFirstName(txtFirstName.getText().toString());
        contact.setLastName(txtLastName.getText().toString());
        contact.setEmailAddress(txtEmail.getText().toString());
        contact.setBusinessPhoneNumber(txtBusinessPhone.getText().toString());
        contact.setHomePhoneNumber(txtHomePhone.getText().toString());
        contact.setMobilePhoneNumber(txtMobilePhone.getText().toString());
        contact.setBuddy(true);
        cpaas.getAddressBookService().addContact(contact, "default", new AddContactCallback() {
            @Override
            public void onSuccess(Contact contact) {
                System.out.println("New Contact Added");
                Intent intent = new Intent(AddContactActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onFail(MobileError error) {
                System.out.println("Error : " + error.toString());
            }
        });
    }

    public void updateContact(Contact contact) {
        Contact existingContact = new Contact(); ///*****
        existingContact.setBuddy(false);
        existingContact.setEmailAddress("test2@test.com");
        existingContact.setFirstName(""); // removing the first name attribute (if exists) by giving empty string.

        cpaas.getAddressBookService().updateContact(existingContact, "default", new UpdateContactCallback() {
            @Override
            public void onSuccess(Contact contact) {
                System.out.println("Contact Updated");

            }

            @Override
            public void onFail(MobileError error) {
                System.out.println("Error : " + error.toString());
            }
        });

    }


}