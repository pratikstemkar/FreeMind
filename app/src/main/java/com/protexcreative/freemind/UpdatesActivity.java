package com.protexcreative.freemind;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.protexcreative.freemind.Adapter.UpdatesAdapter;
import com.protexcreative.freemind.Adapter.UserAdapter;
import com.protexcreative.freemind.Model.Updates;
import com.protexcreative.freemind.Model.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UpdatesActivity extends AppCompatActivity {

    private AdView mAdView1;
    private RecyclerView recyclerView;
    private UpdatesAdapter updatesAdapter;
    private List<Updates> mUpdates;
    private ImageView close;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updates);

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

        close = findViewById(R.id.close);


        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        recyclerView = findViewById(R.id.recycler_view);
        final ProgressBar progressBar = findViewById(R.id.progressBar);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        mUpdates = new ArrayList<>();
        updatesAdapter = new UpdatesAdapter(getApplicationContext(), mUpdates);
        recyclerView.setAdapter(updatesAdapter);

        readUpdates(progressBar);
    }

    private void readUpdates(final ProgressBar progressBar){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Updates");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    mUpdates.clear();
                    for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                        Updates updates = snapshot.getValue(Updates.class);
                        mUpdates.add(updates);
                    }

                Collections.reverse(mUpdates);
                    updatesAdapter.notifyDataSetChanged();
                    progressBar.setVisibility(View.GONE);
                }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
