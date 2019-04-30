package com.protexcreative.freemind.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
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
import com.protexcreative.freemind.Adapter.PostAdapter;
import com.protexcreative.freemind.Model.Post;
import com.protexcreative.freemind.Model.User;
import com.protexcreative.freemind.R;

import java.util.ArrayList;
import java.util.List;


public class TrendFragment extends Fragment {

    private RecyclerView recyclerView;
    private PostAdapter postAdapter;
    private List<Post> postLists;

    private ImageView filter;
    private AdView mAdView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_trend, container, false);
        recyclerView = view.findViewById(R.id.recycler_view);
        filter = view.findViewById(R.id.filter);
        final ProgressBar progressBar = view.findViewById(R.id.progressBar);

        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final PopupMenu popupMenu = new PopupMenu(getContext(), v);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.all:
                                readPosts(progressBar, "all");
                                return true;
                            case R.id.relationships:
                                readPosts(progressBar, "Relationships");
                                return true;
                            case R.id.work:
                                readPosts(progressBar, "Work");
                                return  true;
                            case R.id.friends:
                                readPosts(progressBar, "Friends");
                                return true;
                            case R.id.education:
                                readPosts(progressBar, "Education");
                                return true;
                            case R.id.family:
                                readPosts(progressBar, "Family");
                                return true;
                            case R.id.hopes:
                                readPosts(progressBar, "Hopes");
                                return true;
                            case R.id.mystory:
                                readPosts(progressBar, "My Story");
                                return true;
                            case R.id.mentalhealth:
                                readPosts(progressBar, "Mental Health");
                                return true;
                            default:
                                return false;
                        }
                    }
                });
                popupMenu.inflate(R.menu.cat_menu);
                popupMenu.show();
            }
        });

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        postLists = new ArrayList<>();
        postAdapter = new PostAdapter(getContext(), postLists, true);
        recyclerView.setAdapter(postAdapter);

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

        readPosts(progressBar, "all");

        return view;
    }

    private void readPosts(final ProgressBar progressBar, final String category){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Posts");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                postLists.clear();
                User user = new User();
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    Post post = snapshot.getValue(Post.class);
                        if (category.equals("all")){
                                postLists.add(post);
                        }
                        if (post.getCategory().equals(category)){
                                postLists.add(post);
                        }
                }

                postAdapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}
