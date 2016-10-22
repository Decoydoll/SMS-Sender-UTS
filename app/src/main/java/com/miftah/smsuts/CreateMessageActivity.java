package com.miftah.smsuts;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.miftah.smsuts.model.ContactData;

public class CreateMessageActivity extends AppCompatActivity {

    private ContactData contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_message);

        contact = (ContactData) getIntent().getSerializableExtra("CONTACT");

        TextView header = (TextView)findViewById(R.id.header);
        header.setText("Send SMS to " + contact.getDisplayName());
    }

    public void sendSms(View view){
        EditText messageTextBox = (EditText)findViewById(R.id.messageTextBox);

        String message = messageTextBox.getText().toString();

        if(message.isEmpty()){
            Toast.makeText(this, "Cannot send empty message.", Toast.LENGTH_SHORT).show();
        }
        else{
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(contact.getNumber(), null, message, null, null);
            finish();
        }
    }
}
