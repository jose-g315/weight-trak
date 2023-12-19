/*
Jose D Gonzalez
Weight Trak
Version 1
Setting Goal Weight Class
 */

package com.project.weighttrak;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.project.weighttrak.DataModel.User;

public class GoalWeight extends AppCompatActivity {
    // Declaring variables for the EditTexts and Buttons. Also an instance of the database.
    EditText goalWeight;
    Button setButton;
    TrakDataBase DB2;
    // Variable to save the string for the logged in user.
    String userLoggedIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal_weight);

        // Initializing the variables and the database.
        goalWeight = findViewById(R.id.editTextNumber);
        setButton = findViewById(R.id.button5);
        DB2 = new TrakDataBase(this);

        // Initializing variable with the static variable from the Login class.
        userLoggedIn = Login.userLoggedIn;
        // Disabling button.
        setButton.setEnabled(false);

        // Text watcher method.
        goalWeight.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Enabling button if goalWeight EditText is not empty.
                setButton.setEnabled(!TextUtils.isEmpty(s));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }

        });
    }

    // OnClick method for inserting a user object with the goal weight.
    public void SetGoalWeight(View view) {
        // Initializing new user.
        User user = new User();
        user.goalweight = goalWeight.getText().toString().trim();
        user.username = userLoggedIn;
        // If username does not exits in table then add values.
        if(!DB2.checkUserInGoalWeightTable(userLoggedIn)){
            DB2.addGoalWeight(user);
        }
        // Updating goal weight if username does exists.
        else {
            DB2.updateGoalWeight(userLoggedIn, user.goalweight);
        }
        // Going back to main activity.
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);


    }
}