package com.protexcreative.freemind;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.protexcreative.freemind.Adapter.UserAdapter;
import com.protexcreative.freemind.Model.User;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class FollowersActivity extends AppCompatActivity {

    // declare variables
    String id;
    String title;

    // list idLIst
    List<String> idList;


    private AdView mAdView1;

    // recycler view, adapter, userList
    RecyclerView recyclerView;
    UserAdapter userAdapter;
    List<User> userList;

    TextView name;
    ImageView back;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_followers);

        // get intent and the extras
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        title = intent.getStringExtra("title");

        // get the UI ids
        name = findViewById(R.id.name);
        back = findViewById(R.id.back);
        recyclerView = findViewById(R.id.recycler_view);
        progressBar = findViewById(R.id.progressBar);


        mAdView1 = findViewById(R.id.adView1);


        final AdRequest adRequest1 = new AdRequest.Builder().build();

        mAdView1.loadAd(adRequest1);


        mAdView1.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                mAdView1.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
                mAdView1.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
                mAdView1.loadAd(adRequest1);
            }
        });
        MobileAds.initialize(getApplicationContext(), "ca-app-pub-9930376729131413~7469397260");

        // set title of the page
        name.setText(title);

        // back button on click
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // set up of recycler view
        recyclerView.setHasFixedSize(true);     // has a fixed size
        recyclerView.setLayoutManager(new LinearLayoutManager(this));   // set a linear layout manager
        userList = new ArrayList<>();   // get a list of the users
        userAdapter = new UserAdapter(this, userList, false);   // get the users adapter
        recyclerView.setAdapter(userAdapter);   // set the adapter class to the recycler view

        // create a idList
        idList = new ArrayList<>();

        // switch case for the title
        switch (title){
            case "Likes":
                getLikes(progressBar); // method to get the likes
                break;
            case "Following":
                getFollowing(progressBar);     // method to get the following
                break;
            case "Followers":
                getFollowers(progressBar);     // method to get the followers
                break;
            case "Views":
                getViews(progressBar);
                break;
        }
    }

    private void getViews(final ProgressBar progressBar1){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Quotes").child(id).child(getIntent().getStringExtra("quotesid")).child("views");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                idList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    idList.add(snapshot.getKey());
                }

                showUsers(progressBar1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    // method to get the likes in idList
    private  void getLikes(final ProgressBar progressBar1){
        // get a reference to database to likes
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Likes")
                .child(id); // id is postid
        // if values received
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // first, clear the idList
                idList.clear();
                // for loop to add userid in post likes to idList
                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                    idList.add(snapshot.getKey());
                }
                // method to show everyone in idList
                showUsers(progressBar1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    // method to get the following userid to idList
    private  void getFollowing(final ProgressBar progressBar1){
        // get a database reference to the follow thread
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Follow")
                .child(id).child("following");
        // if value received
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                idList.clear();     // first clear the idList
                // for loop to add users in idList
                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                    idList.add(snapshot.getKey());
                }
                // get the users
                showUsers(progressBar1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    // get the followers
    private  void getFollowers(final  ProgressBar progressBar1){
        // get the database reference
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Follow")
                .child(id).child("followers");
        // if value received
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                idList.clear();     // clear the idList
                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                    idList.add(snapshot.getKey());
                }
                showUsers(progressBar1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    // show the users
    private void showUsers(final ProgressBar progressBar1){
        // get the database reference
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");

        // if value received
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // clear user list
                userList.clear();
                // for loop to get the user data
                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                    User user = snapshot.getValue(User.class);  // get the user data in user class
                    for (String id: idList){
                        if(user.getId().equals(id)){
                            userList.add(user);
                        }
                    }
                }
                progressBar1.setVisibility(View.GONE);
                // tell the adapter
                userAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
