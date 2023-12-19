/*
Jose D Gonzalez
Weight Trak
Version 1
Database Helper
 */

package com.project.weighttrak;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.project.weighttrak.DataModel.User;

import java.util.ArrayList;
import java.util.Date;


public  class TrakDataBase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "trak.db";
    private static final int VERSION = 6;
    public TrakDataBase(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    // Table for log in credentials.
    private static final class LogInTable {
        private static final String TABLE = "login";
        private static final String COL_USERNAME = "username";
        private static final String COL_PASSWORD = "password";
    }
    // Table for goal weight.
    private static final class GoalWeightTable {
        private static final String TABLE = "goal";
        private static final String COL_GOAL = "goalweight";
        private static final String COL_USERNAME = "username";
    }
    // Table for daily weight.
    private static final class DailyWeightTable {
        private static final String TABLE = "daily";
        private static final String COL_USERNAME = "username";
        private static final String COL_DAILYWEIGHT = "dailyweight";
        private static final String COL_DATE = "date";
        private static final String COL_ID = "id";
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Creating LogInTable.
        db.execSQL("create table " + LogInTable.TABLE + " (" + LogInTable.COL_USERNAME + " text primary key, " + LogInTable.COL_PASSWORD + " text)");
        // Creating GoalWeightTable
        db.execSQL("create table " + GoalWeightTable.TABLE + " (" + GoalWeightTable.COL_USERNAME + " text primary key, " +GoalWeightTable.COL_GOAL + " text)");
        // Creating DailyWeightTable
        db.execSQL("create table " + DailyWeightTable.TABLE + " (" + DailyWeightTable.COL_ID + " integer primary key autoincrement, " + DailyWeightTable.COL_DATE + " text, " + DailyWeightTable.COL_DAILYWEIGHT + " text, " + DailyWeightTable.COL_USERNAME + " text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + LogInTable.TABLE);
        db.execSQL("drop table if exists " + GoalWeightTable.TABLE);
        db.execSQL("drop table if exists " + DailyWeightTable.TABLE);
        onCreate(db);
    }

    // Function to delete an entry in the daily weight table.
    public void deleteEntry(String id){
        long ID = Long.parseLong(id);
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DailyWeightTable.TABLE, DailyWeightTable.COL_ID + " = ?", new String[] {Long.toString(ID)});

    }

    // Function to check if user exits in daily weight table.
    public boolean checkUserDailyWeightTable(String username){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from " + DailyWeightTable.TABLE + " where username = ?", new String[] {username});
        if(cursor.getCount() > 0) {
            return true;
        }
        else {
            return false;
        }
    }

    // Function to add weight entry into daily weight table.
    public void addWeight(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        // Setting values with the correct data from the user object.
        values.put(DailyWeightTable.COL_DATE, user.date);
        values.put(DailyWeightTable.COL_DAILYWEIGHT, user.dailyweight);
        values.put(DailyWeightTable.COL_USERNAME, user.username);

        db.insert(DailyWeightTable.TABLE,null, values);
    }

    // Function to display goal weight for a specific user.
    public String displayGoalWeight(String username){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from " + GoalWeightTable.TABLE + " where username = ?", new String[] {username});
        String weightDisplay;
        // If these is a gaol weight set then return it.
        if(cursor.getCount() > 0) {
            cursor.moveToFirst();
            weightDisplay = cursor.getString(1);
            return weightDisplay;
        }
        else {
            return "No Goal Weight Set";
        }
    }

    // Function to update goal weight on goal weight table.
    public void updateGoalWeight(String username, String goalweight) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(GoalWeightTable.COL_GOAL, goalweight);
        db.update(GoalWeightTable.TABLE, values, "username = ?",  new String[] {username});

    }
    // Function to adding goal weight into goal weight table.
    public void addGoalWeight(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(GoalWeightTable.COL_GOAL, user.goalweight);
        values.put(GoalWeightTable.COL_USERNAME, user.username);

        db.insert(GoalWeightTable.TABLE,null, values);
        //db.close();
    }
    // Function for adding log in credentials for a user into the user table.
    public void addLogIn(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(LogInTable.COL_USERNAME, user.username);
        values.put(LogInTable.COL_PASSWORD, user.password);

        db.insert(LogInTable.TABLE, null, values);
        //db.close();
    }

    // Function to check username in goal weight table.
    public boolean checkUserInGoalWeightTable(String username){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from " + GoalWeightTable.TABLE + " where username = ?", new String[] {username});
        if(cursor.getCount() > 0) {
            return true;
        }
        else {
            return false;
        }
    }

    // Function to check a username against the user table.
    public boolean checkUser(String username){
       SQLiteDatabase db = this.getWritableDatabase();
       Cursor cursor = db.rawQuery("Select * from " + LogInTable.TABLE + " where username = ?", new String[] {username});
       if(cursor.getCount() > 0) {
           return true;
       }
       else {
           return false;
       }
    }

    // Function to check a username and password against the user table.
    public boolean checkUser(String username, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from " + LogInTable.TABLE + " where username = ? and password = ?",new String[]{username, password});
        if(cursor.getCount() > 0) {
            return true;
        }
        else {
            return  false;
        }
    }
    // Function to get all records from daily weight table
    public ArrayList<User> getAllWeights(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from " + DailyWeightTable.TABLE + " where username = ?", new String[] {username});
        // Empty array list to hold User objects.
        ArrayList<User> users = new ArrayList<>();
        // Cursor gets first row and then adding the values from each column into a User object.
        if (cursor.moveToFirst()) {
            do {
                User user = new User();
                user.weightId = String.valueOf(cursor.getLong(0));
                user.date = cursor.getString(1);
                user.dailyweight = cursor.getString(2);
                // Adding the user object.
                users.add(user);
            } while (cursor.moveToNext());
        }
        cursor.close();
        // Returning all user objects.
        return users;
    }


}
