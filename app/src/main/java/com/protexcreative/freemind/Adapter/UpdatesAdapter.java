package com.protexcreative.freemind.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.protexcreative.freemind.BuildConfig;
import com.protexcreative.freemind.Model.Updates;
import com.protexcreative.freemind.Model.User;
import com.protexcreative.freemind.R;

import java.util.List;

public class UpdatesAdapter extends RecyclerView.Adapter<UpdatesAdapter.ViewHolder>{

    private Context mContext;
    private List<Updates> mUpdates;

    private FirebaseUser firebaseUser;

    public UpdatesAdapter(Context mContext, List<Updates> mUpdates) {
        this.mContext = mContext;
        this.mUpdates = mUpdates;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.updates_item, viewGroup, false);
        return new UpdatesAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        final Updates updates = mUpdates.get(i);

        if (updates.getLink().equals("")){
            viewHolder.download.setVisibility(View.GONE);
        } else{
            viewHolder.download.setVisibility(View.VISIBLE);
        }

        if (updates.getVersionCode() == BuildConfig.VERSION_CODE){
            viewHolder.download.setText("Installed");
        } else{
            viewHolder.download.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(updates.getLink()));
                    mContext.startActivity(intent);
                }
            });
        }

        viewHolder.version.setText(updates.getVersion());
        viewHolder.description.setText(updates.getDescription());
        viewHolder.date.setText(updates.getDate());
        viewHolder.size.setText(updates.getSize());


    }

    @Override
    public int getItemCount() {
        return mUpdates.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView version, size, date, description;
        public Button download;

        public ViewHolder(@NonNull View itemView){
            super(itemView);

            version = itemView.findViewById(R.id.version);
            size = itemView.findViewById(R.id.size);
            date = itemView.findViewById(R.id.date);
            description = itemView.findViewById(R.id.description);
            download = itemView.findViewById(R.id.download);
        }
    }
}
