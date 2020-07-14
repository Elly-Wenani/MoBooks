package com.example.mobooks;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONObject;

import com.github.barteksc.pdfviewer.PDFView;

public class OnlinePdfViewerActivity extends AppCompatActivity {

    private PDFView mPDFView;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private static final int PICTURE_RESULT = 42; //The answer to everything
    BooksMode mDeal;
    ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_pdfviewer);

        mPDFView = findViewById(R.id.onlinePdfViewer);
        TextView nothingToShow = findViewById(R.id.onlineNothingToShow);

        //String getItem = getIntent().getStringExtra("Books");

        mFirebaseDatabase = FirebaseUtil.mFirebaseDatabase;
        mDatabaseReference = FirebaseUtil.mDatabaseReference;

        mImageView = findViewById(R.id.image);

        final Intent intent = getIntent();
        BooksMode mDeal = (BooksMode) intent.getSerializableExtra("Books");
        if (mDeal == null) {
            mDeal = new BooksMode();
        }
        this.mDeal = mDeal;
        showImage(mDeal.getBkImageUrl());
    }

    private void showImage(String url) {
        if (url != null && url.isEmpty() == false) {
            int width = Resources.getSystem().getDisplayMetrics().widthPixels;
            Picasso.with(this)
                    .load(url)
                    .resize(width, width * 4 / 4)
                    .centerCrop()
                    .into(mImageView);
        }
    }

    //The back button has to be clicked twice before it exits
    private long onBackPressTime;
    private Toast backToast;

    @Override
    public void onBackPressed() {
        if (onBackPressTime + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
            backToast.cancel();
            return;
        } else {
            backToast = Toast.makeText(getApplicationContext(), R.string.press_again_to_exit, Toast.LENGTH_SHORT);
            backToast.show();
        }
        onBackPressTime = System.currentTimeMillis();
    }
}