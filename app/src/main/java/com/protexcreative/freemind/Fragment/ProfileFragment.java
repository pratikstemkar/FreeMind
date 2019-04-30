package com.protexcreative.freemind.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.protexcreative.freemind.BuildConfig;
import com.protexcreative.freemind.DPActivity;
import com.protexcreative.freemind.EditProfileActivity;
import com.protexcreative.freemind.FollowersActivity;
import com.protexcreative.freemind.HomeActivity;
import com.protexcreative.freemind.MainActivity;
import com.protexcreative.freemind.Model.Post;
import com.protexcreative.freemind.Model.Updates;
import com.protexcreative.freemind.Model.User;
import com.protexcreative.freemind.OptionActivity;
import com.protexcreative.freemind.PostActivity;
import com.protexcreative.freemind.R;
import com.protexcreative.freemind.StarActivity;
import com.protexcreative.freemind.UpdatesActivity;

import java.util.HashMap;


public class ProfileFragment extends Fragment implements RewardedVideoAdListener {

    ImageView image_profile, option, verified_img, update;
    TextView username, fullname, bio, instagram, alert;
    Button posts, stars, followers, following, editprofile;
    private AdView mAdView;
    FirebaseUser firebaseUser;

    private RewardedVideoAd mRewardedVideoAd;

    String profileid;
    String instagramid;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        SharedPreferences prefs = getContext().getSharedPreferences("PREFS", Context.MODE_PRIVATE);
        profileid = prefs.getString("profileid", "none");

