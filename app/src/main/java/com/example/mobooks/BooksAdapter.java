package com.example.mobooks;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.MyViewHolder> {

    private List<DataManager> mDataset;
    Context mContext;

    public BooksAdapter(Context mContext, List<DataManager> mDataset) {
        this.mDataset = mDataset;
        this.mContext = mContext;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvBookTitle;
        public TextView tvBookAuthor;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvBookTitle = itemView.findViewById(R.id.tvBookTitle);
            tvBookAuthor = itemView.findViewById(R.id.tvBookAuthor);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public BooksAdapter(List<DataManager> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public BooksAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_books_items, parent, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        DataManager bookTitle = mDataset.get(position);
        DataManager bookAuthor = mDataset.get(position);

        holder.tvBookTitle.setText(bookTitle.getBookTitle());
        holder.tvBookAuthor.setText(bookAuthor.getBookAuthor());
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
