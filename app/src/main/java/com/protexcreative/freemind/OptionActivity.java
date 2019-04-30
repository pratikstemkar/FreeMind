package com.protexcreative.freemind;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.protexcreative.freemind.Fragment.ProfileFragment;

import org.w3c.dom.Text;

import java.util.HashMap;

import static com.protexcreative.freemind.App.CHANNEL_1_ID;

public class OptionActivity extends AppCompatActivity {

    private NotificationManagerCompat notificationManager;

    // initialise the variables
    ImageView close;
    TextView logout, aboutus, appupdates, aboutads, opensource, datapolicy, tou, developers, changeemail, changepassword;
    FirebaseUser firebaseUser;


    private AdView mAdView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option);

        // get the UI components ID
        notificationManager = NotificationManagerCompat.from(this);
        close = findViewById(R.id.close);
        logout = findViewById(R.id.logout);
        aboutus = findViewById(R.id.aboutus);
        appupdates = findViewById(R.id.appupdates);
        aboutads = findViewById(R.id.ads);
        opensource = findViewById(R.id.libraries);
        datapolicy = findViewById(R.id.datapolicy);
        tou = findViewById(R.id.tou);
        developers = findViewById(R.id.developers);
        changeemail = findViewById(R.id.changeemail);
        changepassword = findViewById(R.id.changepassword);

        changepassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OptionActivity.this, ChangePasswordActivity.class));
            }
        });

        changeemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OptionActivity.this, ChangeEmailActivity.class));
            }
        });

        developers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OptionActivity.this, DeveloperActivity.class));
            }
        });

        tou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OptionActivity.this, TermsActivity.class));
            }
        });

        datapolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OptionActivity.this, DataPolicyActivity.class));
            }
        });

        opensource.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OptionActivity.this, OpenSourceActivity.class));
            }
        });

        aboutads.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OptionActivity.this, AdsActivity.class));
            }
        });


        appupdates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OptionActivity.this, UpdatesActivity.class));
            }
        });



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


        //Notification

        // set On Click Listener to about us
        aboutus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // start new activity
                startActivity(new Intent(OptionActivity.this, AboutActivity.class));
            }
        });

        // close on click
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();       // remove the activtity from memory
            }
        });

        // set logout on click
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(OptionActivity.this);
                alertDialog.setTitle("Logout?");

                alertDialog.setPositiveButton("Logout",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                logoutA();
                            }
                        });
                alertDialog.setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }
        });



    }

    private void logoutA(){
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("token_id", "");
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
        reference.updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                // signout method of the FirebaseAuth
                FirebaseAuth.getInstance().signOut();
                // start new activity
                Intent intent = new Intent(OptionActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }

}
