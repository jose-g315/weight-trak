/*
Jose D Gonzalez
Weight Trak
Version 1
Main Activity/Grid View Of Data
 */
package com.project.weighttrak;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.project.weighttrak.DataModel.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {
    // Declaring variables for the EditTexts and Buttons and an instance of the database.
    TextView goalWeight;
    TrakDataBase DB3;
    GridView myGrid;
    ArrayList<User> records;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Initializing the variables
        DB3 = new TrakDataBase(this);
        goalWeight = findViewById(R.id.textView7);
        myGrid = findViewById(R.id.myGrid);
        goalWeight.setText(DB3.displayGoalWeight(Login.userLoggedIn));

        // After user inputs weight entry this function checks to see if its the same as the goal weight.
        checkingGoalWeight(DB3.displayGoalWeight(Login.userLoggedIn));
        // DDisplaying all entry's.
        records = DB3.getAllWeights(Login.userLoggedIn);
        records.toString();
        myGrid.setAdapter(new ArrayAdapter<>(this, R.layout.entry, records));

    }
    // Function to compare to the user daily weight entry.
    public void checkingGoalWeight(String goalweight) {
        // Making sure entry is not null.
        if (AddDailyWeight.weightEntry != null) {
            int goal = Integer.parseInt(goalweight);
            int entry = Integer.parseInt(AddDailyWeight.weightEntry);
            if (entry <= goal) {
                Toast.makeText(this, "Goal Weight Reached!", Toast.LENGTH_LONG).show();
                // Sending SMS if permission is granted.
                if (Permission.permissionGranted) {
                    SMS();

                }
            }
        }
    }

    // Starting Add/Delete Weight Activity.
    public void AddDeleteWeight(View view) {
        Intent intent = new Intent(this, AddDailyWeight.class);
        startActivity(intent);
    }
    //Starting Notifications Activity.
    public void AllowNotifications(View view) {
        Intent intent = new Intent(this, Permission.class);
        startActivity(intent);
    }
    // Starting Set Weight Activity.
    public void SetWeight(View view) {
        Intent intent = new Intent(this, GoalWeight.class);
        startActivity(intent);
    }
    // Function to send a SMS message to the user provided number.
    public void SMS() {
        if (Permission.phoneNumber != null) {
            // EEmulator number
            //String phoneNumber = "5551234567";
            String phoneNumber = Permission.phoneNumber;
            String message = "Congratulations! You Have Reached You Goal Weight! -Sincerely Weight Trak."; // Replace with your desired message

            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, message, null, null);
        }
        else {
            Toast.makeText(this, "Please Input Number In Notifications.", Toast.LENGTH_LONG).show();
        }
    }
}