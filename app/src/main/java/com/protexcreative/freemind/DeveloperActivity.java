package com.protexcreative.freemind;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.protexcreative.freemind.Model.User;

public class DeveloperActivity extends AppCompatActivity {

    String dev1, dev2, dev3, dev4;
    TextView username, username1, username2, username3, fullname, fullname1, fullname2, fullname3;
    ImageView image_profile, image_profile1, image_profile2, image_profile3, close;
    CardView card_view, card_view4, card_view2, card_view1;


    private AdView mAdView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_developer);

        final User user = new User();

        username = findViewById(R.id.username);
        username1 = findViewById(R.id.username1);
        username2 = findViewById(R.id.username2);
        username3 = findViewById(R.id.username3);
        fullname = findViewById(R.id.fullname);
        fullname1 = findViewById(R.id.fullname1);
        fullname2 = findViewById(R.id.fullname2);
        fullname3 = findViewById(R.id.fullname3);
        image_profile = findViewById(R.id.image_profile);
        image_profile1 = findViewById(R.id.image_profile1);
        image_profile2 = findViewById(R.id.image_profile2);
        image_profile3 = findViewById(R.id.image_profile3);
        card_view = findViewById(R.id.card_view);
        card_view4 = findViewById(R.id.card_view4);
        card_view2 = findViewById(R.id.card_view2);
        card_view1 = findViewById(R.id.card_view1);
        close = findViewById(R.id.close);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        mAdView1 = findViewById(R.id.adView1);




        final AdRequest adRequest1 = new AdRequest.Builder().build();




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


        dev1 = "qqNknSMmuwZJZSAdr9FMwaIeBul1";
        dev2 = "AQ1WrP66kPhMkyZ6sijuR3efWUg2";
        dev3 = "1GyIGhJL7uTG5FfMZbnJ2vfpnMA3";
        dev4 = "s7sgpr58j2dhQvZqxhCmgq29pYm2";

        getUser(dev3, image_profile3, username3, fullname3);
        getUser(dev4, image_profile1, username1, fullname1);
        getUser(dev2, image_profile2, username2, fullname2);
        getUser(dev1, image_profile, username, fullname);

        card_view1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DeveloperActivity.this, HomeActivity.class);
                intent.putExtra("authorid", dev3);
                startActivity(intent);
            }
        });

        card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DeveloperActivity.this, HomeActivity.class);
                intent.putExtra("authorid", dev1);
                startActivity(intent);
            }
        });

        card_view4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DeveloperActivity.this, HomeActivity.class);
                intent.putExtra("authorid", dev4);
                startActivity(intent);
            }
        });

        card_view2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DeveloperActivity.this, HomeActivity.class);
                intent.putExtra("authorid", dev2);
                startActivity(intent);
            }
        });


    }

    private void getUser(String dev1, final ImageView imageView, final TextView username, final TextView fullname){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(dev1);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);

                Glide.with(getApplicationContext()).load(user.getImageurl()).into(imageView);
                username.setText(user.getUsername());
                fullname.setText(user.getFullname());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
