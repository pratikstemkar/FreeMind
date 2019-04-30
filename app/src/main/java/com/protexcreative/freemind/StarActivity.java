package com.protexcreative.freemind;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.protexcreative.freemind.Adapter.PostAdapter;
import com.protexcreative.freemind.Adapter.UserAdapter;
import com.protexcreative.freemind.Model.Post;
import com.protexcreative.freemind.Model.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StarActivity extends AppCompatActivity {

    TextView name;
    ImageView close;
    RecyclerView recyclerView;
    ProgressBar progressBar;
    String id, title;
    Switch anonym_switch;
    Boolean ano = false;

    // list idLIst
    List<String> idList;


    private AdView mAdView1;

    List<Post> postList;
    PostAdapter postAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_star);

        name = findViewById(R.id.title);
        close = findViewById(R.id.close);
        recyclerView = findViewById(R.id.recycler_view);
        progressBar = findViewById(R.id.progressBar);
        anonym_switch = findViewById(R.id.anonym_switch);


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

        // get intent and the extras
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        title = intent.getStringExtra("title");

        anonym_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (anonym_switch.isChecked()){
                    ano = true;
                    getYPosts(progressBar);
                } else
                {
                    ano = false;
                    getYPosts(progressBar);
                }
            }
        });

        name.setText(title);

        if (FirebaseAuth.getInstance().getCurrentUser().getUid().equals(id) && title.equals("Posts")){
            anonym_switch.setVisibility(View.VISIBLE);
        }

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // set up of recycler view
        recyclerView.setHasFixedSize(true);     // has a fixed size
        recyclerView.setLayoutManager(new LinearLayoutManager(this));   // set a linear layout manager
        postList = new ArrayList<>();   // get a list of the users
        postAdapter = new PostAdapter(this, postList, false);   // get the users adapter
        recyclerView.setAdapter(postAdapter);   // set the adapter class to the recycler view

        // create a idList
        idList = new ArrayList<>();

        // switch case for the title
        switch (title){
            case "Posts":
                getYPosts(progressBar); // method to get the likes
                break;
            case "Star Posts":
                getSPosts(progressBar);     // method to get the following
                break;
        }
    }

    private void getSPosts(final ProgressBar progressbrar) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Stars").child(id);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // first, clear the idList
                idList.clear();
                // for loop to add userid in post likes to idList
                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                    idList.add(snapshot.getKey());
                }
                showPosts(progressbrar);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void getYPosts(final ProgressBar progressbrar){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Posts");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                idList.clear();
                for (final DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    final Post post = snapshot.getValue(Post.class);
                    if (ano){
                        if (post.getAuthor().equals(id)){
                            idList.add(snapshot.getKey());
                        }
                        showPosts(progressbrar);
                    }  else{
                        if (post.getAuthor().equals(id) && !post.getAnonymous()){
                            idList.add(snapshot.getKey());
                        }
                        showPosts(progressbrar);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void showPosts(final ProgressBar progressbrar){
        // get the database reference
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Posts");

        // if value received
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                postList.clear();
                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                    Post post = snapshot.getValue(Post.class);
                    for (String id: idList){
                        if(post.getPostid().equals(id)){
                            postList.add(post);
                        }
                    }
                }
                Collections.reverse(postList);
                progressbrar.setVisibility(View.GONE);
                // tell the adapter
                postAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
