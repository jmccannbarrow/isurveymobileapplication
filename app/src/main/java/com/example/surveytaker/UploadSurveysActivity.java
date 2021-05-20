package com.example.surveytaker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;

public class UploadSurveysActivity extends AppCompatActivity {
    private String TAG = UploadSurveysActivity.class.getSimpleName();
    private Button buttonGoHome;
    private Button buttonUploadSurveys;
    Intent intent;
    DbHandler db = new DbHandler(this);
    String jsonInString;
    TextView TextStatus;


    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_surveys);

        TextStatus = (TextView)findViewById(R.id.TxtStatus);

        buttonGoHome = (Button) findViewById(R.id.btnGoHome);
        buttonGoHome.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                intent = new Intent(UploadSurveysActivity.this, MainActivity.class);
                startActivity(intent);

            }
        });

        buttonUploadSurveys = (Button) findViewById(R.id.btnUploadSurveys);
        buttonUploadSurveys.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {


                jsonInString = getAllCompletedSurveyInstances();

                try {

                    String s1 = jsonInString;
                    s1 = s1.replace("{\"dataList\":", "");
                    StringBuilder s2 = new StringBuilder(s1);
                    s2.replace(s2.length()-1, s2.length(), "");
                    jsonInString = s2.toString();


                    Toast.makeText(getApplicationContext(),
                            "Uploading Survey Responses",
                            Toast.LENGTH_LONG).show();


                    new UploadSurveys().execute();

                    Toast.makeText(getApplicationContext(),
                            "Survey Responses Uploaded",
                            Toast.LENGTH_LONG).show();

                    TextStatus.setText("Survey Responses Uploaded");

                } catch (Exception ex) {

                }
            }
        });


    }


    private class UploadSurveys extends AsyncTask<Void, Void, Void> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }

        @Override
        protected Void doInBackground(Void... arg0) {

            HttpHandler sh = new HttpHandler();
            // Making a request to url and getting response

            SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
            String serverURLKey = sharedPreferences.getString("serverURLKey", "");

            String url = serverURLKey + "/ReceiveJSON/";
            Log.e(TAG, "url: " + url);

            sh.postServiceCall(url,  jsonInString);
            db.deleteSurveyInstances();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

        }
    }



    public String getAllCompletedSurveyInstances() {


        // Cursor is loaded with data
        Cursor cursor = db.getAllCompletedSurveyInstances();



        ArrayList<Data> dataList = new ArrayList<Data>();

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            do {
                Data data = new Data();
                data.surveyid = cursor.getString(0);
                data.questionid = cursor.getString(1);
                data.userid = cursor.getString(2);
                data.surveyinstanceid = cursor.getString(3);
                data.answer = cursor.getString(4);

                // Add into the ArrayList here
                dataList.add(data);

            } while (cursor.moveToNext());

                   // Now create the object to be passed to GSON
            ListOfData mydataList = new ListOfData();
            mydataList.dataList = dataList;

            Gson gson = new Gson();
            String jsonInString = gson.toJson(mydataList);

            cursor.close();

            return jsonInString;


        }
return  jsonInString;
    }


}



