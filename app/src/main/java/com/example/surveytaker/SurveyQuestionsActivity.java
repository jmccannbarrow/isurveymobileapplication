package com.example.surveytaker;

        import android.app.Activity;
        import android.content.Context;
        import android.content.Intent;
        import android.os.Bundle;
        import android.support.v7.app.AppCompatActivity;
        import android.util.Log;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.Button;
        import android.view.View.OnClickListener;
        import android.widget.LinearLayout;
        import android.widget.ListAdapter;
        import android.widget.ListView;
        import android.widget.SimpleAdapter;
        import android.widget.TextView;
        import android.widget.Toast;
        import android.content.SharedPreferences;
        import android.widget.EditText;

        import java.util.ArrayList;
        import java.util.HashMap;
        import java.util.List;


public class SurveyQuestionsActivity extends AppCompatActivity {

    private String TAG = SurveyQuestionsActivity.class.getSimpleName();
    private Button buttonGoHome;
    private Button buttonSaveSurvey;
    Intent intent;
    TextView TextSurveyName;
    TextView TextSurveyDescription;


    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_questions);
        Bundle extras = getIntent().getExtras();

        buttonGoHome = (Button) findViewById(R.id.btnGoHome);
        buttonSaveSurvey = (Button) findViewById(R.id.btnSaveSurvey);


        DbHandler db = new DbHandler(this);
        String surveyinstancesurveyid = extras.getString("SURVEY_ID");
        String surveyinstanceid = extras.getString("SURVEY_INSTANCE_ID");
        String surveyinstanceuserid = extras.getString("USER_ID");
        String surveyname = extras.getString("SURVEYNAME");
        String surveydescription = extras.getString("SURVEY_DESCRIPTION");

        TextSurveyName = (TextView)findViewById(R.id.TxtSurveyName);
        TextSurveyDescription = (TextView)findViewById(R.id.TxtSurveyDescription);

        TextSurveyName.setText(surveyname);
        TextSurveyDescription.setText(surveydescription);


        ArrayList<HashMap<String, String>> questionList = db.GetQuestions(surveyinstancesurveyid);
        ListView lv = (ListView) findViewById(R.id.question_list);

        ListAdapter adapter = new SimpleAdapter(SurveyQuestionsActivity.this, questionList, R.layout.question_list_item, new String[]{  "question_id", "survey_id", "question_description"}, new int[]{   R.id.question_id,   R.id.survey_id, R.id.question_description})
        {

            public View getView (int position, View convertView, ViewGroup parent)
            {
                View v = super.getView(position, convertView, parent);

                Button b=(Button)v.findViewById(R.id.btnSaveAnswer);

                EditText editText_surveyinstanceanswer = (EditText)v.findViewById(R.id.txtAnswer);
                TextView TextView_surveyinstancequestionid = (TextView)v.findViewById(R.id.question_id);

                String surveyinstancequestionid = TextView_surveyinstancequestionid.getText().toString();
                String surveyinstanceanswer = editText_surveyinstanceanswer.getText().toString();

                b.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View arg0) {

                        db.insertSurveyInstanceQuestionDetails(surveyinstanceid,  surveyinstancequestionid, surveyinstancesurveyid, surveyinstanceuserid, editText_surveyinstanceanswer.getText().toString());

                        }
                });
                return v;
            }

        };

        lv.setAdapter(adapter);



        buttonGoHome.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {


                //delete any records relating to the survey instance
                db.discardSurveyInstance(surveyinstanceid);
                intent = new Intent(SurveyQuestionsActivity.this,MainActivity.class);
                startActivity(intent);

            }
        });

        buttonSaveSurvey.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {


                intent = new Intent(SurveyQuestionsActivity.this,MainActivity.class);
                startActivity(intent);

            }
        });

    }
}