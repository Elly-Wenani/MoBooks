package com.example.mobooks;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;


import com.example.mobooks.DataModels.OnlineBooksMode;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;


import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class BookInsertActivity extends AppCompatActivity {

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private EditText insertBookTitle;
    private EditText insertAuthor;
    private EditText insertImageUrl;
    private EditText insertBookUrl;
    private OnlineBooksMode onlineBooksSet;
    private SweetAlertDialog sweetAlertDialog;
    private Button btnSave;

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
        btnSave = findViewById(R.id.btnSave);


        final Intent intent = getIntent();
        OnlineBooksMode books = (OnlineBooksMode) intent.getSerializableExtra("Books");
        if (books == null) {
            books = new OnlineBooksMode();
        }
        this.onlineBooksSet = books;
        insertBookTitle.setText(books.getBkTitle());
        insertAuthor.setText(books.getBkAuthor());
        insertImageUrl.setText(books.getBkImageUrl());
        insertBookUrl.setText(books.getBkFileUrl());

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Verify();
            }
        });
    }

    public void Verify() {
        String title = insertBookTitle.getText().toString();
        String author = insertAuthor.getText().toString();
        String imageUrl = insertImageUrl.getText().toString();
        String bookUrl = insertBookUrl.getText().toString();

        if (title.isEmpty() && author.isEmpty() && imageUrl.isEmpty() && bookUrl.isEmpty()) {

            sweetAlertDialog = new SweetAlertDialog(BookInsertActivity.this,
                    SweetAlertDialog.ERROR_TYPE);
            sweetAlertDialog.setTitleText("Fields cant be empty")
                    .setConfirmButton("Ok", new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            insertBookTitle.requestFocus();
                            sweetAlertDialog.cancel();
                            insertBookTitle.setError("Enter Book Title");
                        }
                    })
                    .show();

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

            sweetAlertDialog = new SweetAlertDialog(BookInsertActivity.this, SweetAlertDialog.NORMAL_TYPE);
            sweetAlertDialog.setTitleText("Are you sure?")
                    .setConfirmButton("Yes", new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.hide();
                            saveBook();
                        }
                    }).setCancelButton("No", new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    //Nothing happens when no is clicked
                    sweetAlertDialog.cancel();
                }
            }).show();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_delete:
                deleteBook();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void deleteBook() {
        Toast.makeText(this, "To delete book", Toast.LENGTH_SHORT).show();
    }

    private void clearText() {
        insertBookTitle.setText("");
        insertAuthor.setText("");
        insertImageUrl.setText("");
        insertBookUrl.setText("");
    }

    private void saveBook() {

        disableAllEditText();

        sweetAlertDialog = new SweetAlertDialog(BookInsertActivity.this,
                SweetAlertDialog.PROGRESS_TYPE);
//        sweetAlertDialog.getProgressHelper().setBarColor(Color.parseColor("#3B5998"));
        sweetAlertDialog.setTitleText("Please wait")
                .hideConfirmButton()
                .setCancelable(false);
        sweetAlertDialog.show();

        onlineBooksSet.setBkTitle(insertBookTitle.getText().toString().trim());
        onlineBooksSet.setBkAuthor(insertAuthor.getText().toString().trim());
        onlineBooksSet.setBkImageUrl(insertImageUrl.getText().toString().trim());
        onlineBooksSet.setBkFileUrl(insertBookUrl.getText().toString().trim());

        //Updates the values
        if (onlineBooksSet.getId() == null) {
            mDatabaseReference.push().setValue(onlineBooksSet)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                            sweetAlertDialog.hide();
                            enableAllEditText();
                            isSuccessful();
                            clearText();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            sweetAlertDialog.hide();
                            enableAllEditText();
                            notSuccessful();
                        }
                    });
        } else {
            mDatabaseReference.child(onlineBooksSet.getId()).setValue(onlineBooksSet)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            sweetAlertDialog.hide();
                            enableAllEditText();
                            isSuccessful();
                            clearText();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            sweetAlertDialog.hide();
                            enableAllEditText();
                            notSuccessful();
                        }
                    });
        }
    }

    private void disableAllEditText() {

        insertBookTitle.setEnabled(false);
        insertAuthor.setEnabled(false);
        insertImageUrl.setEnabled(false);
        insertBookUrl.setEnabled(false);
    }

    private void enableAllEditText() {

        insertBookTitle.setEnabled(true);
        insertAuthor.setEnabled(true);
        insertImageUrl.setEnabled(true);
        insertBookUrl.setEnabled(true);
    }

    private void isSuccessful() {
        sweetAlertDialog = new SweetAlertDialog(BookInsertActivity.this,
                SweetAlertDialog.SUCCESS_TYPE);
        sweetAlertDialog.setTitleText("Successful")
                .hideConfirmButton()
                .show();
    }

    private void notSuccessful() {
        sweetAlertDialog = new SweetAlertDialog(BookInsertActivity.this,
                SweetAlertDialog.ERROR_TYPE);
        sweetAlertDialog.setTitleText("Failed")
                .hideConfirmButton()
                .show();
    }
}