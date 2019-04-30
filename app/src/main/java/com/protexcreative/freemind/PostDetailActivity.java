package com.protexcreative.freemind;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.protexcreative.freemind.Adapter.CommentAdapter;
import com.protexcreative.freemind.Adapter.PostAdapter;
import com.protexcreative.freemind.Adapter.PostDetailAdapter;
import com.protexcreative.freemind.Model.Comment;
import com.protexcreative.freemind.Model.Post;
import com.protexcreative.freemind.Model.User;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class PostDetailActivity extends AppCompatActivity {

    String postid, authorid;
    RecyclerView recycler_view, recycler_view_com;
    PostDetailAdapter postDetailAdapter;
    CommentAdapter commentAdapter;
    List<Post> postList;
    List<Comment> commentList;

    private AdView mAdView1;
    EditText addcomment;
    ImageView image_profile, close;
    TextView post;

    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);

        close = findViewById(R.id.close);

        recycler_view = findViewById(R.id.recycler_view);
        recycler_view_com = findViewById(R.id.recycler_view_com);

        Intent intent = getIntent();
        postid = intent.getStringExtra("postid");
        authorid = intent.getStringExtra("authorid");

        recycler_view.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recycler_view.setLayoutManager(linearLayoutManager);
        postList = new ArrayList<>();
        postDetailAdapter = new PostDetailAdapter(getApplicationContext(), postList);
        recycler_view.setAdapter(postDetailAdapter);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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


        recycler_view_com.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManagerCom = new LinearLayoutManager(getApplicationContext());
        recycler_view_com.setLayoutManager(linearLayoutManagerCom);
        commentList = new ArrayList<>();
        commentAdapter = new CommentAdapter(getApplicationContext(), commentList);
        recycler_view_com.setAdapter(commentAdapter);

        addcomment = findViewById(R.id.add_comment);
        image_profile = findViewById(R.id.image_profile);
        post = findViewById(R.id.post);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (addcomment.getText().toString().equals("")){
                    Toast.makeText(PostDetailActivity.this, "You can't send empty comment.", Toast.LENGTH_SHORT).show();

                } else{
                    addComment();
                }
            }
        });

        getImage();
        readPosts();
        readComments();

    }

    private void readPosts(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Posts").child(postid);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                postList.clear();
                Post post = dataSnapshot.getValue(Post.class);
                postList.add(post);

                postDetailAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void readComments(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Comments").child(postid);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                commentList.clear();
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    Comment comment = snapshot.getValue(Comment.class);
                    commentList.add(comment);
                }
                Collections.reverse(commentList);
                commentAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void addComment(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Comments").child(postid);

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("comment", addcomment.getText().toString());
        hashMap.put("author", firebaseUser.getUid());
        hashMap.put("date", getDateTime());

        reference.push().setValue(hashMap);
        addNotifications();
        addcomment.setText("");
    }

    private void getImage() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                Glide.with(getApplicationContext()).load(user.getImageurl()).into(image_profile);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void addNotifications(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Notifications").child(authorid);

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("userid", firebaseUser.getUid());
        hashMap.put("text", "commented: " + addcomment.getText().toString());
        hashMap.put("postid", postid);
        hashMap.put("ispost", true);

        reference.push().setValue(hashMap);
    }

    // method to get the date
    private String getDateTime() {
        // set date format
        DateFormat dateFormat = new SimpleDateFormat("E, dd MMM HH:mm");
        Date date = new Date();
        return dateFormat.format(date);
    }
}
