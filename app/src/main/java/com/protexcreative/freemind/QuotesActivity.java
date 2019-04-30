package com.protexcreative.freemind;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.protexcreative.freemind.Model.Quotes;
import com.protexcreative.freemind.Model.User;

import java.util.ArrayList;
import java.util.List;

import jp.shts.android.storiesprogressview.StoriesProgressView;

public class QuotesActivity extends AppCompatActivity implements StoriesProgressView.StoriesListener {

    int counter = 0;
    int x = 0;
    long pressTime = 0L;
    long limit = 500L;

    LinearLayout r_seen;
    TextView seen_number, timeleft;

    StoriesProgressView storiesProgressView;
    ImageView quotes_photo;
    TextView quotes_username, text_quote;
    View back;

    List<Integer> txtfont;
    List<Integer> txtcolor;
    List<String> txtquotes;
    List<String> quotesid;
    List<Long> txtleft;
    String userid;

    private View.OnTouchListener onTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    pressTime = System.currentTimeMillis();
                    storiesProgressView.pause();
                    return false;
                case MotionEvent.ACTION_UP:
                    long now = System.currentTimeMillis();
                    storiesProgressView.resume();
                    return limit < now - pressTime;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quotes);

        r_seen = findViewById(R.id.r_seen);
        seen_number = findViewById(R.id.seen_number);

        storiesProgressView = findViewById(R.id.quotes);
        text_quote = findViewById(R.id.text_quote);
        quotes_photo = findViewById(R.id.quotes_photo);
        quotes_username = findViewById(R.id.quotes_username);
        back = findViewById(R.id.back);
        timeleft = findViewById(R.id.timeleft);

        r_seen.setVisibility(View.GONE);


        userid = getIntent().getStringExtra("userid");

        if(userid.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){
            r_seen.setVisibility(View.VISIBLE);
        }

        getQuotes(userid);
        userinfo(userid);

        View reverse = findViewById(R.id.reverse);
        reverse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storiesProgressView.reverse();
            }
        });
        reverse.setOnTouchListener(onTouchListener);

        View skip = findViewById(R.id.skip);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storiesProgressView.skip();
            }
        });
        skip.setOnTouchListener(onTouchListener);

        r_seen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuotesActivity.this, FollowersActivity.class);
                intent.putExtra("id", userid);
                intent.putExtra("quotesid", quotesid.get(counter));
                intent.putExtra("title", "Views");
                startActivity(intent);
            }
        });

        quotes_username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuotesActivity.this, HomeActivity.class);
                intent.putExtra("authorid", userid);
                startActivity(intent);
            }
        });

        quotes_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuotesActivity.this, HomeActivity.class);
                intent.putExtra("authorid", userid);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onNext() {
        x = ++counter;
        text_quote.setText(txtquotes.get(x));
        text_quote.setTypeface(ResourcesCompat.getFont(getApplicationContext(), getFont(txtfont.get(x))));
        back.setBackgroundColor(getColored(txtcolor.get(x)));
        timeleft.setText(((System.currentTimeMillis() - txtleft.get(x))/3600000) + "h");

        addViews(quotesid, counter);
        seenNumbers(quotesid, counter);
    }

    @Override
    public void onPrev() {
        if((counter - 1) < 0) return;
        x = --counter;
        text_quote.setText(txtquotes.get(x));
        timeleft.setText(((System.currentTimeMillis() - txtleft.get(x))/3600000) + "h");
        text_quote.setTypeface(ResourcesCompat.getFont(getApplicationContext(), getFont(txtfont.get(x))));
        back.setBackgroundColor(getColored(txtcolor.get(x)));
        seenNumbers(quotesid, counter);
    }

    @Override
    public void onComplete() {

        finish();
    }

    @Override
    protected void onDestroy() {
        storiesProgressView.destroy();
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        storiesProgressView.pause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        storiesProgressView.resume();
        super.onResume();
    }

    private void getQuotes(String userid){
        txtfont = new ArrayList<>();
        txtcolor = new ArrayList<>();
        quotesid = new ArrayList<>();
        txtquotes = new ArrayList<>();
        txtleft = new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Quotes").child(userid);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                quotesid.clear();
                txtquotes.clear();
                txtcolor.clear();
                txtfont.clear();
                txtleft.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Quotes quotes = snapshot.getValue(Quotes.class);
                    long timecurrent = System.currentTimeMillis();
                    if (timecurrent > quotes.getTimestart() && timecurrent < quotes.getTimeend()){
                        quotesid.add(quotes.getQuotesid());
                        txtquotes.add(quotes.getQuotes_text());
                        txtfont.add(quotes.getFont());
                        txtcolor.add(quotes.getColor());
                        txtleft.add(quotes.getTimestart());
                    }
                }
                storiesProgressView.setStoriesCount(txtquotes.size());
                storiesProgressView.setStoryDuration(5000L);
                storiesProgressView.setStoriesListener(QuotesActivity.this);
                storiesProgressView.startStories(counter);

                text_quote.setText(txtquotes.get(counter));
                timeleft.setText(((System.currentTimeMillis() - txtleft.get(x))/3600000) + "h");
                back.setBackgroundColor(getColored(txtcolor.get(counter)));
                text_quote.setTypeface(ResourcesCompat.getFont(getApplicationContext(), getFont(txtfont.get(counter))));

                addViews(quotesid, counter);
                seenNumbers(quotesid, counter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void userinfo(String userid){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(userid);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                Glide.with(getApplicationContext()).load(user.getImageurl()).into(quotes_photo);
                quotes_username.setText(user.getUsername());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private int getFont(int f){
        int fontT = 0;
        switch (f){
            case 1:
                fontT = R.font.comfortaa_regular;
                break;
            case 2:
                fontT = R.font.amaticsc_regular;
                break;
            case 3:
                fontT = R.font.astlock_regular;
                break;
            case 4:
                fontT = R.font.baloo_regular;
                break;
            case 5:
                fontT = R.font.baloochetan_regular;
                break;
            case 6:
                fontT = R.font.bangers_regular;
                break;
            case 7:
                fontT = R.font.blackopsone_regular;
                break;
            case 8:
                fontT = R.font.bowlbyone_regular;
                break;
            case 9:
                fontT = R.font.facinateinline_regular;
                break;
            case 10:
                fontT = R.font.frederickathegreat_regular;
                break;
            case 11:
                fontT = R.font.germaniaone_regular;
                break;
            case 12:
                fontT = R.font.gochihand_regular;
                break;
            case 13:
                fontT = R.font.greatvibes_regular;
                break;
            case 14:
                fontT = R.font.homemadeapples_regular;
                break;
            case 15:
                fontT = R.font.indieflower_regular;
                break;
            case 16:
                fontT = R.font.luckiestguy_regular;
                break;
            case 17:
                fontT = R.font.marckscript_regular;
                break;
            case 18:
                fontT = R.font.margarine_regular;
                break;
            case 19:
                fontT = R.font.monoton_regular;
                break;
            case 20:
                fontT = R.font.mrdafoe_regular;
                break;
            case 21:
                fontT = R.font.mrsshephards_regular;
                break;
            case 22:
                fontT = R.font.neucha_regular;
                break;
            case 23:
                fontT = R.font.pacifico_regular;
                break;
            case 24:
                fontT = R.font.patuaone_regular;
                break;
            case 25:
                fontT = R.font.poiretone_regular;
                break;
            case 26:
                fontT = R.font.pressstarrt2p_regular;
                break;
            case 27:
                fontT = R.font.rocksalt_regular;
                break;
            case 28:
                fontT = R.font.sacramento_regular;
                break;
            case 29:
                fontT = R.font.shadowintolight_regular;
                break;
            case 30:
                fontT = R.font.specialelite_regular;
                break;
        }
        return fontT;
    }

    private int getColored(int c){
        int x = 0;
        switch (c){
            case 1:
                x =  Color.rgb(188, 0, 80);
                break;
            case 2:
                x = Color.rgb(238, 46, 79);
                break;
            case 3:
                x = Color.rgb(251, 97, 7);
                break;
            case 4:
                x = Color.rgb(243,222,44);
                break;
            case 5:
                x = Color.rgb(124,181,24);
                break;
            case 6:
                x = Color.rgb(68,229,231);
                break;
            case 7:
                x = Color.rgb(92,122,255);
                break;
            case 8:
                x = Color.rgb(159,126,105);
                break;
        }
        return x;
    }

    private void addViews(List<String> quotesid, int counter){
        FirebaseDatabase.getInstance().getReference("Quotes").child(userid).child(quotesid.get(counter) + "").child("views").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(true);
    }

    private void seenNumbers(List<String> quotesid, int counter){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Quotes").child(userid).child(quotesid.get(counter) + "").child("views");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                seen_number.setText("" + dataSnapshot.getChildrenCount());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
