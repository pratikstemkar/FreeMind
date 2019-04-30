package com.protexcreative.freemind;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.SupportActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {

    // declare variables
    Button login;
    TextView register, forgetpassword;
    TextInputEditText email, password;
    FirebaseAuth mAuth;
    ProgressBar progressBar;

    // on create
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // get the UI ids
        login = findViewById(R.id.login);
        register = findViewById(R.id.txt_register);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        forgetpassword = findViewById(R.id.forgotpassword);
        progressBar = findViewById(R.id.progressBar);

        // Firebase Authentication object
        mAuth = FirebaseAuth.getInstance();

        // register text on click
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // go to a different activity
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                // remove from memory the current activity
                finish();
            }
        });

        // go to diff activity
        forgetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ForgetPasswordActivity.class));
            }
        });

        // login button on click
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // progress bar visible
                progressBar.setVisibility(View.VISIBLE);

                // get the text from the fields
                String str_email = email.getText().toString();
                String str_password = password.getText().toString();

                // check if the fields are not empty
                if(TextUtils.isEmpty(str_email) || TextUtils.isEmpty(str_password)){
                    Toast.makeText(LoginActivity.this, "All fields required!", Toast.LENGTH_SHORT).show();
                } else{
                    // sign in with method
                    mAuth.signInWithEmailAndPassword(str_email, str_password)
                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {        // add on complete listener
                                    if(task.isSuccessful()){        // if the task is successful

                                                String token_id = FirebaseInstanceId.getInstance().getToken();
                                                String current_id = mAuth.getCurrentUser().getUid();
                                                HashMap<String, Object> hashMap = new HashMap<>();
                                                hashMap.put("token_id", token_id);
                                                hashMap.put("verified", isVerified());
                                                DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("Users").child(current_id);

                                                reference1.updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        Toast.makeText(LoginActivity.this, "Token Recieved.", Toast.LENGTH_SHORT).show();
                                                    }
                                                });


                                        // get to the data of the user in database
                                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Users").child(mAuth.getCurrentUser().getUid());

                                        // add on value received
                                        reference.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                // progress bar invisible
                                                progressBar.setVisibility(View.GONE);
                                                // go to a new activity
                                                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                                // add flags to let everyone know that task completed
                                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                // start activity
                                                startActivity(intent);
                                                // remove from memory
                                                finish();
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) { //if the task is cancelled
                                                progressBar.setVisibility(View.GONE);  // progress bar invisible
                                            }
                                        });
                                    } else {    // if task unsuccessful
                                        // progress bar invisible
                                        progressBar.setVisibility(View.GONE);
                                        // toast message for failure
                                        Toast.makeText(LoginActivity.this, "Authentication Failed!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });
    }

    private Boolean isVerified(){
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser.isEmailVerified()){
            return true;
        } else {
            return false;
        }
    }
}

