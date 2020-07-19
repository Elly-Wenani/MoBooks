package com.example.mobooks;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;


import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class BookInsertActivity extends AppCompatActivity {

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private EditText insertBookTitle;
    private EditText insertAuthor;
    private EditText insertImageUrl;
    private EditText insertBookUrl;
    private BooksMode onlineBooksSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_insert);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("MoBooks");

        mFirebaseDatabase = FirebaseUtil.mFirebaseDatabase;
        mDatabaseReference = FirebaseUtil.mDatabaseReference;

        insertBookTitle = findViewById(R.id.insertTitle);
        insertAuthor = findViewById(R.id.insertAuthor);
        insertImageUrl = findViewById(R.id.insertImageUrl);
        insertBookUrl = findViewById(R.id.insertBookUrl);

        final Intent intent = getIntent();
        BooksMode books = (BooksMode) intent.getSerializableExtra("Books");
        if (books == null) {
            books = new BooksMode();
        }
        this.onlineBooksSet = books;
        insertBookTitle.setText(books.getBkTitle());
        insertAuthor.setText(books.getBkAuthor());
        insertImageUrl.setText(books.getBkImageUrl());
        insertBookUrl.setText(books.getBkFileUrl());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:

                String title = insertBookTitle.getText().toString();
                String author = insertAuthor.getText().toString();
                String imageUrl = insertImageUrl.getText().toString();
                String bookUrl = insertBookUrl.getText().toString();

                if (title.isEmpty() && author.isEmpty() && imageUrl.isEmpty() && bookUrl.isEmpty()) {
                    new AlertDialog.Builder(this)
                            .setTitle("MoBooks")
                            .setMessage("Fields cant be empty")
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(getApplicationContext(), "Enter book details", Toast.LENGTH_SHORT).show();
                                }
                            }).show();
                } else if (title.isEmpty()) {
                    insertBookTitle.setError("Enter Book Title");
                    insertBookTitle.requestFocus();
                } else if (author.isEmpty()) {
                    insertAuthor.setError("Enter Book Author");
                    insertAuthor.requestFocus();
                } else if (imageUrl.isEmpty()) {
                    insertImageUrl.setError("Enter Image Url");
                    insertImageUrl.requestFocus();
                } else if (bookUrl.isEmpty()) {
                    insertBookUrl.setError("Enter Book Url");
                    insertBookUrl.requestFocus();
                } else {
                    new AlertDialog.Builder(this)
                            .setTitle("Save Book")
                            .setMessage("Are you sure you want to save this book?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //If the admin clicks yes the books will be uploaded to the database
                                    saveBook();
                                }
                            })
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //Nothing happens when no is clicked
                                }
                            }).show();
                }
                break;

            case R.id.action_delete:
                Toast.makeText(this, "Perform Delete", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    private void clearText() {
        insertBookTitle.setText("");
        insertAuthor.setText("");
        insertImageUrl.setText("");
        insertBookUrl.setText("");
    }

    private void saveBook() {

        onlineBooksSet.setBkTitle(insertBookTitle.getText().toString().trim());
        onlineBooksSet.setBkAuthor(insertAuthor.getText().toString().trim());
        onlineBooksSet.setBkImageUrl(insertImageUrl.getText().toString().trim());
        onlineBooksSet.setBkFileUrl(insertBookUrl.getText().toString().trim());
        Toast.makeText(this, "Book Saved", Toast.LENGTH_SHORT).show();

        //Updates the values
        if (onlineBooksSet.getId() == null) {
            mDatabaseReference.push().setValue(onlineBooksSet);
        } else {
            mDatabaseReference.child(onlineBooksSet.getId()).setValue(onlineBooksSet);
        }
        clearText();
    }
}