package com.example.surveytaker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.Toast;
import android.content.SharedPreferences;
import android.widget.EditText;



public class SettingsActivity extends AppCompatActivity {

    private Button buttonGoHome;
    private Button buttonSaveSettings;
    Intent intent;
    EditText EditTextUserID;
    EditText EditTextPassCode;
    EditText EditTextServerURL;

    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String UserID = "userIdKey";
    public static final String PassCode = "passCodeKey";
    public static final String ServerURL = "serverURLKey";

    SharedPreferences sharedpreferences;

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        buttonGoHome = (Button) findViewById(R.id.btnGoHome);
        buttonSaveSettings = (Button) findViewById(R.id.btnSaveSettings);



        EditTextUserID = (EditText)findViewById(R.id.txtUserID);
        EditTextPassCode = (EditText)findViewById(R.id.txtPassCode);
        EditTextServerURL = (EditText)findViewById(R.id.txtServerURL);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        EditTextUserID.setText(sharedpreferences.getString("userIdKey", ""));
        EditTextPassCode.setText(sharedpreferences.getString("passCodeKey", ""));
        EditTextServerURL.setText(sharedpreferences.getString("serverURLKey", ""));


        buttonGoHome.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                intent = new Intent(SettingsActivity.this,MainActivity.class);
                startActivity(intent);

            }
        });

        buttonSaveSettings.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                String uID  = EditTextUserID.getText().toString();
                String pCode  = EditTextPassCode.getText().toString();
                String sURL  = EditTextServerURL.getText().toString();

                SharedPreferences.Editor editor = sharedpreferences.edit();

                editor.putString(UserID, uID);
                editor.putString(PassCode, pCode);
                editor.putString(ServerURL, sURL);
                editor.apply();
                editor.commit();
            }
        });


    }
}