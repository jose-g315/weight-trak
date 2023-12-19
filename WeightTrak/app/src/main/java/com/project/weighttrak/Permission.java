/*
Jose D Gonzalez
Weight Trak
Version 1
Permission
 */

package com.project.weighttrak;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

public class Permission extends AppCompatActivity {
    // Declaring variables for the EditTexts and Buttons.
     Button allowButton;
     Button stopButton;
     EditText number;
     // Static variables so main activity can access them once they are initialized.
     static boolean permissionGranted;
     static String phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);
        // Initializing the variables
        allowButton = findViewById(R.id.button9);
        stopButton = findViewById(R.id.button10);
        number = findViewById(R.id.editTextPhone);
        // Disabling button.
        allowButton.setEnabled(false);

        // Text watcher method for number Text.
        number.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Enabling button if number EditText is not empty.
                allowButton.setEnabled(!TextUtils.isEmpty(s));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }

        });

    }
    // Function to allow permission.
    public void AllowSMS(View view) {
        // If permission is  already granted then tell user.
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Already Have Permission.", Toast.LENGTH_LONG).show();
            permissionGranted = true;

        } else {

            // Permission is not granted, request it from the user
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 1);
            Toast.makeText(this, "Asking For Permission.", Toast.LENGTH_LONG).show();
            permissionGranted = true;
        }
        // Letting user that the permission was granted.
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Permission Granted.", Toast.LENGTH_LONG).show();

        }
        // Getting user input for phone number so Main Activity can use it.
        phoneNumber = number.getText().toString().trim();
    }
    // Function to take way permission.
    public void StopSMS(View view) {
        // Revoking permission.
        revokeSelfPermissionOnKill(Manifest.permission.SEND_SMS);
        Toast.makeText(this, "Permission Removed. Restart To See Changes.", Toast.LENGTH_LONG).show();
        permissionGranted = false;

        // Going back to main activity.
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    // Function to go back to main activity.
    public void GoBack(View view) {
        // Going back to main activity.
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


}