package com.example.mobooks;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import android.content.Intent;
import android.content.res.Resources;

import com.github.barteksc.pdfviewer.listener.OnErrorListener;
import com.github.barteksc.pdfviewer.listener.OnPageErrorListener;
import com.github.barteksc.pdfviewer.listener.OnRenderListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import com.github.barteksc.pdfviewer.PDFView;
import com.squareup.picasso.Target;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class OnlinePdfViewerActivity extends AppCompatActivity {

    private PDFView mPDFView;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    //private static final int PICTURE_RESULT = 42; //The answer to everything
    BooksMode onlineBooksSet;
    //ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_pdfviewer);

        mPDFView = findViewById(R.id.onlinePdfViewer);
        TextView nothingToShow = findViewById(R.id.onlineNothingToShow);

        //String getItem = getIntent().getStringExtra("Books");

        mFirebaseDatabase = FirebaseUtil.mFirebaseDatabase;
        mDatabaseReference = FirebaseUtil.mDatabaseReference;

        //Get passed intent from BsBooksAdapter
        final Intent intent = getIntent();
        BooksMode onlineBooksSet = (BooksMode) intent.getSerializableExtra("Books");
        if (onlineBooksSet == null) {
            onlineBooksSet = new BooksMode();
        }
        this.onlineBooksSet = onlineBooksSet;
        downloadPdf(onlineBooksSet.getBkFileUrl(), onlineBooksSet.getBkTitle());
    }


    //This method downloads pdf file from server db
    private void downloadPdf(final String FILE_LINK, final String fileName) {
        new AsyncTask<Void, Integer, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... params) {
                return downloadPdf();
            }

            @Nullable
            private Boolean downloadPdf() {
                try {
                    //If file is available it will open instead of downloading
                    File file = getFileStreamPath(fileName);
                    if (file.exists())
                        return true;

                    try {

                        /*----------------Function to exit if net is not connected-----------------*/
                        FileOutputStream fileOutputStream = openFileOutput(fileName, Context.MODE_PRIVATE);
                        URL u = new URL(FILE_LINK);
                        URLConnection conn = u.openConnection();
                        int contentLength = conn.getContentLength();
                        InputStream input = new BufferedInputStream(u.openStream());
                        byte data[] = new byte[contentLength];
                        long total = 0;
                        int count;

                        //Progress
                        while ((count = input.read(data)) != -1) {
                            total += count;
                            publishProgress((int) ((total * 100) / contentLength));
                            fileOutputStream.write(data, 0, count);
                        }

                        fileOutputStream.flush();
                        fileOutputStream.close();
                        input.close();
                        return true;

                    } catch (final Exception e) {
                        e.printStackTrace();
                        return false; //Swallow a 404
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return false;
            }

            @Override
            protected void onProgressUpdate(Integer... values) {
                super.onProgressUpdate(values);
                //seekBar.setProgress(values[0]);
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                super.onPostExecute(aBoolean);
                if (aBoolean) {
                    openPdf(fileName);
                } else {
                    Toast.makeText(getApplicationContext(), "Unable to download file", Toast.LENGTH_SHORT).show();
                }
            }
        }.execute();
    }

    //This method opens the downloaded pdf file from internal storage
    private void openPdf(String fileName) {
        try {
            final File file = getFileStreamPath(fileName);

            mPDFView.fromFile(file)
                    .defaultPage(0)
                    .enableAnnotationRendering(true)
                    .scrollHandle(new DefaultScrollHandle(this))
                    .spacing(2)
                    .enableSwipe(true)
                    .swipeHorizontal(false)
                    .enableAntialiasing(true)
                    .onPageError(new OnPageErrorListener() {
                        @Override
                        public void onPageError(int page, Throwable t) {
                            Toast.makeText(getApplicationContext(), "Page errors!", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .onError(new OnErrorListener() {
                        @Override
                        public void onError(Throwable t) {
                            Toast.makeText(OnlinePdfViewerActivity.this, "Error Occurred", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .onRender(new OnRenderListener() {
                        @Override
                        public void onInitiallyRendered(int nbPages, float pageWidth, float pageHeight) {
                            mPDFView.fitToWidth();
                        }
                    })
                    .load();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }






    //Display passed book from Firebase to OnlinePdfViewerActivity
//    private void showBook(String url) {
//        if (url != null && !url.isEmpty()) {
//            mPDFView.fromUri(Uri.parse(url))
//                    .defaultPage(0)
//                    .enableAnnotationRendering(true)
//                    .scrollHandle(new DefaultScrollHandle(this))
//                    .spacing(0)
//                    .enableSwipe(true)
//                    .swipeHorizontal(false)
//                    .enableAntialiasing(true)
//                    .onPageError(new OnPageErrorListener() {
//                        @Override
//                        public void onPageError(int page, Throwable t) {
//                            Toast.makeText(getApplicationContext(), "Page error!", Toast.LENGTH_SHORT).show();
//                        }
//                    })
//                    .onError(new OnErrorListener() {
//                        @Override
//                        public void onError(Throwable t) {
//                            Toast.makeText(OnlinePdfViewerActivity.this, "Error occurred!", Toast.LENGTH_SHORT).show();
//                        }
//                    })
//                    .onRender(new OnRenderListener() {
//                        @Override
//                        public void onInitiallyRendered(int nbPages, float pageWidth, float pageHeight) {
//                            mPDFView.fitToWidth();
//                        }
//                    })
//                    .load();
//        }
//    }

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