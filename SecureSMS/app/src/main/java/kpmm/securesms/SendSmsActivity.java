package kpmm.securesms;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SendSmsActivity extends Activity {

    Button sendSMSBtn;
    EditText toPhoneNumberET;
    EditText smsMessageET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_sms);
        sendSMSBtn = (Button) findViewById(R.id.btnSendSMS);
        toPhoneNumberET = (EditText) findViewById(R.id.editTextPhoneNo);
        smsMessageET = (EditText) findViewById(R.id.editTextSMS);
        sendSMSBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                sendSMS();
            }
        });
    }

    protected void sendSMS() {
        String toPhoneNumber = toPhoneNumberET.getText().toString();
        Encryption newObj = new Encryption();
        String smsMessage = smsMessageET.getText().toString();

        try {
            smsMessage = newObj.encryptPrivateKey(smsMessage, newObj.getPrivateKey());
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(),
                    "Encryption failed.",
                    Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(toPhoneNumber, null, smsMessage, null, null);
            Toast.makeText(getApplicationContext(), "SMS sent.",
                    Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(),
                    "Sending SMS failed.",
                    Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    protected void sendPublicKey() {
        String toPhoneNumber = toPhoneNumberET.getText().toString();
        Encryption newObj = new Encryption();
        String smsMessage = "!D34DB33F" + newObj.byte2hex(newObj.getPublicKey().getEncoded());

        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(toPhoneNumber, null, smsMessage, null, null);
            Toast.makeText(getApplicationContext(), "SMS sent.",
                    Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(),
                    "Sending SMS failed.",
                    Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    public void goToInbox(View view) {
        Intent intent = new Intent(SendSmsActivity.this, ReceiveSmsActivity.class);
        startActivity(intent);
    }
}