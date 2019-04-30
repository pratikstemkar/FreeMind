package com.protexcreative.freemind.Adapter;


import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.protexcreative.freemind.Model.Quotes;
import com.protexcreative.freemind.Model.User;
import com.protexcreative.freemind.QuotesActivity;
import com.protexcreative.freemind.R;

import java.util.List;

public class QuotesAdapter extends RecyclerView.Adapter<QuotesAdapter.ViewHolder>{

    private Context mContext;
    private List<Quotes> mQuotes;

    public QuotesAdapter(Context mContext, List<Quotes> mQuotes) {
        this.mContext = mContext;
        this.mQuotes = mQuotes;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.quotes_item, viewGroup, false);
        return new QuotesAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final Quotes quotes = mQuotes.get(i);

        if(quotes.getUserid()!=null)
        {
            userInfo(viewHolder, quotes.getUserid());
            seenQuotes(viewHolder, quotes.getUserid());
        }

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 Intent intent = new Intent(mContext, QuotesActivity.class);
                 intent.putExtra("userid", quotes.getUserid());
                 mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mQuotes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView quotes_photo, quotes_photo_seen;
        public TextView quotes_username;

        public ViewHolder(@NonNull View itemView){
            super(itemView);

            quotes_photo = itemView.findViewById(R.id.quotes_photo);
            quotes_photo_seen = itemView.findViewById(R.id.quotes_photo_seen);
            quotes_username = itemView.findViewById(R.id.quotes_username);
        }

    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0){
            return 0;
        }
        return 1;
    }

    private void userInfo(final ViewHolder viewHolder, String userid){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(userid);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                Glide.with(mContext).load(user.getImageurl()).into(viewHolder.quotes_photo);
                Glide.with(mContext).load(user.getImageurl()).into(viewHolder.quotes_photo_seen);
                viewHolder.quotes_username.setText(user.getUsername());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void seenQuotes(final ViewHolder viewHolder, String userid){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Quotes").child(userid);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int i = 0;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    if(!snapshot.child("views").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).exists() && System.currentTimeMillis() < snapshot.getValue(Quotes.class).getTimeend()){
                        i++;
                    }
                }

                if(i > 0){
                    viewHolder.quotes_photo.setVisibility(View.VISIBLE);
                    viewHolder.quotes_photo_seen.setVisibility(View.GONE);
                } else{
                    viewHolder.quotes_photo.setVisibility(View.GONE);
                    viewHolder.quotes_photo_seen.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}