        image_profile = view.findViewById(R.id.image_profile);
        option = view.findViewById(R.id.option);
        username = view.findViewById(R.id.username);
        fullname = view.findViewById(R.id.fullname);
        bio = view.findViewById(R.id.bio);
        instagram = view.findViewById(R.id.instagram);
        posts = view.findViewById(R.id.posts);
        stars = view.findViewById(R.id.stars);
        followers = view.findViewById(R.id.followers);
        following = view.findViewById(R.id.following);
        editprofile = view.findViewById(R.id.editprofile);
        alert = view.findViewById(R.id.alert);
        verified_img = view.findViewById(R.id.verified_img);
        update = view.findViewById(R.id.update);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), UpdatesActivity.class);
                startActivity(intent);
            }
        });

        checkUpdate(update);

        alert.setVisibility(View.GONE);

        userInfo();
        getFollowers();
        getNrPosts();
        getNrStars();

        MobileAds.initialize(getContext(),"ca-app-pub-9930376729131413~7469397260");

        // Get reference to singleton RewardedVideoAd object
        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(getContext());
        mRewardedVideoAd.setRewardedVideoAdListener(this);
        mRewardedVideoAd.loadAd("ca-app-pub-9930376729131413/4572333238", new AdRequest.Builder().build());


        image_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mRewardedVideoAd.isLoaded()){
                    mRewardedVideoAd.show();
                }else {
                    Intent intent = new Intent(getContext(), DPActivity.class);
                    intent.putExtra("profileid", profileid);
                    startActivity(intent);
                }
            }
        });


        mAdView = view.findViewById(R.id.adView);

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
        MobileAds.initialize(getContext(), "ca-app-pub-9930376729131413~7469397260");




        if(profileid.equals(firebaseUser.getUid())){
            editprofile.setText("EDIT PROFILE");
            option.setVisibility(View.VISIBLE);
        } else{
            checkFollow();
            option.setVisibility(View.GONE);
        }

        isVerified(verified_img);




        instagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.instagram.com/"+instagramid+"/")));
            }
        });

        option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), OptionActivity.class));
            }
        });

        editprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String btn = editprofile.getText().toString();

                if(btn.equals("EDIT PROFILE")){
                    startActivity(new Intent(getContext(), EditProfileActivity.class));
                } else if(btn.equals("follow")){
                    FirebaseDatabase.getInstance().getReference().child("Follow").child(firebaseUser.getUid())
                            .child("following").child(profileid).setValue(true);
                    FirebaseDatabase.getInstance().getReference().child("Follow").child(profileid)
                            .child("followers").child(firebaseUser.getUid()).setValue(true);

                    addNotifications();
                } else if(btn.equals("following")){
                    FirebaseDatabase.getInstance().getReference().child("Follow").child(firebaseUser.getUid())
                            .child("following").child(profileid).removeValue();
                    FirebaseDatabase.getInstance().getReference().child("Follow").child(profileid)
                            .child("followers").child(firebaseUser.getUid()).removeValue();
                }
            }
        });

        followers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), FollowersActivity.class);
                intent.putExtra("id", profileid);
                intent.putExtra("title", "Followers");
                startActivity(intent);
            }
        });

        following.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), FollowersActivity.class);
                intent.putExtra("id", profileid);
                intent.putExtra("title", "Following");
                startActivity(intent);
            }
        });

        stars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), StarActivity.class);
                intent.putExtra("id", profileid);
                intent.putExtra("title", "Star Posts");
                startActivity(intent);
            }
        });

        posts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), StarActivity.class);
                intent.putExtra("id", profileid);
                intent.putExtra("title", "Posts");
                startActivity(intent);
            }
        });

        return view;
    }

    private void addNotifications(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Notifications").child(profileid);

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("userid", firebaseUser.getUid());
        hashMap.put("text", "started following you.");
        hashMap.put("postid", "");
        hashMap.put("ispost", false);

        reference.push().setValue(hashMap);
    }

    private void userInfo(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(profileid);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(getContext() == null){
                    return;
                }

                User user = dataSnapshot.getValue(User.class);

                instagramid = user.getInstagram();

                Glide.with(getContext()).load(user.getImageurl()).into(image_profile);
                username.setText(user.getUsername());
                fullname.setText(user.getFullname());
                if(user.getBio().equals("")){
                    bio.setVisibility(View.GONE);
                }else{
                    bio.setText(user.getBio());
                }
                if(user.getInstagram().equals("")){
                    instagram.setVisibility(View.GONE);
                }else{
                    instagram.setText("@" + user.getInstagram());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void checkFollow(){

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
                .child("Follow").child(firebaseUser.getUid()).child("following");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(profileid).exists()){
                    editprofile.setText("following");
                } else{
                    editprofile.setText("follow");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void getFollowers(){

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
                .child("Follow").child(profileid).child("followers");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                followers.setText("" + dataSnapshot.getChildrenCount() + "\nFollowers");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        DatabaseReference references = FirebaseDatabase.getInstance().getReference()
                .child("Follow").child(profileid).child("following");

        references.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                following.setText("" + dataSnapshot.getChildrenCount() + "\nFollowing");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void getNrPosts(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Posts");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int i = 0;
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    Post post = snapshot.getValue(Post.class);
                    if(post.getAuthor().equals(profileid)){
                        i++;
                    }
                }

                posts.setText("" + i + "\nPOSTS");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void getNrStars(){
        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Stars").child(profileid);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                stars.setText(dataSnapshot.getChildrenCount() + "\nSTARS");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onRewardedVideoAdLoaded() {
        image_profile.setEnabled(true);
        alert.setVisibility(View.VISIBLE);
    }

    @Override
    public void onRewardedVideoAdOpened() {

    }

    @Override
    public void onRewardedVideoStarted() {

    }

    @Override
    public void onRewardedVideoAdClosed() {
        mRewardedVideoAd.loadAd("ca-app-pub-9930376729131413/4572333238", new AdRequest.Builder().build());
        alert.setVisibility(View.GONE);
    }

    @Override
    public void onRewarded(RewardItem rewardItem) {
        Toast.makeText(getContext(), "You recieved " + rewardItem.getAmount() + " " + rewardItem.getType() +"!", Toast.LENGTH_SHORT).show();
        alert.setVisibility(View.GONE);
        Intent intent = new Intent(getContext(), DPActivity.class);
        intent.putExtra("profileid", profileid);
        startActivity(intent);
    }

    @Override
    public void onRewardedVideoAdLeftApplication() {
        alert.setVisibility(View.GONE);
    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int i) {
        mRewardedVideoAd.loadAd("ca-app-pub-9930376729131413/4572333238", new AdRequest.Builder().build());
    }

    @Override
    public void onRewardedVideoCompleted() {
        mRewardedVideoAd.loadAd("ca-app-pub-9930376729131413/4572333238", new AdRequest.Builder().build());
        alert.setVisibility(View.GONE);
    }

    private void isVerified(final ImageView imageView){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(profileid);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                if (user.getVerified()){
                    imageView.setVisibility(View.VISIBLE);
                }else{
                    imageView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void checkUpdate(final ImageView imageView){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Updates");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Updates updates = snapshot.getValue(Updates.class);
                    if (updates.getVersionCode() == BuildConfig.VERSION_CODE){
                        imageView.setVisibility(View.GONE);
                    } else{
                        imageView.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
