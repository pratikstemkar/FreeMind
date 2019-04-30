package com.protexcreative.freemind;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.internal.NavigationMenu;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.protexcreative.freemind.Fragment.HomeFragment;
import com.protexcreative.freemind.Fragment.NotificationFragment;
import com.protexcreative.freemind.Fragment.ProfileFragment;
import com.protexcreative.freemind.Fragment.SearchFragment;
import com.protexcreative.freemind.Fragment.TrendFragment;

import java.util.List;

import io.github.yavski.fabspeeddial.FabSpeedDial;

public class HomeActivity extends AppCompatActivity {

    // declare bottom navigation view
    BottomNavigationView bottomNavigationView;
    // set selected fragment to null
    Fragment selectedFragment = null;

    // on create method
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // get UI ids
        bottomNavigationView = findViewById(R.id.bottom_navigation);

        Glide.with(getApplicationContext()).resumeRequests();

        // set navigation selector method
        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);

        // create bundle intent and the extras
        Bundle intent = getIntent().getExtras();
        // if intent is null
        if(intent != null){
            // get author of posts from extras
            String author = intent.getString("authorid");

            if (author != null){
                // try to keep the author name as profileid in extras
                SharedPreferences.Editor editor = getSharedPreferences("PREFS", MODE_PRIVATE).edit();
                editor.putString("profileid", author);
                editor.apply();

                // by default the fragment of profile will be opened
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ProfileFragment()).commit();
            }
            else{
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new NotificationFragment()).commit();
            }



        } else{
            // if intent is null then home fragment will be shown
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
        }
    }



    // method to select a fragment from bottom navigation
    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                    // switch case for menu.xml file menu
                    switch(menuItem.getItemId()){
                        case R.id.nav_home:
                                selectedFragment = new HomeFragment();
                            break;

                        case R.id.nav_search:
                            selectedFragment = new SearchFragment();
                            break;

                        case R.id.nav_notification:
                            selectedFragment = new NotificationFragment();
                            break;

                        case R.id.nav_trend:
                            selectedFragment = new TrendFragment();
                            break;

                        case R.id.nav_profile:
                            SharedPreferences.Editor editor = getSharedPreferences("PREFS", MODE_PRIVATE).edit();
                            editor.putString("profileid", FirebaseAuth.getInstance().getCurrentUser().getUid());
                            editor.apply();
                            selectedFragment = new ProfileFragment();
                            break;
                    }

                    // if the selected fragment is not null
                    if(selectedFragment != null){
                        // go to a selected fragment
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                    }
                    return true;
                }
            };


    @Override
    public void onBackPressed(){


        ActivityManager mngr = (ActivityManager) getSystemService( ACTIVITY_SERVICE );

        List<ActivityManager.RunningTaskInfo> taskList = mngr.getRunningTasks(10);

        if(taskList.get(0).numActivities == 1 &&
                taskList.get(0).topActivity.getClassName().equals(this.getClass().getName())) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);

            builder.setTitle("Exit FreeMind?");
            builder.setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    HomeActivity.super.onBackPressed();
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                }
            });
            builder.show();
        }
        else
        {
            super.onBackPressed();
        }
    }
}


