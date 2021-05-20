package com.example.surveytaker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;



public class DbHandler extends SQLiteOpenHelper {
    private static final int DB_VERSION = 11;
    private static final String DB_NAME = "surveydb";
    private static final String TABLE_SURVEYS = "surveys";
    private static final String KEY_SURVEYID = "surveyid";
    private static final String KEY_SURVEYNAME = "surveyname";
    private static final String KEY_SURVEYDESCRIPTION = "surveydescription";


    private static final String TABLE_QUESTIONS = "questions";
    private static final String KEY_QUESTIONID = "questionid";
    private static final String KEY_QUESTIONDESCRIPTION = "questiondescription";
    private static final String KEY_QUESTION_SURVEYID = "surveyid";

    private static final String TABLE_SURVEYINSTANCES = "surveyinstances";
    private static final String KEY_SURVEYINSTANCEID = "surveyinstanceid";
    private static final String KEY_SURVEYINSTANCE_QUESTIONID = "questionid";
    private static final String KEY_SURVEYINSTANCE_SURVEYID = "surveyid";
    private static final String KEY_SURVEYINSTANCE_USERID= "userid";
    private static final String KEY_SURVEYINSTANCE_ANSWER = "answer";


    public DbHandler(Context context){
        super(context,DB_NAME, null, DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        String CREATE_SURVEYS_TABLE = "CREATE TABLE " + TABLE_SURVEYS + "("
                + KEY_SURVEYID + " TEXT PRIMARY KEY ," + KEY_SURVEYNAME + " TEXT , " + KEY_SURVEYDESCRIPTION + " TEXT"
                +  ")";

        db.execSQL(CREATE_SURVEYS_TABLE);

        String CREATE_QUESTIONS_TABLE = "CREATE TABLE " + TABLE_QUESTIONS + "("
                + KEY_QUESTIONID + " TEXT PRIMARY KEY ," + KEY_QUESTIONDESCRIPTION + " TEXT ," + KEY_QUESTION_SURVEYID + " TEXT"
                +  ")";

        db.execSQL(CREATE_QUESTIONS_TABLE);


        String CREATE_SURVEYINSTANCES_TABLE = "CREATE TABLE " + TABLE_SURVEYINSTANCES + "("
                + KEY_SURVEYINSTANCEID + " TEXT ,"
                + KEY_SURVEYINSTANCE_QUESTIONID + " TEXT ," + KEY_SURVEYINSTANCE_SURVEYID + " TEXT ," + KEY_SURVEYINSTANCE_USERID + " TEXT ,"
                + KEY_SURVEYINSTANCE_ANSWER + " TEXT "
                +  ")";

        db.execSQL(CREATE_SURVEYINSTANCES_TABLE);

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        // Drop older table if exist
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SURVEYS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUESTIONS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SURVEYINSTANCES);
        // Create tables again
        onCreate(db);
    }

    // **** CRUD (Create, Read, Update, Delete) Operations ***** //

    // Adding new Survey Details
    void insertSurveyDetails(String surveyid, String surveyname, String surveydescription){
        //Get the Data Repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();
        //Create a new map of values, where column names are the keys
        ContentValues cValues = new ContentValues();
        cValues.put(KEY_SURVEYID, surveyid);
        cValues.put(KEY_SURVEYNAME, surveyname);
        cValues.put(KEY_SURVEYDESCRIPTION, surveydescription);

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(TABLE_SURVEYS,null, cValues);
        db.close();
    }


    // Adding new Question Details
    void insertQuestionDetails(String surveyid, String questionid, String questiondescription){
        //Get the Data Repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();
        //Create a new map of values, where column names are the keys
        ContentValues cValues = new ContentValues();
        cValues.put(KEY_QUESTION_SURVEYID, surveyid);
        cValues.put(KEY_QUESTIONID, questionid);
        cValues.put(KEY_QUESTIONDESCRIPTION, questiondescription);

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(TABLE_QUESTIONS,null, cValues);
        db.close();
    }

