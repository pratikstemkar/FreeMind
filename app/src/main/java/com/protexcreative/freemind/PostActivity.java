package com.protexcreative.freemind;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.protexcreative.freemind.Model.User;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class PostActivity extends AppCompatActivity {

    // Initialise the variables.
    int font = 1;
    private InterstitialAd mInterstitialAd;
    ImageView close, image_profile;
    EditText txt_post;
    Switch switch1;
    Spinner optionspin;
    Boolean anonym = false;
    private AdView mAdView;
    TextView username, category, post;
    RadioGroup r1;
    RadioButton relationships, work, friends, education, family, hopes, mystory, mentalhealth;
    Button comfortaa, amasticsc, astloch, baloo, baloochetan, bangers, blackops, bowl, facinate, fredericka, germania, gochi, vibes, homemade, flower, luckyguy, marck, margarine, monoton, dafoe, shepphard, neucha, pacifico, patua, poiret, pressstart, rocksalt, sacramento, shadow, elite;

    // Method OnCreation of the activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        // Initialise the Mobile Ads with the publisher ID
        MobileAds.initialize(this,
                "ca-app-pub-9930376729131413~7469397260");
        // Create an Object
        mInterstitialAd = new InterstitialAd(this);
        // Set Ad Unit ID
        mInterstitialAd.setAdUnitId("ca-app-pub-9930376729131413/2715478622");
        // Load Ad in the Background
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        // font
        comfortaa = findViewById(R.id.comfortaa);
        amasticsc = findViewById(R.id.amaticsc);
        astloch = findViewById(R.id.astloch);
        baloo = findViewById(R.id.baloo);
        baloochetan = findViewById(R.id.baloochetan);
        bangers = findViewById(R.id.bangers);
        blackops = findViewById(R.id.blackops);
        bowl = findViewById(R.id.bowl);
        facinate = findViewById(R.id.facinate);
        fredericka = findViewById(R.id.fredericka);
        germania = findViewById(R.id.germania);
        gochi = findViewById(R.id.gochi);
        vibes = findViewById(R.id.vibes);
        homemade = findViewById(R.id.homemade);
        flower = findViewById(R.id.flower);
        luckyguy = findViewById(R.id.luckyguy);
        marck = findViewById(R.id.marck);
        margarine = findViewById(R.id.margarine);
        monoton = findViewById(R.id.monoton);
        dafoe = findViewById(R.id.dafoe);
        shepphard = findViewById(R.id.shepphard);
        neucha = findViewById(R.id.neucha);
        pacifico = findViewById(R.id.pacifico);
        patua = findViewById(R.id.patua);
        poiret = findViewById(R.id.poiret);
        pressstart = findViewById(R.id.pressstart);
        rocksalt = findViewById(R.id.rocksalt);
        sacramento = findViewById(R.id.sacramento);
        shadow = findViewById(R.id.shadow);
        elite = findViewById(R.id.elite);


        // Get the ID of the UI components
        close = findViewById(R.id.close);
        post = findViewById(R.id.post);
        txt_post= findViewById(R.id.txt_post);
        switch1 = findViewById(R.id.switch1);
        username = findViewById(R.id.username);
        image_profile = findViewById(R.id.image_profile);
        category = findViewById(R.id.category);
        r1 = findViewById(R.id.r1);
        relationships = findViewById(R.id.relationships);
        work = findViewById(R.id.work);
        friends = findViewById(R.id.friends);
        education = findViewById(R.id.education);
        family = findViewById(R.id.family);
        hopes = findViewById(R.id.hopes);
        friends = findViewById(R.id.friends);
        mystory = findViewById(R.id.mystory);
        mentalhealth = findViewById(R.id.mentalhealth);

        post.setEnabled(true);

        mAdView = findViewById(R.id.adView);

        final AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                mAdView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
                mAdView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
                mAdView.loadAd(adRequest);
            }
        });

        MobileAds.initialize(getApplicationContext(), "ca-app-pub-9930376729131413~7469397260");


        // Set an OnClick Listener on the Radio Group
        r1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                txt_post.setBackgroundColor(getC());    // set the background color of the text
                category.setText(getCat());     // set the category
                category.setTextColor(getC());      // set the text color of the category
            }
        });

        // font
        comfortaa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                font = 1;
                txt_post.setTypeface(ResourcesCompat.getFont(PostActivity.this, R.font.comfortaa_regular));
                comfortaa.setBackgroundResource(R.drawable.button_selected);
                amasticsc.setBackgroundResource(R.drawable.button);
                astloch.setBackgroundResource(R.drawable.button);
                baloo.setBackgroundResource(R.drawable.button);
                baloochetan.setBackgroundResource(R.drawable.button);
                bangers.setBackgroundResource(R.drawable.button);
                blackops.setBackgroundResource(R.drawable.button);
                bowl.setBackgroundResource(R.drawable.button);
                facinate.setBackgroundResource(R.drawable.button);
                fredericka.setBackgroundResource(R.drawable.button);
                germania.setBackgroundResource(R.drawable.button);
                gochi.setBackgroundResource(R.drawable.button);
                vibes.setBackgroundResource(R.drawable.button);
                homemade.setBackgroundResource(R.drawable.button);
                flower.setBackgroundResource(R.drawable.button);
                luckyguy.setBackgroundResource(R.drawable.button);
                marck.setBackgroundResource(R.drawable.button);
                margarine.setBackgroundResource(R.drawable.button);
                monoton.setBackgroundResource(R.drawable.button);
                dafoe.setBackgroundResource(R.drawable.button);
                shepphard.setBackgroundResource(R.drawable.button);
                neucha.setBackgroundResource(R.drawable.button);
                pacifico.setBackgroundResource(R.drawable.button);
                patua.setBackgroundResource(R.drawable.button);
                poiret.setBackgroundResource(R.drawable.button);
                pressstart.setBackgroundResource(R.drawable.button);
                rocksalt.setBackgroundResource(R.drawable.button);
                sacramento.setBackgroundResource(R.drawable.button);
                shadow.setBackgroundResource(R.drawable.button);
                elite.setBackgroundResource(R.drawable.button);
            }
        });
        amasticsc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                font = 2;
                txt_post.setTypeface(ResourcesCompat.getFont(PostActivity.this, R.font.amaticsc_regular));
                comfortaa.setBackgroundResource(R.drawable.button);
                amasticsc.setBackgroundResource(R.drawable.button_selected);
                astloch.setBackgroundResource(R.drawable.button);
                baloo.setBackgroundResource(R.drawable.button);
                baloochetan.setBackgroundResource(R.drawable.button);
                bangers.setBackgroundResource(R.drawable.button);
                blackops.setBackgroundResource(R.drawable.button);
                bowl.setBackgroundResource(R.drawable.button);
                facinate.setBackgroundResource(R.drawable.button);
                fredericka.setBackgroundResource(R.drawable.button);
                germania.setBackgroundResource(R.drawable.button);
                gochi.setBackgroundResource(R.drawable.button);
                vibes.setBackgroundResource(R.drawable.button);
                homemade.setBackgroundResource(R.drawable.button);
                flower.setBackgroundResource(R.drawable.button);
                luckyguy.setBackgroundResource(R.drawable.button);
                marck.setBackgroundResource(R.drawable.button);
                margarine.setBackgroundResource(R.drawable.button);
                monoton.setBackgroundResource(R.drawable.button);
                dafoe.setBackgroundResource(R.drawable.button);
                shepphard.setBackgroundResource(R.drawable.button);
                neucha.setBackgroundResource(R.drawable.button);
                pacifico.setBackgroundResource(R.drawable.button);
                patua.setBackgroundResource(R.drawable.button);
                poiret.setBackgroundResource(R.drawable.button);
                pressstart.setBackgroundResource(R.drawable.button);
                rocksalt.setBackgroundResource(R.drawable.button);
                sacramento.setBackgroundResource(R.drawable.button);
                shadow.setBackgroundResource(R.drawable.button);
                elite.setBackgroundResource(R.drawable.button);
            }
        });
        astloch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                font = 3;
                txt_post.setTypeface(ResourcesCompat.getFont(PostActivity.this, R.font.astlock_regular));
                comfortaa.setBackgroundResource(R.drawable.button);
                amasticsc.setBackgroundResource(R.drawable.button);
                astloch.setBackgroundResource(R.drawable.button_selected);
                baloo.setBackgroundResource(R.drawable.button);
                baloochetan.setBackgroundResource(R.drawable.button);
                bangers.setBackgroundResource(R.drawable.button);
                blackops.setBackgroundResource(R.drawable.button);
                bowl.setBackgroundResource(R.drawable.button);
                facinate.setBackgroundResource(R.drawable.button);
                fredericka.setBackgroundResource(R.drawable.button);
                germania.setBackgroundResource(R.drawable.button);
                gochi.setBackgroundResource(R.drawable.button);
                vibes.setBackgroundResource(R.drawable.button);
                homemade.setBackgroundResource(R.drawable.button);
                flower.setBackgroundResource(R.drawable.button);
                luckyguy.setBackgroundResource(R.drawable.button);
                marck.setBackgroundResource(R.drawable.button);
                margarine.setBackgroundResource(R.drawable.button);
                monoton.setBackgroundResource(R.drawable.button);
                dafoe.setBackgroundResource(R.drawable.button);
                shepphard.setBackgroundResource(R.drawable.button);
                neucha.setBackgroundResource(R.drawable.button);
                pacifico.setBackgroundResource(R.drawable.button);
                patua.setBackgroundResource(R.drawable.button);
                poiret.setBackgroundResource(R.drawable.button);
                pressstart.setBackgroundResource(R.drawable.button);
                rocksalt.setBackgroundResource(R.drawable.button);
                sacramento.setBackgroundResource(R.drawable.button);
                shadow.setBackgroundResource(R.drawable.button);
                elite.setBackgroundResource(R.drawable.button);
            }
        });
        baloo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                font = 4;
                txt_post.setTypeface(ResourcesCompat.getFont(PostActivity.this, R.font.baloo_regular));
                comfortaa.setBackgroundResource(R.drawable.button);
                amasticsc.setBackgroundResource(R.drawable.button);
                astloch.setBackgroundResource(R.drawable.button);
                baloo.setBackgroundResource(R.drawable.button_selected);
                baloochetan.setBackgroundResource(R.drawable.button);
                bangers.setBackgroundResource(R.drawable.button);
                blackops.setBackgroundResource(R.drawable.button);
                bowl.setBackgroundResource(R.drawable.button);
                facinate.setBackgroundResource(R.drawable.button);
                fredericka.setBackgroundResource(R.drawable.button);
                germania.setBackgroundResource(R.drawable.button);
                gochi.setBackgroundResource(R.drawable.button);
                vibes.setBackgroundResource(R.drawable.button);
                homemade.setBackgroundResource(R.drawable.button);
                flower.setBackgroundResource(R.drawable.button);
                luckyguy.setBackgroundResource(R.drawable.button);
                marck.setBackgroundResource(R.drawable.button);
                margarine.setBackgroundResource(R.drawable.button);
                monoton.setBackgroundResource(R.drawable.button);
                dafoe.setBackgroundResource(R.drawable.button);
                shepphard.setBackgroundResource(R.drawable.button);
                neucha.setBackgroundResource(R.drawable.button);
                pacifico.setBackgroundResource(R.drawable.button);
                patua.setBackgroundResource(R.drawable.button);
                poiret.setBackgroundResource(R.drawable.button);
                pressstart.setBackgroundResource(R.drawable.button);
                rocksalt.setBackgroundResource(R.drawable.button);
                sacramento.setBackgroundResource(R.drawable.button);
                shadow.setBackgroundResource(R.drawable.button);
                elite.setBackgroundResource(R.drawable.button);
            }
        });
        baloochetan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                font = 5;
                txt_post.setTypeface(ResourcesCompat.getFont(PostActivity.this, R.font.baloochetan_regular));
                comfortaa.setBackgroundResource(R.drawable.button);
                amasticsc.setBackgroundResource(R.drawable.button);
                astloch.setBackgroundResource(R.drawable.button);
                baloo.setBackgroundResource(R.drawable.button);
                baloochetan.setBackgroundResource(R.drawable.button_selected);
                bangers.setBackgroundResource(R.drawable.button);
                blackops.setBackgroundResource(R.drawable.button);
                bowl.setBackgroundResource(R.drawable.button);
                facinate.setBackgroundResource(R.drawable.button);
                fredericka.setBackgroundResource(R.drawable.button);
                germania.setBackgroundResource(R.drawable.button);
                gochi.setBackgroundResource(R.drawable.button);
                vibes.setBackgroundResource(R.drawable.button);
                homemade.setBackgroundResource(R.drawable.button);
                flower.setBackgroundResource(R.drawable.button);
                luckyguy.setBackgroundResource(R.drawable.button);
                marck.setBackgroundResource(R.drawable.button);
                margarine.setBackgroundResource(R.drawable.button);
                monoton.setBackgroundResource(R.drawable.button);
                dafoe.setBackgroundResource(R.drawable.button);
                shepphard.setBackgroundResource(R.drawable.button);
                neucha.setBackgroundResource(R.drawable.button);
                pacifico.setBackgroundResource(R.drawable.button);
                patua.setBackgroundResource(R.drawable.button);
                poiret.setBackgroundResource(R.drawable.button);
                pressstart.setBackgroundResource(R.drawable.button);
                rocksalt.setBackgroundResource(R.drawable.button);
                sacramento.setBackgroundResource(R.drawable.button);
                shadow.setBackgroundResource(R.drawable.button);
                elite.setBackgroundResource(R.drawable.button);
            }
        });
        bangers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                font = 6;
                txt_post.setTypeface(ResourcesCompat.getFont(PostActivity.this, R.font.bangers_regular));
                comfortaa.setBackgroundResource(R.drawable.button);
                amasticsc.setBackgroundResource(R.drawable.button);
                astloch.setBackgroundResource(R.drawable.button);
                baloo.setBackgroundResource(R.drawable.button);
                baloochetan.setBackgroundResource(R.drawable.button);
                bangers.setBackgroundResource(R.drawable.button_selected);
                blackops.setBackgroundResource(R.drawable.button);
                bowl.setBackgroundResource(R.drawable.button);
                facinate.setBackgroundResource(R.drawable.button);
                fredericka.setBackgroundResource(R.drawable.button);
                germania.setBackgroundResource(R.drawable.button);
                gochi.setBackgroundResource(R.drawable.button);
                vibes.setBackgroundResource(R.drawable.button);
                homemade.setBackgroundResource(R.drawable.button);
                flower.setBackgroundResource(R.drawable.button);
                luckyguy.setBackgroundResource(R.drawable.button);
                marck.setBackgroundResource(R.drawable.button);
                margarine.setBackgroundResource(R.drawable.button);
                monoton.setBackgroundResource(R.drawable.button);
                dafoe.setBackgroundResource(R.drawable.button);
                shepphard.setBackgroundResource(R.drawable.button);
                neucha.setBackgroundResource(R.drawable.button);
                pacifico.setBackgroundResource(R.drawable.button);
                patua.setBackgroundResource(R.drawable.button);
                poiret.setBackgroundResource(R.drawable.button);
                pressstart.setBackgroundResource(R.drawable.button);
                rocksalt.setBackgroundResource(R.drawable.button);
                sacramento.setBackgroundResource(R.drawable.button);
                shadow.setBackgroundResource(R.drawable.button);
                elite.setBackgroundResource(R.drawable.button);
            }
        });
        blackops.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                font = 7;
                txt_post.setTypeface(ResourcesCompat.getFont(PostActivity.this, R.font.blackopsone_regular));
                comfortaa.setBackgroundResource(R.drawable.button);
                amasticsc.setBackgroundResource(R.drawable.button);
                astloch.setBackgroundResource(R.drawable.button);
                baloo.setBackgroundResource(R.drawable.button);
                baloochetan.setBackgroundResource(R.drawable.button);
                bangers.setBackgroundResource(R.drawable.button);
                blackops.setBackgroundResource(R.drawable.button_selected);
                bowl.setBackgroundResource(R.drawable.button);
                facinate.setBackgroundResource(R.drawable.button);
                fredericka.setBackgroundResource(R.drawable.button);
                germania.setBackgroundResource(R.drawable.button);
                gochi.setBackgroundResource(R.drawable.button);
                vibes.setBackgroundResource(R.drawable.button);
                homemade.setBackgroundResource(R.drawable.button);
                flower.setBackgroundResource(R.drawable.button);
                luckyguy.setBackgroundResource(R.drawable.button);
                marck.setBackgroundResource(R.drawable.button);
                margarine.setBackgroundResource(R.drawable.button);
                monoton.setBackgroundResource(R.drawable.button);
                dafoe.setBackgroundResource(R.drawable.button);
                shepphard.setBackgroundResource(R.drawable.button);
                neucha.setBackgroundResource(R.drawable.button);
                pacifico.setBackgroundResource(R.drawable.button);
                patua.setBackgroundResource(R.drawable.button);
                poiret.setBackgroundResource(R.drawable.button);
                pressstart.setBackgroundResource(R.drawable.button);
                rocksalt.setBackgroundResource(R.drawable.button);
                sacramento.setBackgroundResource(R.drawable.button);
                shadow.setBackgroundResource(R.drawable.button);
                elite.setBackgroundResource(R.drawable.button);
            }
        });
         bowl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                font = 8;
                txt_post.setTypeface(ResourcesCompat.getFont(PostActivity.this, R.font.bowlbyone_regular));
                comfortaa.setBackgroundResource(R.drawable.button);
                amasticsc.setBackgroundResource(R.drawable.button);
                astloch.setBackgroundResource(R.drawable.button);
                baloo.setBackgroundResource(R.drawable.button);
                baloochetan.setBackgroundResource(R.drawable.button);
                bangers.setBackgroundResource(R.drawable.button);
                blackops.setBackgroundResource(R.drawable.button);
                bowl.setBackgroundResource(R.drawable.button_selected);
                facinate.setBackgroundResource(R.drawable.button);
                fredericka.setBackgroundResource(R.drawable.button);
                germania.setBackgroundResource(R.drawable.button);
                gochi.setBackgroundResource(R.drawable.button);
                vibes.setBackgroundResource(R.drawable.button);
                homemade.setBackgroundResource(R.drawable.button);
                flower.setBackgroundResource(R.drawable.button);
                luckyguy.setBackgroundResource(R.drawable.button);
                marck.setBackgroundResource(R.drawable.button);
                margarine.setBackgroundResource(R.drawable.button);
                monoton.setBackgroundResource(R.drawable.button);
                dafoe.setBackgroundResource(R.drawable.button);
                shepphard.setBackgroundResource(R.drawable.button);
                neucha.setBackgroundResource(R.drawable.button);
                pacifico.setBackgroundResource(R.drawable.button);
                patua.setBackgroundResource(R.drawable.button);
                poiret.setBackgroundResource(R.drawable.button);
                pressstart.setBackgroundResource(R.drawable.button);
                rocksalt.setBackgroundResource(R.drawable.button);
                sacramento.setBackgroundResource(R.drawable.button);
                shadow.setBackgroundResource(R.drawable.button);
                elite.setBackgroundResource(R.drawable.button);
            }
        });
        facinate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                font = 9;
                txt_post.setTypeface(ResourcesCompat.getFont(PostActivity.this, R.font.facinateinline_regular));
                comfortaa.setBackgroundResource(R.drawable.button);
                amasticsc.setBackgroundResource(R.drawable.button);
                astloch.setBackgroundResource(R.drawable.button);
                baloo.setBackgroundResource(R.drawable.button);
                baloochetan.setBackgroundResource(R.drawable.button);
                bangers.setBackgroundResource(R.drawable.button);
                blackops.setBackgroundResource(R.drawable.button);
                bowl.setBackgroundResource(R.drawable.button);
                facinate.setBackgroundResource(R.drawable.button_selected);
                fredericka.setBackgroundResource(R.drawable.button);
                germania.setBackgroundResource(R.drawable.button);
                gochi.setBackgroundResource(R.drawable.button);
                vibes.setBackgroundResource(R.drawable.button);
                homemade.setBackgroundResource(R.drawable.button);
                flower.setBackgroundResource(R.drawable.button);
                luckyguy.setBackgroundResource(R.drawable.button);
                marck.setBackgroundResource(R.drawable.button);
                margarine.setBackgroundResource(R.drawable.button);
                monoton.setBackgroundResource(R.drawable.button);
                dafoe.setBackgroundResource(R.drawable.button);
                shepphard.setBackgroundResource(R.drawable.button);
                neucha.setBackgroundResource(R.drawable.button);
                pacifico.setBackgroundResource(R.drawable.button);
                patua.setBackgroundResource(R.drawable.button);
                poiret.setBackgroundResource(R.drawable.button);
                pressstart.setBackgroundResource(R.drawable.button);
                rocksalt.setBackgroundResource(R.drawable.button);
                sacramento.setBackgroundResource(R.drawable.button);
                shadow.setBackgroundResource(R.drawable.button);
                elite.setBackgroundResource(R.drawable.button);
            }
        });
        fredericka.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                font = 10;
                txt_post.setTypeface(ResourcesCompat.getFont(PostActivity.this, R.font.frederickathegreat_regular));
                comfortaa.setBackgroundResource(R.drawable.button);
                amasticsc.setBackgroundResource(R.drawable.button);
                astloch.setBackgroundResource(R.drawable.button);
                baloo.setBackgroundResource(R.drawable.button);
                baloochetan.setBackgroundResource(R.drawable.button);
                bangers.setBackgroundResource(R.drawable.button);
                blackops.setBackgroundResource(R.drawable.button);
                bowl.setBackgroundResource(R.drawable.button);
                facinate.setBackgroundResource(R.drawable.button);
                fredericka.setBackgroundResource(R.drawable.button_selected);
                germania.setBackgroundResource(R.drawable.button);
                gochi.setBackgroundResource(R.drawable.button);
                vibes.setBackgroundResource(R.drawable.button);
                homemade.setBackgroundResource(R.drawable.button);
                flower.setBackgroundResource(R.drawable.button);
                luckyguy.setBackgroundResource(R.drawable.button);
                marck.setBackgroundResource(R.drawable.button);
                margarine.setBackgroundResource(R.drawable.button);
                monoton.setBackgroundResource(R.drawable.button);
                dafoe.setBackgroundResource(R.drawable.button);
                shepphard.setBackgroundResource(R.drawable.button);
                neucha.setBackgroundResource(R.drawable.button);
                pacifico.setBackgroundResource(R.drawable.button);
                patua.setBackgroundResource(R.drawable.button);
                poiret.setBackgroundResource(R.drawable.button);
                pressstart.setBackgroundResource(R.drawable.button);
                rocksalt.setBackgroundResource(R.drawable.button);
                sacramento.setBackgroundResource(R.drawable.button);
                shadow.setBackgroundResource(R.drawable.button);
                elite.setBackgroundResource(R.drawable.button);
            }
        });
        germania.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                font = 11;
                txt_post.setTypeface(ResourcesCompat.getFont(PostActivity.this, R.font.germaniaone_regular));
                comfortaa.setBackgroundResource(R.drawable.button);
                amasticsc.setBackgroundResource(R.drawable.button);
                astloch.setBackgroundResource(R.drawable.button);
                baloo.setBackgroundResource(R.drawable.button);
                baloochetan.setBackgroundResource(R.drawable.button);
                bangers.setBackgroundResource(R.drawable.button);
                blackops.setBackgroundResource(R.drawable.button);
                bowl.setBackgroundResource(R.drawable.button);
                facinate.setBackgroundResource(R.drawable.button);
                fredericka.setBackgroundResource(R.drawable.button);
                germania.setBackgroundResource(R.drawable.button_selected);
                gochi.setBackgroundResource(R.drawable.button);
                vibes.setBackgroundResource(R.drawable.button);
                homemade.setBackgroundResource(R.drawable.button);
                flower.setBackgroundResource(R.drawable.button);
                luckyguy.setBackgroundResource(R.drawable.button);
                marck.setBackgroundResource(R.drawable.button);
                margarine.setBackgroundResource(R.drawable.button);
                monoton.setBackgroundResource(R.drawable.button);
                dafoe.setBackgroundResource(R.drawable.button);
                shepphard.setBackgroundResource(R.drawable.button);
                neucha.setBackgroundResource(R.drawable.button);
                pacifico.setBackgroundResource(R.drawable.button);
                patua.setBackgroundResource(R.drawable.button);
                poiret.setBackgroundResource(R.drawable.button);
                pressstart.setBackgroundResource(R.drawable.button);
                rocksalt.setBackgroundResource(R.drawable.button);
                sacramento.setBackgroundResource(R.drawable.button);
                shadow.setBackgroundResource(R.drawable.button);
                elite.setBackgroundResource(R.drawable.button);
            }
        });
        gochi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                font = 12;
                txt_post.setTypeface(ResourcesCompat.getFont(PostActivity.this, R.font.gochihand_regular));
                comfortaa.setBackgroundResource(R.drawable.button);
                amasticsc.setBackgroundResource(R.drawable.button);
                astloch.setBackgroundResource(R.drawable.button);
                baloo.setBackgroundResource(R.drawable.button);
                baloochetan.setBackgroundResource(R.drawable.button);
                bangers.setBackgroundResource(R.drawable.button);
                blackops.setBackgroundResource(R.drawable.button);
                bowl.setBackgroundResource(R.drawable.button);
                facinate.setBackgroundResource(R.drawable.button);
                fredericka.setBackgroundResource(R.drawable.button);
                germania.setBackgroundResource(R.drawable.button);
                gochi.setBackgroundResource(R.drawable.button_selected);
                vibes.setBackgroundResource(R.drawable.button);
                homemade.setBackgroundResource(R.drawable.button);
                flower.setBackgroundResource(R.drawable.button);
                luckyguy.setBackgroundResource(R.drawable.button);
                marck.setBackgroundResource(R.drawable.button);
                margarine.setBackgroundResource(R.drawable.button);
                monoton.setBackgroundResource(R.drawable.button);
                dafoe.setBackgroundResource(R.drawable.button);
                shepphard.setBackgroundResource(R.drawable.button);
                neucha.setBackgroundResource(R.drawable.button);
                pacifico.setBackgroundResource(R.drawable.button);
                patua.setBackgroundResource(R.drawable.button);
                poiret.setBackgroundResource(R.drawable.button);
                pressstart.setBackgroundResource(R.drawable.button);
                rocksalt.setBackgroundResource(R.drawable.button);
                sacramento.setBackgroundResource(R.drawable.button);
                shadow.setBackgroundResource(R.drawable.button);
                elite.setBackgroundResource(R.drawable.button);
            }
        });
        vibes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                font  = 13;
                txt_post.setTypeface(ResourcesCompat.getFont(PostActivity.this, R.font.greatvibes_regular));
                comfortaa.setBackgroundResource(R.drawable.button);
                amasticsc.setBackgroundResource(R.drawable.button);
                astloch.setBackgroundResource(R.drawable.button);
                baloo.setBackgroundResource(R.drawable.button);
                baloochetan.setBackgroundResource(R.drawable.button);
                bangers.setBackgroundResource(R.drawable.button);
                blackops.setBackgroundResource(R.drawable.button);
                bowl.setBackgroundResource(R.drawable.button);
                facinate.setBackgroundResource(R.drawable.button);
                fredericka.setBackgroundResource(R.drawable.button);
                germania.setBackgroundResource(R.drawable.button);
                gochi.setBackgroundResource(R.drawable.button);
                vibes.setBackgroundResource(R.drawable.button_selected);
                homemade.setBackgroundResource(R.drawable.button);
                flower.setBackgroundResource(R.drawable.button);
                luckyguy.setBackgroundResource(R.drawable.button);
                marck.setBackgroundResource(R.drawable.button);
                margarine.setBackgroundResource(R.drawable.button);
                monoton.setBackgroundResource(R.drawable.button);
                dafoe.setBackgroundResource(R.drawable.button);
                shepphard.setBackgroundResource(R.drawable.button);
                neucha.setBackgroundResource(R.drawable.button);
                pacifico.setBackgroundResource(R.drawable.button);
                patua.setBackgroundResource(R.drawable.button);
                poiret.setBackgroundResource(R.drawable.button);
                pressstart.setBackgroundResource(R.drawable.button);
                rocksalt.setBackgroundResource(R.drawable.button);
                sacramento.setBackgroundResource(R.drawable.button);
                shadow.setBackgroundResource(R.drawable.button);
                elite.setBackgroundResource(R.drawable.button);
            }
        });
        homemade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                font = 14;
                txt_post.setTypeface(ResourcesCompat.getFont(PostActivity.this, R.font.homemadeapples_regular));
                comfortaa.setBackgroundResource(R.drawable.button);
                amasticsc.setBackgroundResource(R.drawable.button);
                astloch.setBackgroundResource(R.drawable.button);
                baloo.setBackgroundResource(R.drawable.button);
                baloochetan.setBackgroundResource(R.drawable.button);
                bangers.setBackgroundResource(R.drawable.button);
                blackops.setBackgroundResource(R.drawable.button);
                bowl.setBackgroundResource(R.drawable.button);
                facinate.setBackgroundResource(R.drawable.button);
                fredericka.setBackgroundResource(R.drawable.button);
                germania.setBackgroundResource(R.drawable.button);
                gochi.setBackgroundResource(R.drawable.button);
                vibes.setBackgroundResource(R.drawable.button);
                homemade.setBackgroundResource(R.drawable.button_selected);
                flower.setBackgroundResource(R.drawable.button);
                luckyguy.setBackgroundResource(R.drawable.button);
                marck.setBackgroundResource(R.drawable.button);
                margarine.setBackgroundResource(R.drawable.button);
                monoton.setBackgroundResource(R.drawable.button);
                dafoe.setBackgroundResource(R.drawable.button);
                shepphard.setBackgroundResource(R.drawable.button);
                neucha.setBackgroundResource(R.drawable.button);
                pacifico.setBackgroundResource(R.drawable.button);
                patua.setBackgroundResource(R.drawable.button);
                poiret.setBackgroundResource(R.drawable.button);
                pressstart.setBackgroundResource(R.drawable.button);
                rocksalt.setBackgroundResource(R.drawable.button);
                sacramento.setBackgroundResource(R.drawable.button);
                shadow.setBackgroundResource(R.drawable.button);
                elite.setBackgroundResource(R.drawable.button);
            }
        });
        flower.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                font = 15;
                txt_post.setTypeface(ResourcesCompat.getFont(PostActivity.this, R.font.indieflower_regular));
                comfortaa.setBackgroundResource(R.drawable.button);
                amasticsc.setBackgroundResource(R.drawable.button);
                astloch.setBackgroundResource(R.drawable.button);
                baloo.setBackgroundResource(R.drawable.button);
                baloochetan.setBackgroundResource(R.drawable.button);
                bangers.setBackgroundResource(R.drawable.button);
                blackops.setBackgroundResource(R.drawable.button);
                bowl.setBackgroundResource(R.drawable.button);
                facinate.setBackgroundResource(R.drawable.button);
                fredericka.setBackgroundResource(R.drawable.button);
                germania.setBackgroundResource(R.drawable.button);
                gochi.setBackgroundResource(R.drawable.button);
                vibes.setBackgroundResource(R.drawable.button);
                homemade.setBackgroundResource(R.drawable.button);
                flower.setBackgroundResource(R.drawable.button_selected);
                luckyguy.setBackgroundResource(R.drawable.button);
                marck.setBackgroundResource(R.drawable.button);
                margarine.setBackgroundResource(R.drawable.button);
                monoton.setBackgroundResource(R.drawable.button);
                dafoe.setBackgroundResource(R.drawable.button);
                shepphard.setBackgroundResource(R.drawable.button);
                neucha.setBackgroundResource(R.drawable.button);
                pacifico.setBackgroundResource(R.drawable.button);
                patua.setBackgroundResource(R.drawable.button);
                poiret.setBackgroundResource(R.drawable.button);
                pressstart.setBackgroundResource(R.drawable.button);
                rocksalt.setBackgroundResource(R.drawable.button);
                sacramento.setBackgroundResource(R.drawable.button);
                shadow.setBackgroundResource(R.drawable.button);
                elite.setBackgroundResource(R.drawable.button);
            }
        });
        luckyguy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                font = 16;
                txt_post.setTypeface(ResourcesCompat.getFont(PostActivity.this, R.font.luckiestguy_regular));
                comfortaa.setBackgroundResource(R.drawable.button);
                amasticsc.setBackgroundResource(R.drawable.button);
                astloch.setBackgroundResource(R.drawable.button);
                baloo.setBackgroundResource(R.drawable.button);
                baloochetan.setBackgroundResource(R.drawable.button);
                bangers.setBackgroundResource(R.drawable.button);
                blackops.setBackgroundResource(R.drawable.button);
                bowl.setBackgroundResource(R.drawable.button);
                facinate.setBackgroundResource(R.drawable.button);
                fredericka.setBackgroundResource(R.drawable.button);
                germania.setBackgroundResource(R.drawable.button);
                gochi.setBackgroundResource(R.drawable.button);
                vibes.setBackgroundResource(R.drawable.button);
                homemade.setBackgroundResource(R.drawable.button);
                flower.setBackgroundResource(R.drawable.button);
                luckyguy.setBackgroundResource(R.drawable.button_selected);
                marck.setBackgroundResource(R.drawable.button);
                margarine.setBackgroundResource(R.drawable.button);
                monoton.setBackgroundResource(R.drawable.button);
                dafoe.setBackgroundResource(R.drawable.button);
                shepphard.setBackgroundResource(R.drawable.button);
                neucha.setBackgroundResource(R.drawable.button);
                pacifico.setBackgroundResource(R.drawable.button);
                patua.setBackgroundResource(R.drawable.button);
                poiret.setBackgroundResource(R.drawable.button);
                pressstart.setBackgroundResource(R.drawable.button);
                rocksalt.setBackgroundResource(R.drawable.button);
                sacramento.setBackgroundResource(R.drawable.button);
                shadow.setBackgroundResource(R.drawable.button);
                elite.setBackgroundResource(R.drawable.button);
            }
        });
        marck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                font = 17;
                txt_post.setTypeface(ResourcesCompat.getFont(PostActivity.this, R.font.marckscript_regular));
                comfortaa.setBackgroundResource(R.drawable.button);
                amasticsc.setBackgroundResource(R.drawable.button);
                astloch.setBackgroundResource(R.drawable.button);
                baloo.setBackgroundResource(R.drawable.button);
                baloochetan.setBackgroundResource(R.drawable.button);
                bangers.setBackgroundResource(R.drawable.button);
                blackops.setBackgroundResource(R.drawable.button);
                bowl.setBackgroundResource(R.drawable.button);
                facinate.setBackgroundResource(R.drawable.button);
                fredericka.setBackgroundResource(R.drawable.button);
                germania.setBackgroundResource(R.drawable.button);
                gochi.setBackgroundResource(R.drawable.button);
                vibes.setBackgroundResource(R.drawable.button);
                homemade.setBackgroundResource(R.drawable.button);
                flower.setBackgroundResource(R.drawable.button);
                luckyguy.setBackgroundResource(R.drawable.button);
                marck.setBackgroundResource(R.drawable.button_selected);
                margarine.setBackgroundResource(R.drawable.button);
                monoton.setBackgroundResource(R.drawable.button);
                dafoe.setBackgroundResource(R.drawable.button);
                shepphard.setBackgroundResource(R.drawable.button);
                neucha.setBackgroundResource(R.drawable.button);
                pacifico.setBackgroundResource(R.drawable.button);
                patua.setBackgroundResource(R.drawable.button);
                poiret.setBackgroundResource(R.drawable.button);
                pressstart.setBackgroundResource(R.drawable.button);
                rocksalt.setBackgroundResource(R.drawable.button);
                sacramento.setBackgroundResource(R.drawable.button);
                shadow.setBackgroundResource(R.drawable.button);
                elite.setBackgroundResource(R.drawable.button);
            }
        });
        margarine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                font = 18;
                txt_post.setTypeface(ResourcesCompat.getFont(PostActivity.this, R.font.margarine_regular));
                comfortaa.setBackgroundResource(R.drawable.button);
                amasticsc.setBackgroundResource(R.drawable.button);
                astloch.setBackgroundResource(R.drawable.button);
                baloo.setBackgroundResource(R.drawable.button);
                baloochetan.setBackgroundResource(R.drawable.button);
                bangers.setBackgroundResource(R.drawable.button);
                blackops.setBackgroundResource(R.drawable.button);
                bowl.setBackgroundResource(R.drawable.button);
                facinate.setBackgroundResource(R.drawable.button);
                fredericka.setBackgroundResource(R.drawable.button);
                germania.setBackgroundResource(R.drawable.button);
                gochi.setBackgroundResource(R.drawable.button);
                vibes.setBackgroundResource(R.drawable.button);
                homemade.setBackgroundResource(R.drawable.button);
                flower.setBackgroundResource(R.drawable.button);
                luckyguy.setBackgroundResource(R.drawable.button);
                marck.setBackgroundResource(R.drawable.button);
                margarine.setBackgroundResource(R.drawable.button_selected);
                monoton.setBackgroundResource(R.drawable.button);
                dafoe.setBackgroundResource(R.drawable.button);
                shepphard.setBackgroundResource(R.drawable.button);
                neucha.setBackgroundResource(R.drawable.button);
                pacifico.setBackgroundResource(R.drawable.button);
                patua.setBackgroundResource(R.drawable.button);
                poiret.setBackgroundResource(R.drawable.button);
                pressstart.setBackgroundResource(R.drawable.button);
                rocksalt.setBackgroundResource(R.drawable.button);
                sacramento.setBackgroundResource(R.drawable.button);
                shadow.setBackgroundResource(R.drawable.button);
                elite.setBackgroundResource(R.drawable.button);
            }
        });
        monoton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                font = 19;
                txt_post.setTypeface(ResourcesCompat.getFont(PostActivity.this, R.font.monoton_regular));
                comfortaa.setBackgroundResource(R.drawable.button);
                amasticsc.setBackgroundResource(R.drawable.button);
                astloch.setBackgroundResource(R.drawable.button);
                baloo.setBackgroundResource(R.drawable.button);
                baloochetan.setBackgroundResource(R.drawable.button);
                bangers.setBackgroundResource(R.drawable.button);
                blackops.setBackgroundResource(R.drawable.button);
                bowl.setBackgroundResource(R.drawable.button);
                facinate.setBackgroundResource(R.drawable.button);
                fredericka.setBackgroundResource(R.drawable.button);
                germania.setBackgroundResource(R.drawable.button);
                gochi.setBackgroundResource(R.drawable.button);
                vibes.setBackgroundResource(R.drawable.button);
                homemade.setBackgroundResource(R.drawable.button);
                flower.setBackgroundResource(R.drawable.button);
                luckyguy.setBackgroundResource(R.drawable.button);
                marck.setBackgroundResource(R.drawable.button);
                margarine.setBackgroundResource(R.drawable.button);
                monoton.setBackgroundResource(R.drawable.button_selected);
                dafoe.setBackgroundResource(R.drawable.button);
                shepphard.setBackgroundResource(R.drawable.button);
                neucha.setBackgroundResource(R.drawable.button);
                pacifico.setBackgroundResource(R.drawable.button);
                patua.setBackgroundResource(R.drawable.button);
                poiret.setBackgroundResource(R.drawable.button);
                pressstart.setBackgroundResource(R.drawable.button);
                rocksalt.setBackgroundResource(R.drawable.button);
                sacramento.setBackgroundResource(R.drawable.button);
                shadow.setBackgroundResource(R.drawable.button);
                elite.setBackgroundResource(R.drawable.button);
            }
        });
        dafoe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                font = 20;
                txt_post.setTypeface(ResourcesCompat.getFont(PostActivity.this, R.font.mrdafoe_regular));
                comfortaa.setBackgroundResource(R.drawable.button);
                amasticsc.setBackgroundResource(R.drawable.button);
                astloch.setBackgroundResource(R.drawable.button);
                baloo.setBackgroundResource(R.drawable.button);
                baloochetan.setBackgroundResource(R.drawable.button);
                bangers.setBackgroundResource(R.drawable.button);
                blackops.setBackgroundResource(R.drawable.button);
                bowl.setBackgroundResource(R.drawable.button);
                facinate.setBackgroundResource(R.drawable.button);
                fredericka.setBackgroundResource(R.drawable.button);
                germania.setBackgroundResource(R.drawable.button);
                gochi.setBackgroundResource(R.drawable.button);
                vibes.setBackgroundResource(R.drawable.button);
                homemade.setBackgroundResource(R.drawable.button);
                flower.setBackgroundResource(R.drawable.button);
                luckyguy.setBackgroundResource(R.drawable.button);
                marck.setBackgroundResource(R.drawable.button);
                margarine.setBackgroundResource(R.drawable.button);
                monoton.setBackgroundResource(R.drawable.button);
                dafoe.setBackgroundResource(R.drawable.button_selected);
                shepphard.setBackgroundResource(R.drawable.button);
                neucha.setBackgroundResource(R.drawable.button);
                pacifico.setBackgroundResource(R.drawable.button);
                patua.setBackgroundResource(R.drawable.button);
                poiret.setBackgroundResource(R.drawable.button);
                pressstart.setBackgroundResource(R.drawable.button);
                rocksalt.setBackgroundResource(R.drawable.button);
                sacramento.setBackgroundResource(R.drawable.button);
                shadow.setBackgroundResource(R.drawable.button);
                elite.setBackgroundResource(R.drawable.button);
            }
        });
        shepphard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                font = 21;
                txt_post.setTypeface(ResourcesCompat.getFont(PostActivity.this, R.font.mrsshephards_regular));
                comfortaa.setBackgroundResource(R.drawable.button);
                amasticsc.setBackgroundResource(R.drawable.button);
                astloch.setBackgroundResource(R.drawable.button);
                baloo.setBackgroundResource(R.drawable.button);
                baloochetan.setBackgroundResource(R.drawable.button);
                bangers.setBackgroundResource(R.drawable.button);
                blackops.setBackgroundResource(R.drawable.button);
                bowl.setBackgroundResource(R.drawable.button);
                facinate.setBackgroundResource(R.drawable.button);
                fredericka.setBackgroundResource(R.drawable.button);
                germania.setBackgroundResource(R.drawable.button);
                gochi.setBackgroundResource(R.drawable.button);
                vibes.setBackgroundResource(R.drawable.button);
                homemade.setBackgroundResource(R.drawable.button);
                flower.setBackgroundResource(R.drawable.button);
                luckyguy.setBackgroundResource(R.drawable.button);
                marck.setBackgroundResource(R.drawable.button);
                margarine.setBackgroundResource(R.drawable.button);
                monoton.setBackgroundResource(R.drawable.button);
                dafoe.setBackgroundResource(R.drawable.button);
                shepphard.setBackgroundResource(R.drawable.button_selected);
                neucha.setBackgroundResource(R.drawable.button);
                pacifico.setBackgroundResource(R.drawable.button);
                patua.setBackgroundResource(R.drawable.button);
                poiret.setBackgroundResource(R.drawable.button);
                pressstart.setBackgroundResource(R.drawable.button);
                rocksalt.setBackgroundResource(R.drawable.button);
                sacramento.setBackgroundResource(R.drawable.button);
                shadow.setBackgroundResource(R.drawable.button);
                elite.setBackgroundResource(R.drawable.button);
            }
        });
        neucha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                font = 22;
                txt_post.setTypeface(ResourcesCompat.getFont(PostActivity.this, R.font.neucha_regular));
                comfortaa.setBackgroundResource(R.drawable.button);
                amasticsc.setBackgroundResource(R.drawable.button);
                astloch.setBackgroundResource(R.drawable.button);
                baloo.setBackgroundResource(R.drawable.button);
                baloochetan.setBackgroundResource(R.drawable.button);
                bangers.setBackgroundResource(R.drawable.button);
                blackops.setBackgroundResource(R.drawable.button);
                bowl.setBackgroundResource(R.drawable.button);
                facinate.setBackgroundResource(R.drawable.button);
                fredericka.setBackgroundResource(R.drawable.button);
                germania.setBackgroundResource(R.drawable.button);
                gochi.setBackgroundResource(R.drawable.button);
                vibes.setBackgroundResource(R.drawable.button);
                homemade.setBackgroundResource(R.drawable.button);
                flower.setBackgroundResource(R.drawable.button);
                luckyguy.setBackgroundResource(R.drawable.button);
                marck.setBackgroundResource(R.drawable.button);
                margarine.setBackgroundResource(R.drawable.button);
                monoton.setBackgroundResource(R.drawable.button);
                dafoe.setBackgroundResource(R.drawable.button);
                shepphard.setBackgroundResource(R.drawable.button);
                neucha.setBackgroundResource(R.drawable.button_selected);
                pacifico.setBackgroundResource(R.drawable.button);
                patua.setBackgroundResource(R.drawable.button);
                poiret.setBackgroundResource(R.drawable.button);
                pressstart.setBackgroundResource(R.drawable.button);
                rocksalt.setBackgroundResource(R.drawable.button);
                sacramento.setBackgroundResource(R.drawable.button);
                shadow.setBackgroundResource(R.drawable.button);
                elite.setBackgroundResource(R.drawable.button);
            }
        });
        pacifico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                font = 23;
                txt_post.setTypeface(ResourcesCompat.getFont(PostActivity.this, R.font.pacifico_regular));
                comfortaa.setBackgroundResource(R.drawable.button);
                amasticsc.setBackgroundResource(R.drawable.button);
                astloch.setBackgroundResource(R.drawable.button);
                baloo.setBackgroundResource(R.drawable.button);
                baloochetan.setBackgroundResource(R.drawable.button);
                bangers.setBackgroundResource(R.drawable.button);
                blackops.setBackgroundResource(R.drawable.button);
                bowl.setBackgroundResource(R.drawable.button);
                facinate.setBackgroundResource(R.drawable.button);
                fredericka.setBackgroundResource(R.drawable.button);
                germania.setBackgroundResource(R.drawable.button);
                gochi.setBackgroundResource(R.drawable.button);
                vibes.setBackgroundResource(R.drawable.button);
                homemade.setBackgroundResource(R.drawable.button);
                flower.setBackgroundResource(R.drawable.button);
                luckyguy.setBackgroundResource(R.drawable.button);
                marck.setBackgroundResource(R.drawable.button);
                margarine.setBackgroundResource(R.drawable.button);
                monoton.setBackgroundResource(R.drawable.button);
                dafoe.setBackgroundResource(R.drawable.button);
                shepphard.setBackgroundResource(R.drawable.button);
                neucha.setBackgroundResource(R.drawable.button);
                pacifico.setBackgroundResource(R.drawable.button_selected);
                patua.setBackgroundResource(R.drawable.button);
                poiret.setBackgroundResource(R.drawable.button);
                pressstart.setBackgroundResource(R.drawable.button);
                rocksalt.setBackgroundResource(R.drawable.button);
                sacramento.setBackgroundResource(R.drawable.button);
                shadow.setBackgroundResource(R.drawable.button);
                elite.setBackgroundResource(R.drawable.button);
            }
        });
        patua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                font = 24;
                txt_post.setTypeface(ResourcesCompat.getFont(PostActivity.this, R.font.patuaone_regular));
                comfortaa.setBackgroundResource(R.drawable.button);
                amasticsc.setBackgroundResource(R.drawable.button);
                astloch.setBackgroundResource(R.drawable.button);
                baloo.setBackgroundResource(R.drawable.button);
                baloochetan.setBackgroundResource(R.drawable.button);
                bangers.setBackgroundResource(R.drawable.button);
                blackops.setBackgroundResource(R.drawable.button);
                bowl.setBackgroundResource(R.drawable.button);
                facinate.setBackgroundResource(R.drawable.button);
                fredericka.setBackgroundResource(R.drawable.button);
                germania.setBackgroundResource(R.drawable.button);
                gochi.setBackgroundResource(R.drawable.button);
                vibes.setBackgroundResource(R.drawable.button);
                homemade.setBackgroundResource(R.drawable.button);
                flower.setBackgroundResource(R.drawable.button);
                luckyguy.setBackgroundResource(R.drawable.button);
                marck.setBackgroundResource(R.drawable.button);
                margarine.setBackgroundResource(R.drawable.button);
                monoton.setBackgroundResource(R.drawable.button);
                dafoe.setBackgroundResource(R.drawable.button);
                shepphard.setBackgroundResource(R.drawable.button);
                neucha.setBackgroundResource(R.drawable.button);
                pacifico.setBackgroundResource(R.drawable.button);
                patua.setBackgroundResource(R.drawable.button_selected);
                poiret.setBackgroundResource(R.drawable.button);
                pressstart.setBackgroundResource(R.drawable.button);
                rocksalt.setBackgroundResource(R.drawable.button);
                sacramento.setBackgroundResource(R.drawable.button);
                shadow.setBackgroundResource(R.drawable.button);
                elite.setBackgroundResource(R.drawable.button);
            }
        });
        poiret.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                font = 25;
                txt_post.setTypeface(ResourcesCompat.getFont(PostActivity.this, R.font.poiretone_regular));
                comfortaa.setBackgroundResource(R.drawable.button);
                amasticsc.setBackgroundResource(R.drawable.button);
                astloch.setBackgroundResource(R.drawable.button);
                baloo.setBackgroundResource(R.drawable.button);
                baloochetan.setBackgroundResource(R.drawable.button);
                bangers.setBackgroundResource(R.drawable.button);
                blackops.setBackgroundResource(R.drawable.button);
                bowl.setBackgroundResource(R.drawable.button);
                facinate.setBackgroundResource(R.drawable.button);
                fredericka.setBackgroundResource(R.drawable.button);
                germania.setBackgroundResource(R.drawable.button);
                gochi.setBackgroundResource(R.drawable.button);
                vibes.setBackgroundResource(R.drawable.button);
                homemade.setBackgroundResource(R.drawable.button);
                flower.setBackgroundResource(R.drawable.button);
                luckyguy.setBackgroundResource(R.drawable.button);
                marck.setBackgroundResource(R.drawable.button);
                margarine.setBackgroundResource(R.drawable.button);
                monoton.setBackgroundResource(R.drawable.button);
                dafoe.setBackgroundResource(R.drawable.button);
                shepphard.setBackgroundResource(R.drawable.button);
                neucha.setBackgroundResource(R.drawable.button);
                pacifico.setBackgroundResource(R.drawable.button);
                patua.setBackgroundResource(R.drawable.button);
                poiret.setBackgroundResource(R.drawable.button_selected);
                pressstart.setBackgroundResource(R.drawable.button);
                rocksalt.setBackgroundResource(R.drawable.button);
                sacramento.setBackgroundResource(R.drawable.button);
                shadow.setBackgroundResource(R.drawable.button);
                elite.setBackgroundResource(R.drawable.button);
            }
        });
        pressstart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                font = 26;
                txt_post.setTypeface(ResourcesCompat.getFont(PostActivity.this, R.font.pressstarrt2p_regular));
                comfortaa.setBackgroundResource(R.drawable.button);
                amasticsc.setBackgroundResource(R.drawable.button);
                astloch.setBackgroundResource(R.drawable.button);
                baloo.setBackgroundResource(R.drawable.button);
                baloochetan.setBackgroundResource(R.drawable.button);
                bangers.setBackgroundResource(R.drawable.button);
                blackops.setBackgroundResource(R.drawable.button);
                bowl.setBackgroundResource(R.drawable.button);
                facinate.setBackgroundResource(R.drawable.button);
                fredericka.setBackgroundResource(R.drawable.button);
                germania.setBackgroundResource(R.drawable.button);
                gochi.setBackgroundResource(R.drawable.button);
                vibes.setBackgroundResource(R.drawable.button);
                homemade.setBackgroundResource(R.drawable.button);
                flower.setBackgroundResource(R.drawable.button);
                luckyguy.setBackgroundResource(R.drawable.button);
                marck.setBackgroundResource(R.drawable.button);
                margarine.setBackgroundResource(R.drawable.button);
                monoton.setBackgroundResource(R.drawable.button);
                dafoe.setBackgroundResource(R.drawable.button);
                shepphard.setBackgroundResource(R.drawable.button);
                neucha.setBackgroundResource(R.drawable.button);
                pacifico.setBackgroundResource(R.drawable.button);
                patua.setBackgroundResource(R.drawable.button);
                poiret.setBackgroundResource(R.drawable.button);
                pressstart.setBackgroundResource(R.drawable.button_selected);
                rocksalt.setBackgroundResource(R.drawable.button);
                sacramento.setBackgroundResource(R.drawable.button);
                shadow.setBackgroundResource(R.drawable.button);
                elite.setBackgroundResource(R.drawable.button);
            }
        });
        rocksalt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                font = 27;
                txt_post.setTypeface(ResourcesCompat.getFont(PostActivity.this, R.font.rocksalt_regular));
                comfortaa.setBackgroundResource(R.drawable.button);
                amasticsc.setBackgroundResource(R.drawable.button);
                astloch.setBackgroundResource(R.drawable.button);
                baloo.setBackgroundResource(R.drawable.button);
                baloochetan.setBackgroundResource(R.drawable.button);
                bangers.setBackgroundResource(R.drawable.button);
                blackops.setBackgroundResource(R.drawable.button);
                bowl.setBackgroundResource(R.drawable.button);
                facinate.setBackgroundResource(R.drawable.button);
                fredericka.setBackgroundResource(R.drawable.button);
                germania.setBackgroundResource(R.drawable.button);
                gochi.setBackgroundResource(R.drawable.button);
                vibes.setBackgroundResource(R.drawable.button);
                homemade.setBackgroundResource(R.drawable.button);
                flower.setBackgroundResource(R.drawable.button);
                luckyguy.setBackgroundResource(R.drawable.button);
                marck.setBackgroundResource(R.drawable.button);
                margarine.setBackgroundResource(R.drawable.button);
                monoton.setBackgroundResource(R.drawable.button);
                dafoe.setBackgroundResource(R.drawable.button);
                shepphard.setBackgroundResource(R.drawable.button);
                neucha.setBackgroundResource(R.drawable.button);
                pacifico.setBackgroundResource(R.drawable.button);
                patua.setBackgroundResource(R.drawable.button);
                poiret.setBackgroundResource(R.drawable.button);
                pressstart.setBackgroundResource(R.drawable.button);
                rocksalt.setBackgroundResource(R.drawable.button_selected);
                sacramento.setBackgroundResource(R.drawable.button);
                shadow.setBackgroundResource(R.drawable.button);
                elite.setBackgroundResource(R.drawable.button);
            }
        });
        sacramento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                font  = 28;
                txt_post.setTypeface(ResourcesCompat.getFont(PostActivity.this, R.font.sacramento_regular));
                comfortaa.setBackgroundResource(R.drawable.button);
                amasticsc.setBackgroundResource(R.drawable.button);
                astloch.setBackgroundResource(R.drawable.button);
                baloo.setBackgroundResource(R.drawable.button);
                baloochetan.setBackgroundResource(R.drawable.button);
                bangers.setBackgroundResource(R.drawable.button);
                blackops.setBackgroundResource(R.drawable.button);
                bowl.setBackgroundResource(R.drawable.button);
                facinate.setBackgroundResource(R.drawable.button);
                fredericka.setBackgroundResource(R.drawable.button);
                germania.setBackgroundResource(R.drawable.button);
                gochi.setBackgroundResource(R.drawable.button);
                vibes.setBackgroundResource(R.drawable.button);
                homemade.setBackgroundResource(R.drawable.button);
                flower.setBackgroundResource(R.drawable.button);
                luckyguy.setBackgroundResource(R.drawable.button);
                marck.setBackgroundResource(R.drawable.button);
                margarine.setBackgroundResource(R.drawable.button);
                monoton.setBackgroundResource(R.drawable.button);
                dafoe.setBackgroundResource(R.drawable.button);
                shepphard.setBackgroundResource(R.drawable.button);
                neucha.setBackgroundResource(R.drawable.button);
                pacifico.setBackgroundResource(R.drawable.button);
                patua.setBackgroundResource(R.drawable.button);
                poiret.setBackgroundResource(R.drawable.button);
                pressstart.setBackgroundResource(R.drawable.button);
                rocksalt.setBackgroundResource(R.drawable.button);
                sacramento.setBackgroundResource(R.drawable.button_selected);
                shadow.setBackgroundResource(R.drawable.button);
                elite.setBackgroundResource(R.drawable.button);
            }
        });
        shadow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                font = 29;
                txt_post.setTypeface(ResourcesCompat.getFont(PostActivity.this, R.font.shadowintolight_regular));
                comfortaa.setBackgroundResource(R.drawable.button);
                amasticsc.setBackgroundResource(R.drawable.button);
                astloch.setBackgroundResource(R.drawable.button);
                baloo.setBackgroundResource(R.drawable.button);
                baloochetan.setBackgroundResource(R.drawable.button);
                bangers.setBackgroundResource(R.drawable.button);
                blackops.setBackgroundResource(R.drawable.button);
                bowl.setBackgroundResource(R.drawable.button);
                facinate.setBackgroundResource(R.drawable.button);
                fredericka.setBackgroundResource(R.drawable.button);
                germania.setBackgroundResource(R.drawable.button);
                gochi.setBackgroundResource(R.drawable.button);
                vibes.setBackgroundResource(R.drawable.button);
                homemade.setBackgroundResource(R.drawable.button);
                flower.setBackgroundResource(R.drawable.button);
                luckyguy.setBackgroundResource(R.drawable.button);
                marck.setBackgroundResource(R.drawable.button);
                margarine.setBackgroundResource(R.drawable.button);
                monoton.setBackgroundResource(R.drawable.button);
                dafoe.setBackgroundResource(R.drawable.button);
                shepphard.setBackgroundResource(R.drawable.button);
                neucha.setBackgroundResource(R.drawable.button);
                pacifico.setBackgroundResource(R.drawable.button);
                patua.setBackgroundResource(R.drawable.button);
                poiret.setBackgroundResource(R.drawable.button);
                pressstart.setBackgroundResource(R.drawable.button);
                rocksalt.setBackgroundResource(R.drawable.button);
                sacramento.setBackgroundResource(R.drawable.button);
                shadow.setBackgroundResource(R.drawable.button_selected);
                elite.setBackgroundResource(R.drawable.button);
            }
        });
        elite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                font = 30;
                txt_post.setTypeface(ResourcesCompat.getFont(PostActivity.this, R.font.specialelite_regular));
                comfortaa.setBackgroundResource(R.drawable.button);
                amasticsc.setBackgroundResource(R.drawable.button);
                astloch.setBackgroundResource(R.drawable.button);
                baloo.setBackgroundResource(R.drawable.button);
                baloochetan.setBackgroundResource(R.drawable.button);
                bangers.setBackgroundResource(R.drawable.button);
                blackops.setBackgroundResource(R.drawable.button);
                bowl.setBackgroundResource(R.drawable.button);
                facinate.setBackgroundResource(R.drawable.button);
                fredericka.setBackgroundResource(R.drawable.button);
                germania.setBackgroundResource(R.drawable.button);
                gochi.setBackgroundResource(R.drawable.button);
                vibes.setBackgroundResource(R.drawable.button);
                homemade.setBackgroundResource(R.drawable.button);
                flower.setBackgroundResource(R.drawable.button);
                luckyguy.setBackgroundResource(R.drawable.button);
                marck.setBackgroundResource(R.drawable.button);
                margarine.setBackgroundResource(R.drawable.button);
                monoton.setBackgroundResource(R.drawable.button);
                dafoe.setBackgroundResource(R.drawable.button);
                shepphard.setBackgroundResource(R.drawable.button);
                neucha.setBackgroundResource(R.drawable.button);
                pacifico.setBackgroundResource(R.drawable.button);
                patua.setBackgroundResource(R.drawable.button);
                poiret.setBackgroundResource(R.drawable.button);
                pressstart.setBackgroundResource(R.drawable.button);
                rocksalt.setBackgroundResource(R.drawable.button);
                sacramento.setBackgroundResource(R.drawable.button);
                shadow.setBackgroundResource(R.drawable.button);
                elite.setBackgroundResource(R.drawable.button_selected);
            }
        });


        // Set an On Checked Change Listener to the Toggle Switch
        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // If the toggle is ON
                if(switch1.isChecked()){
                    username.setText("anonymous");      // set username to anonymous
                    Glide.with(getApplicationContext()).load("https://firebasestorage.googleapis.com/v0/b/freemind-dc8cd.appspot.com/o/anonymous-512.png?alt=media&token=29de2c3f-ed72-4da3-ab6c-6ae72f6d8b66").into(image_profile);
                    anonym = true;
                } else
                {
                    authorInfo(image_profile,username, FirebaseAuth.getInstance().getCurrentUser().getUid());   // get details of the user signed IN
                    anonym = false;
                }
            }
        });

        // get details of the user signed IN
        authorInfo(image_profile,username, FirebaseAuth.getInstance().getCurrentUser().getUid());

        // On Click on the close button
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(PostActivity.this);
                alertDialog.setTitle("Discard Post?");

                alertDialog.setPositiveButton("Discard",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                PostActivity.super.onBackPressed();
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

        // post On Click method
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // if the ads are loaded successfully
                if (mInterstitialAd.isLoaded()) {
                    // show ads
                    mInterstitialAd.show();
                    // set an ad listener
                    mInterstitialAd.setAdListener(new AdListener() {
                        @Override
                        public void onAdLoaded() {
                            // Code to be executed when an ad finishes loading.
                        }

                        @Override
                        public void onAdFailedToLoad(int errorCode) {
                            // Code to be executed when an ad request fails.
                            if(TextUtils.isEmpty(txt_post.getText())){
                                Toast.makeText(PostActivity.this, "You have to write something to post.", Toast.LENGTH_SHORT).show();
                            } else{

                                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Posts");

                                String postid = reference.push().getKey();

                                HashMap<String, Object> hashMap = new HashMap<>();
                                hashMap.put("postid", postid);
                                hashMap.put("text_post", txt_post.getText().toString().trim());
                                hashMap.put("author", getU(username.getText().toString()));
                                hashMap.put("date", getDateTime());
                                hashMap.put("color", getC());
                                hashMap.put("category", getCat());
                                hashMap.put("font", font);
                                hashMap.put("anonymous", anonym);
                                hashMap.put("size", 0);

                                reference.child(postid).setValue(hashMap)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()){
                                            post.setEnabled(false);
                                            finish();
                                        }
                                    }
                                });




                            }
                        }

                        @Override
                        public void onAdOpened() {
                            // Code to be executed when the ad is displayed.
                            // load the ad again in background
                            mInterstitialAd.loadAd(new AdRequest.Builder().build());
                        }

                        @Override
                        public void onAdLeftApplication() {
                            // Code to be executed when the user has left the app.
                            // load the ad again in background
                            mInterstitialAd.loadAd(new AdRequest.Builder().build());
                        }

                        @Override
                        public void onAdClosed() {
                            // Code to be executed when the interstitial ad is closed.
                            mInterstitialAd.loadAd(new AdRequest.Builder().build());
                            if(TextUtils.isEmpty(txt_post.getText())){
                                Toast.makeText(PostActivity.this, "You have to write something to post.", Toast.LENGTH_SHORT).show();
                            } else{

                                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Posts");

                                String postid = reference.push().getKey();

                                HashMap<String, Object> hashMap = new HashMap<>();
                                hashMap.put("postid", postid);
                                hashMap.put("text_post", txt_post.getText().toString().trim());
                                hashMap.put("author", getU(username.getText().toString()));
                                hashMap.put("date", getDateTime());
                                hashMap.put("color", getC());
                                hashMap.put("category", getCat());
                                hashMap.put("font", font);
                                hashMap.put("anonymous", anonym);
                                hashMap.put("size", 0);

                                reference.child(postid).setValue(hashMap)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()){
                                            post.setEnabled(false);
                                            finish();
                                        }
                                    }
                                });



                            }
                        }
                    });
                } else {
                    Log.d("TAG", "The interstitial wasn't loaded yet.");
                    if(TextUtils.isEmpty(txt_post.getText())){
                        Toast.makeText(PostActivity.this, "You have to write something to post.", Toast.LENGTH_SHORT).show();
                    } else{

                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Posts");

                        String postid = reference.push().getKey();

                        HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put("postid", postid);
                        hashMap.put("text_post", txt_post.getText().toString().trim());
                        hashMap.put("author", getU(username.getText().toString()));
                        hashMap.put("date", getDateTime());
                        hashMap.put("color", getC());
                        hashMap.put("category", getCat());
                        hashMap.put("font", font);
                        hashMap.put("anonymous", anonym);
                        hashMap.put("size", 0);

                        reference.child(postid).setValue(hashMap)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    post.setEnabled(false);
                                    finish();
                                }
                            }
                        });



                    }
                }
            }
        });

    }

    // method to get the date
    private String getDateTime() {
        // set date format
        DateFormat dateFormat = new SimpleDateFormat("E, dd MMM yyyy HH:mm");
        Date date = new Date();
        return dateFormat.format(date);
    }

    // get the author info
    private void authorInfo(final ImageView image_profile, final TextView username, String userid){
        // get the database reference
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(userid);

        // get a value event listener
        // when we get some value
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // get the user data from database to User class
                User user = dataSnapshot.getValue(User.class);
                // get the profile image with glide(library)
                Glide.with(getApplicationContext()).load(user.getImageurl()).into(image_profile);
                username.setText(user.getUsername());       // get the username

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    // get the username
    private String getU(String user1)
    {
        String result = "";
        result = FirebaseAuth.getInstance().getCurrentUser().getUid();
        return result;
    }

    // get the color according to the category
    private int getC(){
        int result = 0;

        if(relationships.isChecked()){
            result = Color.rgb(188, 0, 80);
        }
        if(work.isChecked()){
            result = Color.rgb(238, 46, 79);
        }
        if(friends.isChecked()){
            result = Color.rgb(251, 97, 7);
        }
        if(education.isChecked()){
            result = Color.rgb(243,222,44);
        }
        if(family.isChecked()){
            result = Color.rgb(124,181,24);
        }
        if(hopes.isChecked()){
            result = Color.rgb(68,229,231);
        }
        if(mystory.isChecked()){
            result = Color.rgb(92,122,255);
        }
        if(mentalhealth.isChecked()){
            result = Color.rgb(159,126,105);
        }

        return result;
    }

    // get the category
    private String getCat(){
        String result = "";
        if(relationships.isChecked()){
            result = "Relationships";
        }
        if(work.isChecked()){
            result = "Work";
        }
        if(friends.isChecked()){
            result = "Friends";
        }
        if(education.isChecked()){
            result = "Education";
        }
        if(family.isChecked()){
            result = "Family";
        }
        if(hopes.isChecked()){
            result = "Hopes";
        }
        if(mystory.isChecked()){
            result = "My Story";
        }
        if(mentalhealth.isChecked()){
            result = "Mental Health";
        }
        return result;
    }

    @Override
    public void onBackPressed() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Discard Post?");
        builder.setPositiveButton("Discard", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                PostActivity.super.onBackPressed();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

}
