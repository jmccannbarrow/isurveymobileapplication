package com.example.surveytaker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button buttonDownloadSurveys;
    private Button buttonMySurveys;
    private Button buttonUploadSurveys;
    private Button buttonSettings;

    Intent intent;

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /////////////////////////////////////////////////////////////////////////////////////////////
        buttonDownloadSurveys = (Button) findViewById(R.id.btnDownloadSurveys);
        buttonDownloadSurveys.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                intent = new Intent(MainActivity.this,DownloadSurveysActivity.class);
                startActivity(intent);

            }
        });

        /////////////////////////////////////////////////////////////////////////////////////////////
        buttonMySurveys = (Button) findViewById(R.id.btnMySurveys);
        buttonMySurveys.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                intent = new Intent(MainActivity.this,MySurveysActivity.class);
                startActivity(intent);

            }
        });

        /////////////////////////////////////////////////////////////////////////////////////////////
        buttonUploadSurveys = (Button) findViewById(R.id.btnUploadSurveys);
        buttonUploadSurveys.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                intent = new Intent(MainActivity.this,UploadSurveysActivity.class);
                startActivity(intent);

            }
        });

        /////////////////////////////////////////////////////////////////////////////////////////////
        buttonSettings = (Button) findViewById(R.id.btnSettings);
        buttonSettings.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                intent = new Intent(MainActivity.this,SettingsActivity.class);
                startActivity(intent);

            }
        });





    }
}