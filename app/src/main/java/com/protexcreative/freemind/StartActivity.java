package com.protexcreative.freemind;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class StartActivity extends AppCompatActivity {

    Button login, register;         // Declare the UI components from the activity_start.xml file
    FirebaseUser firebaseUser;      // Declare a variable for FirebaseUser


    // Method to occur onStart of the activity.
    // Code to check if the user is already signed IN
    @Override
    protected void onStart() {
        super.onStart();

        // get the CURRENT USER credentials
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        // if no CURRENT USER exists, it will follow usual procedure
        // if a CURRENT USER already exists, it will directly got to HomeActivity
        if (firebaseUser != null){
            // Method used to go to a different activity
            startActivity(new Intent(StartActivity.this, HomeActivity.class));
            finish();
        }
    }



    // Method to occur on Creation of the activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        // Initialize the UI components
        // Get their IDs
        login = findViewById(R.id.login);
        register = findViewById(R.id.register);

        // Set an OnClick Listener to LOGIN button
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Method used to go to a different activity.
                startActivity(new Intent(StartActivity.this, LoginActivity.class));
            }
        });

        // Set an OnClick Listener to REGISTER button
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Method used to go to a different activity
                startActivity(new Intent(StartActivity.this, RegisterActivity.class));
            }
        });
    }
}
