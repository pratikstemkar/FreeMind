package com.protexcreative.freemind;

import android.content.Intent;
import android.support.annotation.NonNull;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    // Declare the UI components
    Button register;
    TextView txt_login;
    EditText username, fullname, email, password, passwordconfirm;
    FirebaseAuth mAuth;
    DatabaseReference reference;
    ProgressBar progressBar;

    // Method implemented on Creation of the activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Get a Firebase Authentication Variable
        mAuth = FirebaseAuth.getInstance();

        // Link the UI components to the IDs
        txt_login = findViewById(R.id.txt_login);
        register = findViewById(R.id.register);
        username = findViewById(R.id.username);
        fullname = findViewById(R.id.fullname);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        progressBar = findViewById(R.id.progressBar);
        passwordconfirm = findViewById(R.id.passwordconfirm);

        // Set an OnClick Listener to the REGISTER button
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Set the visibility of the ProgressBar (Circle Loading) to VISIBLE
                progressBar.setVisibility(View.VISIBLE);

                // Get the text entered in the Fields
                final String str_username = username.getText().toString().toLowerCase().replaceAll("\\s+", "_");
                final String str_fullname = fullname.getText().toString();
                final String str_email = email.getText().toString();
                final String str_password = password.getText().toString();
                final String str_passwordc = passwordconfirm.getText().toString();

                Query query = FirebaseDatabase.getInstance().getReference().child("Users").orderByChild("username").equalTo(str_username);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.getChildrenCount()>0){
                            username.setError("Username already exists.");
                            progressBar.setVisibility(View.GONE);
                        }else{
                            // Check if the text in the field is not empty
                            if(TextUtils.isEmpty(str_username) || TextUtils.isEmpty(str_fullname) ||
                                    TextUtils.isEmpty(str_email) || TextUtils.isEmpty(str_password)){
                                // Toast Message is small Black background message at the bottom of the screen
                                username.setError("No field should be empty.");
                                fullname.setError("No field should be empty.");
                                email.setError("No field should be empty.");
                                password.setError("No field should be empty.");
                                passwordconfirm.setError("No field should be empty.");
                                progressBar.setVisibility(View.GONE);
                            } else if(str_password.length() < 6) {
                                password.setError("Password length should be more than 6.");
                                progressBar.setVisibility(View.GONE);
                            } else if (!str_password.equals(str_passwordc)) {
                                password.setError("Passwords do not match.");
                                passwordconfirm.setError("Passwords do not match.");
                                progressBar.setVisibility(View.GONE);
                            }else
                            {
                                // Method declared below that will log the user in the app.
                                username.setError(null);
                                passwordconfirm.setError(null);
                                password.setError(null);
                                fullname.setError(null);
                                email.setError(null);
                                register(str_username, str_fullname, str_email, str_password);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        // Set an OnClick Listener to the Text at the Bottom of the button
        txt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Method to go to a different activity
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                // Method to dismiss the activity (Remove from the memory[RAM])
                finish();
            }
        });
    }

    // Method that handles all the Registration activities.
    private void register(final String username, final String fullname, String email, String password){
        // Method of Firebase Authentication to create a user with Email and Password
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {    // OnComplete Listener will implement when the above Method(createUserWithEmailAndPassword) is completed
                        // If the task is successful following activities occur
                        // Task is a stack of activities that occur in LIFO pattern
                        if(task.isSuccessful()){
                            // The newly created user is taken
                            FirebaseUser firebaseUser = mAuth.getCurrentUser();
                            // New user's UserId is taken
                            String userid = firebaseUser.getUid();

                            // A reference is made to the FirebaseDatabase
                            // Reference is made to the Users thread and then to UserId child
                            reference = FirebaseDatabase.getInstance().getReference().child("Users").child(userid);
                            String token_id = FirebaseInstanceId.getInstance().getToken();

                            // A hashmap datatype is used to enter the data.
                            // Hashmap is like dictionary of Python or Multi Dimensional Array
                            // First create its object.
                            HashMap<String, Object> hashmap = new HashMap<>();
                            // Use put() method to enter the data.
                            hashmap.put("id", userid);
                            hashmap.put("username", username.toLowerCase());
                            hashmap.put("fullname", fullname);
                            // Link in the imageurl is link of the default image that will be visible to the new Users
                            hashmap.put("imageurl", "https://firebasestorage.googleapis.com/v0/b/freemind-dc8cd.appspot.com/o/uploads%2FProfile_avatar_placeholder_large.png?alt=media&token=175601de-1a31-4212-a338-f2e106fa62d3");
                            hashmap.put("bio", "");
                            hashmap.put("instagram", "");
                            hashmap.put("token_id", token_id);
                            hashmap.put("sex", "");
                            hashmap.put("dob", "");
                            hashmap.put("email", firebaseUser.getEmail());
                            hashmap.put("verified", false);

                            // Now the hashmap data is set to the FirebaseDatabase reference we created
                            reference.setValue(hashmap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {      // OnComplete Listener is added.
                                    // if the task is completed successfully
                                    if(task.isSuccessful()){
                                        // ProgressBar is gone
                                        progressBar.setVisibility(View.GONE);
                                        // Create a new intent to go to a new Activity
                                        Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
                                        // Add Flags.
                                        // These are just to let other activties know that no task is left here.
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        // Start the new Activity.
                                        startActivity(intent);
                                    }
                                }
                            });
                        } else {           // If the task is not successful
                            // ProgressBar is made gone
                            progressBar.setVisibility(View.GONE);
                            // Toast message is given.
                            Toast.makeText(RegisterActivity.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}

