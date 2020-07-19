package com.example.mobooks.Adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobooks.DataModels.OnlineBooksMode;
import com.example.mobooks.FirebaseUtil;
import com.example.mobooks.OnlinePdfViewerActivity;
import com.example.mobooks.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class BiographyAdapter extends RecyclerView.Adapter<BiographyAdapter.BioViewHolder>{

    ArrayList<OnlineBooksMode> onlineBooksSet;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private ChildEventListener mChildEventListener;
    private ImageView onlineBookImage;

    public BiographyAdapter() {

        mFirebaseDatabase = FirebaseUtil.mFirebaseDatabase;
        mDatabaseReference = FirebaseUtil.mDatabaseReference;
        onlineBooksSet = FirebaseUtil.onlineBooksSet;
        mChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                OnlineBooksMode td = snapshot.getValue(OnlineBooksMode.class);
                Log.d("Biography Book: ", td.getBkTitle());
                td.setId(snapshot.getKey());
                onlineBooksSet.add(td);
                notifyItemInserted(onlineBooksSet.size() - 1);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        mDatabaseReference.addChildEventListener(mChildEventListener);
    }

    @NonNull
    @Override
    public BioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.books_row, parent, false);
        return new BioViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BiographyAdapter.BioViewHolder holder, int position) {
        OnlineBooksMode onlineBooksMode = onlineBooksSet.get(position);
        holder.bind(onlineBooksMode);
    }

    @Override
    public int getItemCount() {
        return onlineBooksSet.size();
    }

    public class BioViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvTitle;
        TextView tvAuthor;

        public BioViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.idTvTitle);
            tvAuthor = itemView.findViewById(R.id.idTvAuthor);
            onlineBookImage = itemView.findViewById(R.id.idBookImage);
            itemView.setOnClickListener(this);
        }

        public void bind(OnlineBooksMode onlineBooksMode) {
            tvTitle.setText(onlineBooksMode.getBkTitle());
            tvAuthor.setText(onlineBooksMode.getBkAuthor());
            showImage(onlineBooksMode.getBkImageUrl());
        }

        //Pass intent to OnlinePdfViewer Activity
        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Log.d("Click: ", String.valueOf(position));
            OnlineBooksMode selectedDeal = onlineBooksSet.get(position);
            Intent intent = new Intent(v.getContext(), OnlinePdfViewerActivity.class);
            intent.putExtra("Books", selectedDeal);
            v.getContext().startActivity(intent);
        }

        //Load image on recycler view
        private void showImage(String url){
            if (url != null && !url.isEmpty()){
                Picasso.with(onlineBookImage.getContext())
                        .load(url)
                        .resize(80, 80)
                        .centerCrop()
                        .into(onlineBookImage);
            }
        }
    }
}