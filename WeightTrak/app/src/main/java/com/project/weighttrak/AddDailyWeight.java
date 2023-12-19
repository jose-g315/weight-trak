/*
Jose D Gonzalez
Weight Trak
Version 1
Adding Weight Entry Class
 */


package com.project.weighttrak;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.project.weighttrak.DataModel.User;

public class AddDailyWeight extends AppCompatActivity {
    // Declaring variables for the EditTexts and Buttons. Also an instance of the database.
    EditText date;
    EditText weight;
    EditText idText;
    Button addButton;
    Button deleteButton;
    TrakDataBase DB4;
    // Variable to save the string for the logged in user.
    String userLoggedIn;
    // Variable to hold string for the weight entry.
    static String weightEntry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_daily_weight);

        // Initializing the variables and the database.
        date = findViewById(R.id.editTextText2);
        weight = findViewById(R.id.editTextText3);
        idText = findViewById(R.id.editTextNumber2);
        deleteButton = findViewById(R.id.button8);
        addButton = findViewById(R.id.button7);
        DB4 = new TrakDataBase(this);

        // Initializing variable with the static variable from the Login class.
        userLoggedIn = Login.userLoggedIn;
        // Disabling buttons.
        addButton.setEnabled(false);
        deleteButton.setEnabled(false);

        // Using a TextWatcher for both date and weight.
        date.addTextChangedListener(addWeightWatcher);
        weight.addTextChangedListener(addWeightWatcher);

        // Text watcher method for idText.
        idText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Enabling button if goalWeight EditText is not empty.
                deleteButton.setEnabled(!TextUtils.isEmpty(s));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }

        });

    }

    // TextWatcher object to handle two EditTexts.
    TextWatcher addWeightWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // Creating a string to hold in the input of both EditTexts.
            String dateText = date.getText().toString().trim();
            String weightText = weight.getText().toString().trim();
            // Enabling/Disabling buttons depending if the EditTexts are empty or not.
            addButton.setEnabled(!dateText.isEmpty() && !weightText.isEmpty());

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
    // Function to add a weight entry when clicking button.
    public void AddWeight(View view) {
        // Initializing a user object with date and weight.
        User user = new User();
        user.date = date.getText().toString().trim();
        user.dailyweight = weight.getText().toString();
        // Initialing user object with the user that is logged in.
        user.username = userLoggedIn;
        // Setting static variable to weight to be able to be checked in main activity.
        weightEntry = weight.getText().toString().trim();
        // Adding weight if user is not in daily weight table.
        if(!DB4.checkUserDailyWeightTable(userLoggedIn)){
            DB4.addWeight(user);
        }
        else {
            DB4.addWeight(user);
        }
        // Going back to main activity.
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    // Function to delete weight entry when clicking button.
    public void DeleteEntry(View view) {
        // String variable to hold text from IdText.
        String id = idText.getText().toString().trim();
        // Deleting entry if user is in daily weight table.
        if(DB4.checkUserDailyWeightTable(userLoggedIn)){
            DB4.deleteEntry(id);
            // Going back to main activity.
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        else {
            Toast.makeText(this, "Could Not Find Entry", Toast.LENGTH_LONG).show();
        }

    }

}