package ro.pub.cs.systems.eim.lab03.phonedialer;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.SyncStateContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageButton;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class PhoneDialerActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_dialer);


        int buttons[] = {
                R.id.button0,
                R.id.button1,
                R.id.button2,
                R.id.button3,
                R.id.button4,
                R.id.button5,
                R.id.button6,
                R.id.button7,
                R.id.button8,
                R.id.button9,
                R.id.button_star,
                R.id.button_hash,
                R.id.button_hangup,
                R.id.button_call,
                R.id.button_backspace,
                R.id.button_contacts_manager
        };

        for(int id : buttons) {
            if(findViewById(id) instanceof Button) {
                Button btn = (Button) findViewById(id);
                btn.setOnClickListener(this);
            } else if(findViewById(id) instanceof  ImageButton) {
                ImageButton btn = (ImageButton) findViewById(id);
                btn.setOnClickListener(this);
            }
        }
    }

    @Override
    public void onClick(View view) {
        EditText editText = (EditText)findViewById(R.id.editText);

        if(view instanceof ImageButton) {
            ImageButton hangup = (ImageButton)findViewById(R.id.button_hangup);
            ImageButton call = (ImageButton)findViewById(R.id.button_call);
            ImageButton backspace = (ImageButton)findViewById(R.id.button_backspace);
            ImageButton contacts_manager = (ImageButton)findViewById(R.id.button_contacts_manager);

            ImageButton btn = (ImageButton)view;
            if(btn.equals(backspace)) {
                if(editText.getText().length() > 0)
                    editText.setText(editText.getText().subSequence(0, editText.getText().length() - 1));
            } else if(btn.equals(call)) {
                String phoneNumber = ((EditText)findViewById(R.id.editText)).getText().toString();
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + phoneNumber));
                if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                    startActivity(intent);
                } else {
                    ActivityCompat.requestPermissions((Activity) getApplicationContext(), new String[]{Manifest.permission.CALL_PHONE}, 1);
                }
            } else if(btn.equals(contacts_manager)) {
                String phoneNumber = ((EditText)findViewById(R.id.editText)).getText().toString();
                if (phoneNumber.length() > 0) {
                    Intent intent = new Intent("ro.pub.cs.systems.eim.lab04.contactsmanager.intent.action.ContactsManagerActivity");
                    intent.putExtra("ro.pub.cs.systems.eim.lab04.contactsmanager.PHONE_NUMBER_KEY", phoneNumber);
                    startActivityForResult(intent, Constants.CONTACTS_MANAGER_REQUEST_CODE);
                } else {
                    Toast.makeText(getApplication(), "Introdu un numar de telefon", Toast.LENGTH_LONG).show();
                }
            }
        }
        else if(view instanceof Button) {
            Button btn = (Button)view;
            char c = btn.getText().charAt(btn.getText().length() - 1);
            editText.setText(editText.getText() + String.format("%c", c));
        }

    }
}
