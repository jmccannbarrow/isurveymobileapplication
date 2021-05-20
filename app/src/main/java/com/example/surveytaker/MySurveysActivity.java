package com.example.surveytaker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class MySurveysActivity extends AppCompatActivity {

    private String TAG = MySurveysActivity.class.getSimpleName();
    private Button buttonGoHome;
    Intent intent;

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_surveys);

        DbHandler db = new DbHandler(this);

        ArrayList<HashMap<String, String>> surveyList = db.GetSurveys();
        ListView lv = (ListView) findViewById(R.id.survey_list);

        ListAdapter adapter = new SimpleAdapter(MySurveysActivity.this, surveyList, R.layout.list_row,new String[]{  "survey_id", "survey_name", "survey_description"}, new int[]{   R.id.survey_id,   R.id.survey_name, R.id.survey_description});
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                HashMap<String,String> map =(HashMap<String,String>)lv.getItemAtPosition(position);

                String surveyID = map.get("survey_id");
                String surveyName = map.get("survey_name");
                String surveyDescription= map.get("survey_description");

                SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                String userIdKey = sharedPreferences.getString("userIdKey", "");


                Intent intentSurveyDetails  = new Intent(MySurveysActivity.this,SurveyDetailsActivity.class);
                intentSurveyDetails.putExtra("SURVEY_ID", surveyID);
                intentSurveyDetails.putExtra("SURVEY_NAME", surveyName);
                intentSurveyDetails.putExtra("SURVEY_DESCRIPTION", surveyDescription);
                intentSurveyDetails.putExtra("USER_ID", userIdKey);
                startActivity(intentSurveyDetails);
            }
        });




        buttonGoHome = (Button) findViewById(R.id.btnGoHome);
        buttonGoHome.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                intent = new Intent(MySurveysActivity.this,MainActivity.class);
                startActivity(intent);

            }
        });



    }


}
