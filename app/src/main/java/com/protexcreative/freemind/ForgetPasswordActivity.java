package com.protexcreative.freemind;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetPasswordActivity extends AppCompatActivity {

    // declare variables
    EditText email;
    Button passwordreset;
    ProgressBar progressBar;

    // firebase auth variable
    FirebaseAuth firebaseAuth;

    // on create
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        // get the UI ids
        email = findViewById(R.id.email);
        passwordreset = findViewById(R.id.passwordreset);
        progressBar = findViewById(R.id.progressBar);

        // firebase auth object
        firebaseAuth = FirebaseAuth.getInstance();

        // button on click
        passwordreset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // progress bar visible
                progressBar.setVisibility(View.VISIBLE);
                // method to send password reset mail
                firebaseAuth.sendPasswordResetEmail(email.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {      // on completion of the method
                        if(task.isSuccessful()){
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(ForgetPasswordActivity.this, "Password Reset Email Sent.", Toast.LENGTH_SHORT).show();
                        } else{
                            // failure message
                            Toast.makeText(ForgetPasswordActivity.this, "Password Reset Failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}
