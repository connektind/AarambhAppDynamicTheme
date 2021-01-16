package com.example.aarambhappdynamictheme.sheardPrefrence;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.aarambhappdynamictheme.model.TestQuestionHandleModel;

import java.util.ArrayList;
import java.util.List;


public class AarambhTestHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DB_NAME = "TQuiz.db";

    //If you want to add more questions or wanna update table values
    //or any kind of modification in db just increment version no.
    private static final int DB_VERSION = 3;
    //Table name
    private static final String TABLE_NAME = "TQ";
    //Id of question
    private static final String UID = "_UID";
    //Question
    private static final String QUESTION = "QUESTION";
    //Option A
    private static final String OPTA = "OPTA";
    //Option B
    private static final String OPTB = "OPTB";
    //Option C
    private static final String OPTC = "OPTC";
    //Option D
    private static final String OPTD = "OPTD";
    //Answer
    private static final String ANSWER = "ANSWER";
    private static final String ANSWER1 = "ANSWER1";
    private static final String TYPE = "TYPE";


    //So basically we are now creating table with first column-id , sec column-question , third column -option A, fourth column -option B , Fifth column -option C , sixth column -option D , seventh column - answer(i.e ans of  question)
    private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ( " + UID + " INTEGER PRIMARY KEY AUTOINCREMENT , " + QUESTION + " VARCHAR(255), " + OPTA + " VARCHAR(255), " + OPTB + " VARCHAR(255), " + OPTC + " VARCHAR(255), " + OPTD + " VARCHAR(255), " + ANSWER + " VARCHAR(255), " + ANSWER1 + " VARCHAR(255), " + TYPE + " VARCHAR(255));";
    //Drop table query
    private static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    public AarambhTestHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //OnCreate is called only once
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //OnUpgrade is called when ever we upgrade or increment our database version no
        sqLiteDatabase.execSQL(DROP_TABLE);
        onCreate(sqLiteDatabase);
    }

    public void allQuestion() {
        ArrayList<TestQuestionHandleModel> arraylist = new ArrayList<>();

        arraylist.add(new TestQuestionHandleModel("Which is the largest fresh water lake in the world?","Chilika Lake","Lake Baikal","Lake Titicaca","None of these","Lake Baikal","1"));
        //true false type
        arraylist.add(new TestQuestionHandleModel("Two lines with positive slopes can be perpendicular.","True","False","False","2"));
        //multipal option
        arraylist.add(new TestQuestionHandleModel("Which of the following integers are multiples of both 2 and 3?","8","9","12","18","12","18","3"));
        //fill in the blank
        arraylist.add(new TestQuestionHandleModel("Rohit is _____ knowledgeable on this subject.","smartly","powerfully","firmly","highly","highly","4"));

        arraylist.add(new TestQuestionHandleModel("Which is the largest fresh water lake in the world?","Chilika Lake","Lake Baikal","Lake Titicaca","None of these","Lake Baikal","1"));
        //true false type
        arraylist.add(new TestQuestionHandleModel("The product of two positive numbers is NOT positive.","True","False","False","2"));
        //multipal option
        arraylist.add(new TestQuestionHandleModel("Which of the following integers are multiples of both 2 and 3?","8","9","12","18","12","18","3"));
        //fill in the blank
        arraylist.add(new TestQuestionHandleModel("Bees store _______________in their beehive.","smartly","Honey","firmly","highly","Honey","4"));

        arraylist.add(new TestQuestionHandleModel("Which is the largest fresh water lake in the world?","Chilika Lake","Lake Baikal","Lake Titicaca","None of these","Lake Baikal","1"));
        //true false type
        arraylist.add(new TestQuestionHandleModel("30% of x is equal to 0.03x","True","False","False","2"));
        //multipal option
        arraylist.add(new TestQuestionHandleModel("Which of the following integers are multiples of both 2 and 3?","8","9","12","18","12","18","3"));
        //fill in the blank
        arraylist.add(new TestQuestionHandleModel("Lion is a ___________ Animal.","smartly","powerfully","Carnivore","highly","Carnivore","4"));

        arraylist.add(new TestQuestionHandleModel("Which is the largest fresh water lake in the world?","Chilika Lake","Lake Baikal","Lake Titicaca","None of these","Lake Baikal","1"));
        //true false type
        arraylist.add(new TestQuestionHandleModel("All plants are edible.","True","False","False","2"));
        //multipal option
        arraylist.add(new TestQuestionHandleModel("Which of the following integers are multiples of both 2 and 3?","8","9","12","18","12","18","3"));
        //fill in the blank
        arraylist.add(new TestQuestionHandleModel("We eat ___________ in onion plant.","leave","powerfully","firmly","highly","leave","4"));

        arraylist.add(new TestQuestionHandleModel("Which is the largest fresh water lake in the world?","Chilika Lake","Lake Baikal","Lake Titicaca","None of these","Lake Baikal","1"));
        //true false type
        arraylist.add(new TestQuestionHandleModel("Cooked food is tasty and healthy.","True","False","True","2"));
        //multipal option
        arraylist.add(new TestQuestionHandleModel("Which of the following integers are multiples of both 2 and 3?","8","9","12","18","12","18","3"));
        //fill in the blank
        arraylist.add(new TestQuestionHandleModel("The edible plant part in spinach is the______________.","smartly","powerfully","firmly","Leave","Leave","4"));

        this.addAllQuestions(arraylist);

    }


    private void addAllQuestions(ArrayList<TestQuestionHandleModel> allQuestions) {
        SQLiteDatabase db = this.getWritableDatabase();
        Log.e("All Ques",allQuestions.get(0).getQuestion()+"");
        Log.e("All Ques",allQuestions.get(0).getOption1()+"");
        Log.e("All Ques",allQuestions.get(0).getOption2()+"");
        Log.e("All Ques",allQuestions.get(0).getOption3()+"");
        Log.e("All Ques",allQuestions.get(0).getOption4()+"");
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            for (TestQuestionHandleModel question : allQuestions) {
                values.put(QUESTION, question.getQuestion());
                values.put(OPTA, question.getOption1());
                Log.e("option1",OPTA);
                values.put(OPTB, question.getOption2());
                Log.e("option1",OPTB);
                values.put(OPTC, question.getOption3());
                Log.e("option1",OPTC);
                values.put(OPTD, question.getOption4());
                Log.e("option1",OPTD);
                values.put(ANSWER, question.getAnswer());
                values.put(ANSWER1, question.getAnswer2());
                values.put(TYPE, question.getType());
                Log.e("option1",TYPE);
                db.insert(TABLE_NAME, null, values);
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
            db.close();
        }
    }


    public List<TestQuestionHandleModel> getAllOfTheQuestions() {

        List<TestQuestionHandleModel> questionsList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        String coloumn[] = {UID, QUESTION, OPTA, OPTB, OPTC, OPTD, ANSWER,ANSWER1,TYPE};
        Log.e("opt111", OPTA);
        Log.e("opt111", OPTB);
        Log.e("opt111", OPTC);
        Log.e("opt111", OPTD);
        Cursor cursor = db.query(TABLE_NAME, coloumn, null, null, null, null, null);


        while (cursor.moveToNext()) {
            try {
                TestQuestionHandleModel question = new TestQuestionHandleModel();
                question.setId(cursor.getInt(0));
                try {
                    question.setType(cursor.getString(8));
                    Log.e("Type", cursor.getString(8));
                }catch (Exception e){
                    e.printStackTrace();
                }

                try{
                    question.setQuestion(cursor.getString(1));
                    Log.e("Col1", cursor.getString(1));
                }catch (Exception e){
                    e.printStackTrace();
                }
                try {
                    question.setOption1(cursor.getString(2));
                    Log.e("Opt1", cursor.getString(2));

                }catch (Exception e){
                    e.printStackTrace();
                }
                try {
                    question.setOption2(cursor.getString(3));
                    Log.e("Opt2", cursor.getString(3));

                }catch (Exception e){
                    e.printStackTrace();
                }
                try {
                    question.setOption3(cursor.getString(4));
                    Log.e("Opt3", cursor.getString(4));

                }catch (Exception e){
                    e.printStackTrace();
                }

                try {
                    question.setOption4(cursor.getString(5));
                    Log.e("Opt4", cursor.getString(5));

                }catch (Exception e){
                    e.printStackTrace();
                }
                try {
                    question.setAnswer(cursor.getString(6));
                    Log.e("Answer", cursor.getString(6));

                }catch (Exception e){
                    e.printStackTrace();
                }
                try {
                    question.setAnswer2(cursor.getString(7));
                    Log.e("Answer2", cursor.getString(7));
                }catch (Exception e){
                    e.printStackTrace();
                }


                questionsList.add(question);
            } catch (Exception e) {
                e.printStackTrace();
            }}

            db.setTransactionSuccessful();
            db.endTransaction();
            cursor.close();
            db.close();
            return questionsList;
        }
    }
