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
import android.widget.TextView;
import android.widget.Toast;
import android.content.SharedPreferences;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class SurveyDetailsActivity extends AppCompatActivity {

    private String TAG = SurveyDetailsActivity.class.getSimpleName();
    private Button buttonGoHome;
    private Button buttonStartSurvey;

    Intent intent;
    TextView TextSurveyID;
    TextView TextSurveyName;
    TextView TextSurveyDescription;
    String SURVEYID;
    String SURVEYNAME;
    String SURVEYDESCRIPTION;
    String SURVEYINSTANCEID;
    String USERID;


    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_details);
        Bundle extras = getIntent().getExtras();

        buttonGoHome = (Button) findViewById(R.id.btnGoHome);
        buttonStartSurvey = (Button) findViewById(R.id.btnStartSurvey);

       TextSurveyID = (TextView)findViewById(R.id.TxtSurveyID);
        TextSurveyName = (TextView)findViewById(R.id.TxtSurveyName);
        TextSurveyDescription = (TextView)findViewById(R.id.TxtSurveyDescription);

        TextSurveyID.setText(extras.getString("SURVEY_ID"));
        TextSurveyName.setText(extras.getString("SURVEY_NAME"));
        TextSurveyDescription.setText(extras.getString("SURVEY_DESCRIPTION"));

        SURVEYID = extras.getString("SURVEY_ID");
        SURVEYNAME = extras.getString("SURVEY_NAME");
        SURVEYDESCRIPTION = extras.getString("SURVEY_DESCRIPTION");
        USERID = extras.getString("USER_ID");


        buttonGoHome.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                intent = new Intent(SurveyDetailsActivity.this,MainActivity.class);
                startActivity(intent);

            }
        });



        buttonStartSurvey.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                SURVEYINSTANCEID =  UUID.randomUUID().toString();
                Intent surveyQuestionsIntent = new Intent(SurveyDetailsActivity.this,SurveyQuestionsActivity.class);

                 surveyQuestionsIntent.putExtra("SURVEY_ID", SURVEYID);
                 surveyQuestionsIntent.putExtra("SURVEY_NAME", SURVEYNAME);
                 surveyQuestionsIntent.putExtra("SURVEY_DESCRIPTION", SURVEYDESCRIPTION);
                surveyQuestionsIntent.putExtra("SURVEY_INSTANCE_ID", SURVEYINSTANCEID);
                surveyQuestionsIntent.putExtra("USER_ID", USERID);


                startActivity(surveyQuestionsIntent);



            }
        });

    }
}