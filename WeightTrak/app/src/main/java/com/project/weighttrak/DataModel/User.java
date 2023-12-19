/*
Jose D Gonzalez
Weight Trak
Version 1
User Class
 */

package com.project.weighttrak.DataModel;

import java.util.ArrayList;

// User object to hold variables.
public class User {
    public String username = null;
    public String password = null;
    public String goalweight = null;
    public String dailyweight = null;
    public String date = null;
    public String weightId = null;


    // Overriding toString to format User object output.
    @Override
    public  String toString() {
        return  "ID: " + weightId + "\n   Date: " + date + "\n   Weight: " + dailyweight;
    }

}