    // Adding new Survey Instance Questions
    void insertSurveyInstanceQuestionDetails(String surveyinstanceid, String surveyinstancequestionid, String surveyinstancesurveyid, String surveyinstanceuserid, String surveyinstanceanswer){
        //Get the Data Repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();
        //Create a new map of values, where column names are the keys
        ContentValues cValues = new ContentValues();
        cValues.put(KEY_SURVEYINSTANCEID, surveyinstanceid);
        cValues.put(KEY_SURVEYINSTANCE_QUESTIONID, surveyinstancequestionid);
        cValues.put(KEY_SURVEYINSTANCE_SURVEYID, surveyinstancesurveyid);
        cValues.put(KEY_SURVEYINSTANCE_USERID, surveyinstanceuserid);
        cValues.put(KEY_SURVEYINSTANCE_ANSWER, surveyinstanceanswer);

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(TABLE_SURVEYINSTANCES,null, cValues);
        db.close();
    }


    // Get Survey Details
    public ArrayList<HashMap<String, String>> GetSurveys(){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> surveyList = new ArrayList<>();
        String query = "SELECT surveyid, surveyname, surveydescription FROM "+ TABLE_SURVEYS;
        Cursor cursor = db.rawQuery(query,null);
        while (cursor.moveToNext()){
            HashMap<String,String> survey = new HashMap<>();
            survey.put("survey_id",cursor.getString(cursor.getColumnIndex(KEY_SURVEYID)));
            survey.put("survey_name",cursor.getString(cursor.getColumnIndex(KEY_SURVEYNAME)));
            survey.put("survey_description",cursor.getString(cursor.getColumnIndex(KEY_SURVEYDESCRIPTION)));

            surveyList.add(survey);
        }
        return  surveyList;
    }



    // Get Questions
    public ArrayList<HashMap<String, String>> GetQuestions(String surveyid){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> questionList = new ArrayList<>();
        String query = "SELECT surveyid, questionid, questiondescription FROM "+ TABLE_QUESTIONS + " WHERE surveyid = " + surveyid;
        Cursor cursor = db.rawQuery(query,null);
        while (cursor.moveToNext()){
            HashMap<String,String> questions = new HashMap<>();
            questions.put("question_id",cursor.getString(cursor.getColumnIndex(KEY_QUESTIONID)));
            questions.put("survey_id",cursor.getString(cursor.getColumnIndex(KEY_SURVEYID)));
            questions.put("question_description",cursor.getString(cursor.getColumnIndex(KEY_QUESTIONDESCRIPTION)));

            questionList.add(questions);
        }
        return  questionList;
    }


    // delete all survey records
    void deleteSurveys(){
        //Get the Data Repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_SURVEYS);
        db.close();
    }

    // delete all question records
    void deleteQuestions(){
        //Get the Data Repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_QUESTIONS);
        db.close();
    }

    // delete all survey instance records
    void deleteSurveyInstances(){
        //Get the Data Repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_SURVEYINSTANCES);
        db.close();
    }



    void discardSurveyInstance(String surveyinstanceid){
        //Get the Data Repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_SURVEYINSTANCES + " WHERE " + KEY_SURVEYINSTANCEID  + " = '" + surveyinstanceid + "'" );
        db.close();
    }


    public Cursor getAllCompletedSurveyInstances() {
        SQLiteDatabase db = this.getWritableDatabase();
        //Cursor c=db.rawQuery("select distinct * from "+TABLE_Name+" where "+Col_3+" LIKE '%?%' and "+Col_4+" LIKE '%?%'", new String[]{DepartStation,Destination});

        String query = "SELECT surveyid, questionid, userid, surveyinstanceid, answer FROM " + TABLE_SURVEYINSTANCES;
        Cursor cursor = db.rawQuery(query,null);
        return cursor;
    }

}
