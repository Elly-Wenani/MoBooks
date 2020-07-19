package com.example.mobooks.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobooks.DataModels.LocalBooksMode;
import com.example.mobooks.R;

import java.util.ArrayList;

public class LocalBooksAdapter extends RecyclerView.Adapter<LocalBooksAdapter.ViewHolder> {

    private ArrayList<LocalBooksMode> bookDataSet;

    Context mContext;
    private OnBookClickListener mOnBookClickListener;

    public LocalBooksAdapter(Context mContext, ArrayList<LocalBooksMode> mLocalDataSet, OnBookClickListener mOnBookClickListener) {
        this.bookDataSet = mLocalDataSet;
        this.mContext = mContext;
        this.mOnBookClickListener = mOnBookClickListener;
    }

    @Override
    public int getItemCount() {
        return bookDataSet.size();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_books_row, parent, false);
        return new ViewHolder(v, mOnBookClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        LocalBooksMode bookTitle = bookDataSet.get(position);
        LocalBooksMode bookAuthor = bookDataSet.get(position);

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
