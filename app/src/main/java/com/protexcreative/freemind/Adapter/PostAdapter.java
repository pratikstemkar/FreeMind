package com.protexcreative.freemind.Adapter;

import android.app.AlertDialog;
import android.app.Notification;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.protexcreative.freemind.CommentsActivity;
import com.protexcreative.freemind.FollowersActivity;
import com.protexcreative.freemind.Fragment.ProfileFragment;
import com.protexcreative.freemind.HomeActivity;
import com.protexcreative.freemind.Model.Post;
import com.protexcreative.freemind.Model.User;
import com.protexcreative.freemind.PostDetailActivity;
import com.protexcreative.freemind.R;

import java.util.HashMap;
import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder>{

    public Context mContext;
    public List<Post> mPost;
    public Boolean isFragment;
    public Boolean check = false;

    private FirebaseUser firebaseUser;

    public PostAdapter(Context mContext, List<Post> mPost, Boolean isFragment) {
        this.mContext = mContext;
        this.mPost = mPost;
        this.isFragment = isFragment;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.post_item, viewGroup, false);
        return new PostAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        final Post post = mPost.get(i);


        viewHolder.text_post.setText(post.getText_post());
        viewHolder.text_post.setBackgroundColor(post.getColor());
        viewHolder.category.setText(post.getCategory());
        viewHolder.category.setTextColor(post.getColor());
        viewHolder.date.setText(post.getDate());
        viewHolder.text_post.setTypeface(ResourcesCompat.getFont(mContext, getFont(post.getFont())));

        authorInfo(viewHolder.image_profile, viewHolder.username, post.getAuthor(), post.getAnonymous());
        isLikes(post.getPostid(), viewHolder.hot);
        nrLikes(viewHolder.likes, post.getPostid());
        getComments(post.getPostid(), viewHolder.comments);
        isSaved(post.getPostid(), viewHolder.star);
        isVerified(post.getAuthor(), viewHolder.verified_img, viewHolder.username);




        viewHolder.star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(viewHolder.star.getTag().equals("star")){
                    FirebaseDatabase.getInstance().getReference().child("Stars").child(firebaseUser.getUid())
                            .child(post.getPostid()).setValue(true);
                } else{
                    FirebaseDatabase.getInstance().getReference().child("Stars").child(firebaseUser.getUid())
                            .child(post.getPostid()).removeValue();
                }
            }
        });

        viewHolder.text_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, PostDetailActivity.class);
                intent.putExtra("postid", post.getPostid());
                intent.putExtra("authorid", post.getAuthor());
                mContext.startActivity(intent);
            }
        });

        viewHolder.hot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(viewHolder.hot.getTag().equals("hot")){
                    FirebaseDatabase.getInstance().getReference().child("Likes").child(post.getPostid())
                            .child(firebaseUser.getUid()).setValue(true);
                    addNotifications(post.getAuthor(), post.getPostid());
                } else{
                    FirebaseDatabase.getInstance().getReference().child("Likes").child(post.getPostid())
                            .child(firebaseUser.getUid()).removeValue();
                }
            }
        });

        viewHolder.comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, CommentsActivity.class);
                intent.putExtra("postid", post.getPostid());
                intent.putExtra("authorid", post.getAuthor());
                mContext.startActivity(intent);
            }
        });

        viewHolder.comments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, CommentsActivity.class);
                intent.putExtra("postid", post.getPostid());
                intent.putExtra("authorid", post.getAuthor());
                mContext.startActivity(intent);
            }
        });

        if (post.getAnonymous()){

        } else {
            viewHolder.username.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(isFragment){
                        SharedPreferences.Editor editor = mContext.getSharedPreferences("PREFS", Context.MODE_PRIVATE).edit();
                        editor.putString("profileid", post.getAuthor());
                        editor.apply();

                        ((FragmentActivity)mContext).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                new ProfileFragment()).commit();
                    } else{
                        Intent intent = new Intent(mContext, HomeActivity.class);
                        intent.putExtra("authorid", post.getAuthor());
                        mContext.startActivity(intent);
                    }
                }
            });

            viewHolder.image_profile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(isFragment){
                        SharedPreferences.Editor editor = mContext.getSharedPreferences("PREFS", Context.MODE_PRIVATE).edit();
                        editor.putString("profileid", post.getAuthor());
                        editor.apply();

                        ((FragmentActivity)mContext).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                new ProfileFragment()).commit();
                    } else{
                        Intent intent = new Intent(mContext, HomeActivity.class);
                        intent.putExtra("authorid", post.getAuthor());
                        mContext.startActivity(intent);
                    }
                }
            });
        }



        viewHolder.likes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, FollowersActivity.class);
                intent.putExtra("id", post.getPostid());
                intent.putExtra("title", "Likes");
                mContext.startActivity(intent);
            }
        });

        viewHolder.more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final PopupMenu popupMenu = new PopupMenu(mContext, v);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.report:
                                if (checkReport(post.getPostid())){
                                    report(post.getPostid());
                                } else {
                                    Toast.makeText(mContext, "Post Already Reported.", Toast.LENGTH_SHORT).show();
                                }
                                return true;
                            case R.id.delete:
                                deletePost(post.getPostid());
                                return true;
                                default:
                                    return false;
                        }
                    }
                });
                popupMenu.inflate(R.menu.post_menu);
                if (!post.getAuthor().equals(firebaseUser.getUid())){
                    popupMenu.getMenu().findItem(R.id.delete).setVisible(false);
                }
                popupMenu.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return mPost.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView image_profile, hot, comment, star, verified_img, more;
        public TextView username, likes, date, text_post, comments, category;

        public ViewHolder(@NonNull View itemView){
            super(itemView);

            image_profile = itemView.findViewById(R.id.image_profile);
            hot = itemView.findViewById(R.id.hot);
            comment = itemView.findViewById(R.id.comment);
            star = itemView.findViewById(R.id.star);
            username = itemView.findViewById(R.id.username);
            likes = itemView.findViewById(R.id.likes);
            date = itemView.findViewById(R.id.date);
            text_post = itemView.findViewById(R.id.text_post);
            comments = itemView.findViewById(R.id.comments);
            category = itemView.findViewById(R.id.category);
            verified_img = itemView.findViewById(R.id.verified_img);
            more = itemView.findViewById(R.id.more);
        }
    }

    private void getComments(String postid, final TextView comments){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Comments").child(postid);


        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                comments.setText("View all " + dataSnapshot.getChildrenCount() + " Comments");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void isLikes(String postid, final ImageView imageView){

        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
                .child("Likes")
                .child(postid);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(firebaseUser.getUid()).exists()){
                    imageView.setImageResource(R.drawable.ic_like);
                    imageView.setTag("liked");
                } else{
                    imageView.setImageResource(R.drawable.ic_hot);
                    imageView.setTag("hot");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void addNotifications(String userid, String postid){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Notifications").child(userid);

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("userid", firebaseUser.getUid());
        hashMap.put("text", "liked your post.");
        hashMap.put("postid", postid);
        hashMap.put("ispost", true);

        reference.push().setValue(hashMap);
    }

    private void nrLikes(final TextView likes, String postid){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Likes")
                .child(postid);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                likes.setText(dataSnapshot.getChildrenCount() + " likes");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void authorInfo(final ImageView image_profile, final TextView username, String userid, final Boolean anonym){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(userid);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                if(anonym){
                    Glide.with(mContext).load("https://firebasestorage.googleapis.com/v0/b/freemind-dc8cd.appspot.com/o/anonymous-512.png?alt=media&token=29de2c3f-ed72-4da3-ab6c-6ae72f6d8b66").into(image_profile);
                    username.setText("anonymous");
                } else{
                    Glide.with(mContext).load(user.getImageurl()).into(image_profile);
                    username.setText(user.getUsername());
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void isSaved(final String postid, final ImageView imageView){
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Stars")
                .child(firebaseUser.getUid());

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(postid).exists()){
                    imageView.setImageResource(R.drawable.ic_stared);
                    imageView.setTag("stared");
                } else{
                    imageView.setImageResource(R.drawable.ic_star);
                    imageView.setTag("star");
                }
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

    private void isVerified(String userid, final ImageView imageView, final TextView textView){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(userid);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                if (user.getVerified() && !textView.getText().toString().equals("anonymous")){
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

    private Boolean checkReport(final String postid){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Reports");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!dataSnapshot.child(postid).exists()){
                    check = true;
                } else{
                    check = false;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return check;
    }

    private void report(String postid){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Reports").child(postid);
        reference.setValue(true)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(mContext, "Post Reported !!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void deletePost(final String postid){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
        alertDialog.setTitle("Delete Post?");

        alertDialog.setPositiveButton("Delete",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Posts").child(postid);
                        reference.removeValue()
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()){
                                            Toast.makeText(mContext, "Post Deleted!", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
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


}
