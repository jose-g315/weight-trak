/*
Jose D Gonzalez
Weight Trak
Version 1
Log In Activity
 */

package com.project.weighttrak;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.project.weighttrak.DataModel.User;

public class Login extends AppCompatActivity {
    // Declaring variables for the EditTexts and Buttons. Also an instance of the database.
    EditText userName;
    EditText passWord;
    Button logIn;
    Button createLogIn;
    TrakDataBase DB;
    // Declaring static variable so other classes can access it without making a Login object.
    static String userLoggedIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initializing the variables and the database.
        userName = findViewById(R.id.username);
        passWord = findViewById(R.id.password);
        logIn = findViewById(R.id.button2);
        createLogIn = findViewById(R.id.button3);
        DB = new TrakDataBase(this);

        // Disabling both buttons.
        logIn.setEnabled(false);
        createLogIn.setEnabled(false);

        // Using a TextWatcher for both userName and passWord.
        userName.addTextChangedListener(logInWatcher);
        passWord.addTextChangedListener(logInWatcher);

    }

    // TextWatcher object to handle two EditTexts.
    TextWatcher logInWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // Creating a string to hold in the input of both EditTexts.
            String userNameText = userName.getText().toString().trim();
            String passwordText = passWord.getText().toString().trim();
            // Enabling/Disabling buttons depending if the EditTexts are empty or not.
            logIn.setEnabled(!userNameText.isEmpty() && !passwordText.isEmpty());
            createLogIn.setEnabled(!userNameText.isEmpty() && !passwordText.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    // OnClick function for the Log In button.
    public void LoginAction(View view) {
        // Checking to see if both credentials match any from the database.
        if(DB.checkUser(userName.getText().toString().trim(), passWord.getText().toString().trim())){
            userLoggedIn = userName.getText().toString().trim();
            // Starting main activity.
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            Toast.makeText(this, "Welcome To Weight Trak", Toast.LENGTH_LONG).show();
        }
        // If credentials don't match then the user is notified with instructions.
        else {
            Toast.makeText(this, "Username/Password Is Not Correct. If New User Click Button Below.", Toast.LENGTH_LONG).show();
        }

    }

    // OnClick function for the Create Log In button.
    public void CreateLogInAction(View view) {
        // Checking to see if the username already exits in the database.
        if(!DB.checkUser(userName.getText().toString().trim())) {
            // Creating an empty user object and adding credentials to the appropriate variables.
            User user = new User();
            user.username = userName.getText().toString().trim();
            user.password = passWord.getText().toString().trim();
            userLoggedIn = userName.getText().toString().trim();
            // Adding user to the database.
            DB.addLogIn(user);
            Toast.makeText(this, "New Log In Created. Welcome! Click Log In Button Above.", Toast.LENGTH_LONG).show();
        }
        // Letting user know that user already exits and to just press the log in button.
        else {
            Toast.makeText(this, "Log In Already Exists. Please Press Log In Button!", Toast.LENGTH_LONG).show();
        }
    }
}