package com.example.surveytaker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.app.ProgressDialog;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;


public class DownloadSurveysActivity extends AppCompatActivity {

    private String TAG = DownloadSurveysActivity.class.getSimpleName();
    private Button buttonGoHome;
    private Button buttonDownloadSurveys;
    Intent intent;
    TextView TextStatus;

    DbHandler db = new DbHandler(this);

   // ArrayList<HashMap<String, String>> surveyList;
   // ArrayList<HashMap<String, String>> questionList;


    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_surveys);



        TextStatus = (TextView)findViewById(R.id.TxtStatus);

        buttonGoHome = (Button) findViewById(R.id.btnGoHome);
        buttonGoHome.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                intent = new Intent(DownloadSurveysActivity.this, MainActivity.class);
                startActivity(intent);

            }
        });


        buttonDownloadSurveys = (Button) findViewById(R.id.btnDownloadSurveys);
        buttonDownloadSurveys.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Toast.makeText(getApplicationContext(),
                        "Downloading Surveys & Questions",
                        Toast.LENGTH_LONG).show();


                new GetSurveys().execute();
                new GetQuestions().execute();

                Toast.makeText(getApplicationContext(),
                        "Surveys & Questions Downloaded",
                        Toast.LENGTH_LONG).show();

                TextStatus.setText("Surveys & Questions Downloaded");

            }

        });

    }


    private class GetSurveys extends AsyncTask<Void, Void, Void> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(DownloadSurveysActivity.this, "Json Data is downloading", Toast.LENGTH_LONG).show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {



            db.deleteSurveyInstances();
            db.deleteQuestions();
            db.deleteSurveys();

            SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
            String serverURLKey = sharedPreferences.getString("serverURLKey", "");
            String userIdKey = sharedPreferences.getString("userIdKey", "");
            String passCodeKey = sharedPreferences.getString("passCodeKey", "");

            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
           String url = serverURLKey + "/usersurveys/" + userIdKey + "/" + passCodeKey +"/";

            String jsonStr = sh.makeServiceCall(url);


            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONArray surveys = jsonObj.getJSONArray("surveys");

                    // looping through All Surveys
                    for (int i = 0; i < surveys.length(); i++) {
                        JSONObject c = surveys.getJSONObject(i);
                        String surveyid = c.getString("surveyid");
                        String surveyname = c.getString("surveyname");
                        String surveydescription = c.getString("surveydescription");

                        db.insertSurveyDetails(surveyid, surveyname, surveydescription);



                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });

                }

            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG).show();
                    }
                });
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

        }
    }

        ///////////////////////

        private class GetQuestions extends AsyncTask<Void, Void, Void> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                Toast.makeText(DownloadSurveysActivity.this, "Json Data is downloading", Toast.LENGTH_LONG).show();

            }

            @Override
            protected Void doInBackground(Void... arg0) {

                HttpHandler sh = new HttpHandler();

                SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                String serverURLKey = sharedPreferences.getString("serverURLKey", "");
                String userIdKey = sharedPreferences.getString("userIdKey", "");
                String passCodeKey = sharedPreferences.getString("passCodeKey", "");

                // Making a request to url and getting response
               String url = serverURLKey + "/userquestions/" + userIdKey + "/" + passCodeKey +"/";

                String jsonStr = sh.makeServiceCall(url);


                if (jsonStr != null) {
                    try {
                        JSONObject jsonObj = new JSONObject(jsonStr);

                        // Getting JSON Array node
                        JSONArray questions = jsonObj.getJSONArray("questions");

                        // looping through All Questions
                        for (int i = 0; i < questions.length(); i++) {
                            JSONObject c = questions.getJSONObject(i);
                            String surveyid = c.getString("surveyid");
                            String questionid = c.getString("questionid");
                            String questiondescription = c.getString("questiondescription");


                            db.insertQuestionDetails(surveyid, questionid, questiondescription);

                            /*
                            // tmp hash map for single question
                            HashMap<String, String> question = new HashMap<>();

                            // adding each child node to HashMap key => value
                            question.put("surveyid", surveyid);
                            question.put("questionid", questionid);
                            question.put("questiondescription", questiondescription);

                            // adding question to question list
                            questionList.add(question);

                             */
                        }
                    } catch (final JSONException e) {
                        Log.e(TAG, "Json parsing error: " + e.getMessage());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(),
                                        "Json parsing error: " + e.getMessage(),
                                        Toast.LENGTH_LONG).show();
                            }
                        });

                    }

                } else {
                    Log.e(TAG, "Couldn't get json from server.");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Couldn't get json from server. Check LogCat for possible errors!",
                                    Toast.LENGTH_LONG).show();
                        }
                    });
                }

                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                super.onPostExecute(result);

            }
        }

}

