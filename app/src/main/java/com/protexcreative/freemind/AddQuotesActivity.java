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
import android.text.Layout;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.protexcreative.freemind.Model.User;

import java.util.HashMap;

public class AddQuotesActivity extends AppCompatActivity {
    int font = 1, color = 1;

    View back;
    private InterstitialAd mInterstitialAd;
    ImageView close, image_profile;
    TextView username, post;
    TextView relationships, work, friends, education, family, hopes, mystory, mentalhealth;
    EditText text_quote;
    Button comfortaa, amasticsc, astloch, baloo, baloochetan, bangers, blackops, bowl, facinate, fredericka, germania, gochi, vibes, homemade, flower, luckyguy, marck, margarine, monoton, dafoe, shepphard, neucha, pacifico, patua, poiret, pressstart, rocksalt, sacramento, shadow, elite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_quotes);

        close = findViewById(R.id.close);
        username = findViewById(R.id.username);
        image_profile = findViewById(R.id.image_profile);
        text_quote = findViewById(R.id.text_quote);
        post = findViewById(R.id.post);

        post.setEnabled(true);

        MobileAds.initialize(this,
                "ca-app-pub-9930376729131413~7469397260");
        // Create an Object
        mInterstitialAd = new InterstitialAd(this);
        // Set Ad Unit ID
        mInterstitialAd.setAdUnitId("ca-app-pub-9930376729131413/8195414287");
        // Load Ad in the Background
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                            addQuote(text_quote, post);
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
                            addQuote(text_quote, post);
                        }
                    });
                } else {
                    Log.d("Interstitial not loaded", "none");
                    addQuote(text_quote, post);
                }
            }
        });





                userinfo(image_profile, username);

                back = findViewById(R.id.back);

                // color
                relationships = findViewById(R.id.relationships);
                work = findViewById(R.id.work);
                friends = findViewById(R.id.friends);
                education = findViewById(R.id.education);
                family = findViewById(R.id.family);
                hopes = findViewById(R.id.hopes);
                mystory = findViewById(R.id.mystory);
                mentalhealth = findViewById(R.id.mentalhealth);


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

                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(AddQuotesActivity.this);
                        alertDialog.setTitle("Discard Quote?");

                        alertDialog.setPositiveButton("Discard",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        AddQuotesActivity.super.onBackPressed();
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

                //color
                relationships.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        back.setBackgroundColor(Color.rgb(188, 0, 80));
                        color = 1;
                        getSelected(color);
                    }
                });

                work.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        back.setBackgroundColor(Color.rgb(238, 46, 79));
                        color = 2;
                        getSelected(color);
                    }
                });

                friends.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        back.setBackgroundColor(Color.rgb(251, 97, 7));
                        color = 3;
                        getSelected(color);
                    }
                });

                education.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        back.setBackgroundColor(Color.rgb(243, 222, 44));
                        color = 4;
                        getSelected(color);
                    }
                });

                family.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        back.setBackgroundColor(Color.rgb(124, 181, 24));
                        color = 5;
                        getSelected(color);
                    }
                });

                hopes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        back.setBackgroundColor(Color.rgb(68, 229, 231));
                        color = 6;
                        getSelected(color);
                    }
                });

                mystory.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        back.setBackgroundColor(Color.rgb(92, 122, 255));
                        color = 7;
                        getSelected(color);
                    }
                });

                mentalhealth.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        back.setBackgroundColor(Color.rgb(159, 126, 105));
                        color = 8;
                        getSelected(color);
                    }
                });


                // font
                comfortaa.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        font = 1;
                        text_quote.setTypeface(ResourcesCompat.getFont(AddQuotesActivity.this, R.font.comfortaa_regular));
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
                        text_quote.setTypeface(ResourcesCompat.getFont(AddQuotesActivity.this, R.font.amaticsc_regular));
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
                        text_quote.setTypeface(ResourcesCompat.getFont(AddQuotesActivity.this, R.font.astlock_regular));
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
                        text_quote.setTypeface(ResourcesCompat.getFont(AddQuotesActivity.this, R.font.baloo_regular));
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
                        text_quote.setTypeface(ResourcesCompat.getFont(AddQuotesActivity.this, R.font.baloochetan_regular));
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
                        text_quote.setTypeface(ResourcesCompat.getFont(AddQuotesActivity.this, R.font.bangers_regular));
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
                        text_quote.setTypeface(ResourcesCompat.getFont(AddQuotesActivity.this, R.font.blackopsone_regular));
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
                        text_quote.setTypeface(ResourcesCompat.getFont(AddQuotesActivity.this, R.font.bowlbyone_regular));
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
                        text_quote.setTypeface(ResourcesCompat.getFont(AddQuotesActivity.this, R.font.facinateinline_regular));
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
                        text_quote.setTypeface(ResourcesCompat.getFont(AddQuotesActivity.this, R.font.frederickathegreat_regular));
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
                        text_quote.setTypeface(ResourcesCompat.getFont(AddQuotesActivity.this, R.font.germaniaone_regular));
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
                        text_quote.setTypeface(ResourcesCompat.getFont(AddQuotesActivity.this, R.font.gochihand_regular));
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
                        font = 13;
                        text_quote.setTypeface(ResourcesCompat.getFont(AddQuotesActivity.this, R.font.greatvibes_regular));
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
                        text_quote.setTypeface(ResourcesCompat.getFont(AddQuotesActivity.this, R.font.homemadeapples_regular));
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
                        text_quote.setTypeface(ResourcesCompat.getFont(AddQuotesActivity.this, R.font.indieflower_regular));
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
                        text_quote.setTypeface(ResourcesCompat.getFont(AddQuotesActivity.this, R.font.luckiestguy_regular));
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
                        text_quote.setTypeface(ResourcesCompat.getFont(AddQuotesActivity.this, R.font.marckscript_regular));
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
                        text_quote.setTypeface(ResourcesCompat.getFont(AddQuotesActivity.this, R.font.margarine_regular));
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
                        text_quote.setTypeface(ResourcesCompat.getFont(AddQuotesActivity.this, R.font.monoton_regular));
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
                        text_quote.setTypeface(ResourcesCompat.getFont(AddQuotesActivity.this, R.font.mrdafoe_regular));
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
                        text_quote.setTypeface(ResourcesCompat.getFont(AddQuotesActivity.this, R.font.mrsshephards_regular));
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
                        text_quote.setTypeface(ResourcesCompat.getFont(AddQuotesActivity.this, R.font.neucha_regular));
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
                        text_quote.setTypeface(ResourcesCompat.getFont(AddQuotesActivity.this, R.font.pacifico_regular));
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
                        text_quote.setTypeface(ResourcesCompat.getFont(AddQuotesActivity.this, R.font.patuaone_regular));
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
                        text_quote.setTypeface(ResourcesCompat.getFont(AddQuotesActivity.this, R.font.poiretone_regular));
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
                        text_quote.setTypeface(ResourcesCompat.getFont(AddQuotesActivity.this, R.font.pressstarrt2p_regular));
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
                        text_quote.setTypeface(ResourcesCompat.getFont(AddQuotesActivity.this, R.font.rocksalt_regular));
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
                        font = 28;
                        text_quote.setTypeface(ResourcesCompat.getFont(AddQuotesActivity.this, R.font.sacramento_regular));
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
                        text_quote.setTypeface(ResourcesCompat.getFont(AddQuotesActivity.this, R.font.shadowintolight_regular));
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
                        text_quote.setTypeface(ResourcesCompat.getFont(AddQuotesActivity.this, R.font.specialelite_regular));
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
            }

            private void userinfo(final ImageView imageView, final TextView textView) {
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        User user = dataSnapshot.getValue(User.class);
                        Glide.with(getApplicationContext()).load(user.getImageurl()).into(imageView);
                        textView.setText(user.getUsername());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            private void getSelected(int i) {
                switch (i) {
                    case 1:
                        relationships.setText("*");
                        work.setText("");
                        friends.setText("");
                        education.setText("");
                        family.setText("");
                        hopes.setText("");
                        mystory.setText("");
                        mentalhealth.setText("");
                        break;
                    case 2:
                        relationships.setText("");
                        work.setText("*");
                        friends.setText("");
                        education.setText("");
                        family.setText("");
                        hopes.setText("");
                        mystory.setText("");
                        mentalhealth.setText("");
                        break;
                    case 3:
                        relationships.setText("");
                        work.setText("");
                        friends.setText("*");
                        education.setText("");
                        family.setText("");
                        hopes.setText("");
                        mystory.setText("");
                        mentalhealth.setText("");
                        break;
                    case 4:
                        relationships.setText("");
                        work.setText("");
                        friends.setText("");
                        education.setText("*");
                        family.setText("");
                        hopes.setText("");
                        mystory.setText("");
                        mentalhealth.setText("");
                        break;
                    case 5:
                        relationships.setText("");
                        work.setText("");
                        friends.setText("");
                        education.setText("");
                        family.setText("*");
                        hopes.setText("");
                        mystory.setText("");
                        mentalhealth.setText("");
                        break;
                    case 6:
                        relationships.setText("");
                        work.setText("");
                        friends.setText("");
                        education.setText("");
                        family.setText("");
                        hopes.setText("*");
                        mystory.setText("");
                        mentalhealth.setText("");
                        break;
                    case 7:
                        relationships.setText("");
                        work.setText("");
                        friends.setText("");
                        education.setText("");
                        family.setText("");
                        hopes.setText("");
                        mystory.setText("*");
                        mentalhealth.setText("");
                        break;
                    case 8:
                        relationships.setText("");
                        work.setText("");
                        friends.setText("");
                        education.setText("");
                        family.setText("");
                        hopes.setText("");
                        mystory.setText("");
                        mentalhealth.setText("*");
                        break;
                }
            }

            private void addQuote(EditText editText, final TextView button) {
        if (editText.getText().toString().equals("")){
            Toast.makeText(this, "You cannot post empty Quote.", Toast.LENGTH_SHORT).show();
        } else{
            String userid = FirebaseAuth.getInstance().getCurrentUser().getUid();

            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Quotes").child(userid);

            String quotesid = reference.push().getKey();
            long timeend = System.currentTimeMillis() + 86400000;


            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("userid", userid);
            hashMap.put("timestart", ServerValue.TIMESTAMP);
            hashMap.put("timeend", timeend);
            hashMap.put("quotesid", quotesid);
            hashMap.put("quotes_text", editText.getText().toString());
            hashMap.put("color", color);
            hashMap.put("font", font);

            reference.child(quotesid).setValue(hashMap)
            .addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        button.setEnabled(false);
                        finish();
                    }
                }
            });



        }



    }

    @Override
    public void onBackPressed(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Discard Quote?");
        builder.setPositiveButton("Discard", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                AddQuotesActivity.super.onBackPressed();
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

