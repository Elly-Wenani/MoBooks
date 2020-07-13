package com.example.mobooks;

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

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;

public class BsBooksAdapter extends RecyclerView.Adapter<BsBooksAdapter.DealViewHolder>{

    ArrayList<BooksMode> mDeals;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private ChildEventListener mChildEventListener;
    private ImageView mImageDeal;

    public BsBooksAdapter() {
        //FirebaseUtil.openFbReference("traveldeals");

        mFirebaseDatabase = FirebaseUtil.mFirebaseDatabase;
        mDatabaseReference = FirebaseUtil.mDatabaseReference;
        mDeals = FirebaseUtil.mDeals;
        mChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                BooksMode td = snapshot.getValue(BooksMode.class);
                Log.d("Deal: ", td.getTitle());
                td.setId(snapshot.getKey());
                mDeals.add(td);
                notifyItemInserted(mDeals.size() - 1);
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
    public DealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.business_books_row, parent, false);
        return new DealViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DealViewHolder holder, int position) {
        BooksMode deal = mDeals.get(position);
        holder.bind(deal);
    }

    @Override
    public int getItemCount() {
        return mDeals.size();
    }

    public class DealViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvTitle;
        TextView tvDescription;
        TextView tvPrice;

        public DealViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            //tvPrice = itemView.findViewById(R.id.tvPrice);
            mImageDeal = itemView.findViewById(R.id.imageDeal);
            itemView.setOnClickListener(this);
        }

        public void bind(BooksMode deal) {

            tvTitle.setText(deal.getTitle());
            tvDescription.setText(deal.getDescription());
            //tvPrice.setText(deal.getPrice());
            showImage(deal.getImageUrl());

//            if (deal.getPrice() == null) {
//                tvPrice.setText("0.00");
//            }
        }

        @Override
        public void onClick(View v) {
//            int position = getAdapterPosition();
//            Log.d("Click: ", String.valueOf(position));
//            BooksMode selectedDeal = mDeals.get(position);
//            Intent intent = new Intent(v.getContext(), DealInsertActivity.class);
//            intent.putExtra("Deal", selectedDeal);
//            v.getContext().startActivity(intent);
        }

        private void showImage(String url){
            if (url != null && url.isEmpty() == false){
                Picasso.with(mImageDeal.getContext())
                        .load(url)
                        .resize(80, 80)
                        .centerCrop()
                        .into(mImageDeal);
            }
        }
    }

}
