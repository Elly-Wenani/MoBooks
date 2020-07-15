package com.example.mobooks;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import android.content.Intent;

import com.github.barteksc.pdfviewer.listener.OnErrorListener;
import com.github.barteksc.pdfviewer.listener.OnPageErrorListener;
import com.github.barteksc.pdfviewer.listener.OnRenderListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.github.barteksc.pdfviewer.PDFView;

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
    BooksMode onlineBooksSet;

    private SeekBar mSeekBar;
    private TextView mLoading;
    private TextView tvSeekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_pdfviewer);

        mPDFView = findViewById(R.id.onlinePdfViewer);
        mLoading = findViewById(R.id.tvLoading);
        tvSeekBar = findViewById(R.id.tvSeekBar);

        mFirebaseDatabase = FirebaseUtil.mFirebaseDatabase;
        mDatabaseReference = FirebaseUtil.mDatabaseReference;

        //Get passed intent from BsBooksAdapter
        final Intent intent = getIntent();
        BooksMode onlineBooksSet = (BooksMode) intent.getSerializableExtra("Books");
        if (onlineBooksSet == null) {
            onlineBooksSet = new BooksMode();
        }
        this.onlineBooksSet = onlineBooksSet;
        initSeekBar();
        downloadPdfFile(onlineBooksSet.getBkFileUrl(), onlineBooksSet.getBkTitle());
    }

    //This method downloads pdf file from server db
    private void downloadPdfFile(final String FILE_LINK, final String fileName) {
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
                mSeekBar.setProgress(values[0]);
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                super.onPostExecute(aBoolean);
                if (aBoolean) {
                    openPdfFile(fileName);
                } else {
                    Toast.makeText(getApplicationContext(), "Unable to download file", Toast.LENGTH_SHORT).show();
                }
            }
        }.execute();
    }

    //This method opens the downloaded pdf file from internal storage
    private void openPdfFile(String fileName) {
        try {
            final File file = getFileStreamPath(fileName);
            Log.e("File ", "file: " + file.getAbsolutePath());
            mSeekBar.setVisibility(View.GONE);
            mLoading.setVisibility(View.GONE);
            tvSeekBar.setVisibility(View.GONE);
            mPDFView.setVisibility(View.VISIBLE);
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

                            alertMessage();
                            //This condition deletes any file that did not finish loading
                            if (file.exists()) {
                                file.delete();
                            }
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

    //This method alerts the user to re-load the file again if it failed loading
    private void alertMessage() {
        new AlertDialog.Builder(this)
                .setMessage("The previous download was interrupted. Kindly " +
                        "press cancel and open this book again to re-load!\n" +
                        "Note: Do not interrupt loading process")
                .setPositiveButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        OnlinePdfViewerActivity.this.finish();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void initSeekBar() {
        mSeekBar = findViewById(R.id.seekBar);
        mSeekBar.getProgressDrawable().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_IN);
        mSeekBar.getThumb().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int val = (progress * (seekBar.getWidth()) - 4
                        * seekBar.getThumbOffset()) / seekBar.getMax();
                tvSeekBar.setText("" + progress);
                tvSeekBar.setX(seekBar.getX() + val + seekBar.getThumbOffset() / 2);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
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

//A9B7C6