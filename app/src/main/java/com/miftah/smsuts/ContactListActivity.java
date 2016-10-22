package com.miftah.smsuts;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.miftah.smsuts.model.ContactData;

import java.util.ArrayList;

public class ContactListActivity extends AppCompatActivity {

    private ListView mListView;
    private ArrayList<ContactData> mContacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);

        mListView = (ListView)findViewById(R.id.contacts);

        new Thread(()->{
            getContacts();
        }).start();

        mListView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                // ListView Clicked item value
                ContactData itemValue = (ContactData) mListView.getItemAtPosition(position);

                if(itemValue.getNumber() == null){
                    Toast.makeText(getApplicationContext(), "Contact has no number.", Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent = new Intent(getApplicationContext(), CreateMessageActivity.class);
                    intent.putExtra("CONTACT", itemValue);
                    startActivity(intent);
                }
            }
        });
    }

    private void getContacts(){
        mContacts = new ArrayList<ContactData>();
        String phoneNumber = null;
        String displayName = null;

        Cursor cursor = getContentResolver().query(
                ContactsContract.Contacts.CONTENT_URI,
                null,
                null,
                null,
                null
        );

        while (cursor.moveToNext()){
            displayName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            phoneNumber = null;

            if(Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0){
                Cursor phoneCursor = getContentResolver().query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                        new String[] { cursor.getString(cursor.getColumnIndex( ContactsContract.Contacts._ID )) },
                        null
                );
                while (phoneCursor.moveToNext()) {
                    phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                }
                phoneCursor.close();
            }

            if(phoneNumber != null)
                mContacts.add(new ContactData(phoneNumber, displayName));
        }

        runOnUiThread(() -> {
                ArrayAdapter<ContactData> adapter = new ArrayAdapter<ContactData>(getApplicationContext(), R.layout.contacts_row, R.id.contact, mContacts);
                mListView.setAdapter(adapter);
        });
    }


}
