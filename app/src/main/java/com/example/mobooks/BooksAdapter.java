package com.example.mobooks;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.ViewHolder> {

    private ArrayList<DataManager> bookDataset;

    Context mContext;
    private OnBookClickListener mOnBookClickListener;

    public BooksAdapter(Context mContext, ArrayList<DataManager> mDataset, OnBookClickListener mOnBookClickListener) {
        this.bookDataset = mDataset;
        this.mContext = mContext;
        this.mOnBookClickListener = mOnBookClickListener;
    }

    @Override
    public int getItemCount() {
        return bookDataset.size();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_books_row, parent, false);
        return new ViewHolder(v, mOnBookClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        DataManager bookTitle = bookDataset.get(position);
        DataManager bookAuthor = bookDataset.get(position);

        holder.tvBookTitle.setText(bookTitle.getBookTitle());
        holder.tvBookAuthor.setText(bookAuthor.getBookAuthor());
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView tvBookTitle;
        public TextView tvBookAuthor;
        OnBookClickListener mOnBookClickListener;

        public ViewHolder(View itemView, OnBookClickListener mOnBookClickListener) {
            super(itemView);
            tvBookTitle = itemView.findViewById(R.id.tvBookTitle);
            tvBookAuthor = itemView.findViewById(R.id.tvBookAuthor);
            this.mOnBookClickListener = mOnBookClickListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mOnBookClickListener.onBookClick(v, getAdapterPosition());
        }
    }

    public interface OnBookClickListener{
        void onBookClick(View v, int position);
    }
}
