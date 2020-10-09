package com.example.kandyloginwithretrofit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.rbbn.cpaas.mobile.CPaaS;
import com.rbbn.cpaas.mobile.addressbook.api.AddContactCallback;
import com.rbbn.cpaas.mobile.addressbook.model.Contact;
import com.rbbn.cpaas.mobile.utilities.exception.MobileError;

public class AddContactActivity extends AppCompatActivity {

    private CPaaS cpaas;
    private TextView txtPrimaryContact;
    private TextView txtFirstName;
    private TextView txtLastName;
    private TextView txtEmail;
    private TextView txtBusinessPhone;
    private TextView txtHomePhone;
    private TextView txtMobilePhone;
    private static final String TAG="AddContactActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        cpaas = CPaaSManager.getInstance().getCpaas();
        txtPrimaryContact = findViewById(R.id.txtPrimaryContact);
        txtFirstName = findViewById(R.id.txtFirstName);
        txtLastName = findViewById(R.id.txtLastName);
        txtEmail = findViewById(R.id.txtEmail);
        txtBusinessPhone = findViewById(R.id.txtBusinessPhone);
        txtHomePhone = findViewById(R.id.txtHomePhone);
        txtMobilePhone = findViewById(R.id.txtMobilePhone);
    }

    //btnSave onClick Function
    public void saveContact(View view) {
        addNewContact();
    }

    /**
     * Create a new contact
     * If success go to MainActivity
     */
    public void addNewContact() {
        if(txtFirstName.getText().toString().isEmpty()){
            Log.i(TAG,"First name is empty");
        }else{
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
                    Log.i(TAG,"New Contact Added");
                    Intent intent = new Intent(AddContactActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }

                @Override
                public void onFail(MobileError error) {
                    Log.i(TAG,"Error : " + error.toString());
                }
            });
        }

    }

}