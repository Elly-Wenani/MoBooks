package com.example.mobooks;

import android.os.Bundle;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnErrorListener;
import com.github.barteksc.pdfviewer.listener.OnPageErrorListener;
import com.github.barteksc.pdfviewer.listener.OnRenderListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class LocalPdfViewerActivity extends AppCompatActivity {

    private PDFView mPDFView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_viewer);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mPDFView = findViewById(R.id.pdfViewer);
        TextView nothingToShow = findViewById(R.id.nothingToShow);

        String getItem = getIntent().getStringExtra("pdfFileName");

        switch (getItem) {
            case "Bob Marley Biography":
                mPDFView.fromAsset("Bob_Marley_A_Biography.pdf")
                        .defaultPage(0)
                        .enableAnnotationRendering(true)
                        .scrollHandle(new DefaultScrollHandle(this))
                        .spacing(0)
                        .enableSwipe(true)
                        .swipeHorizontal(false)
                        .enableAntialiasing(true)
                        .onPageError(new OnPageErrorListener() {
                            @Override
                            public void onPageError(int page, Throwable t) {
                                Toast.makeText(getApplicationContext(), "Page error!", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .onError(new OnErrorListener() {
                            @Override
                            public void onError(Throwable t) {
                                Toast.makeText(LocalPdfViewerActivity.this, "Error occurred!", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .onRender(new OnRenderListener() {
                            @Override
                            public void onInitiallyRendered(int nbPages, float pageWidth, float pageHeight) {
                                mPDFView.fitToWidth();
                            }
                        })
                        .load();
                break;
            case "Wealth Without Theft":
                mPDFView.fromAsset("Wealth_Without_Theft_You_can_be_Rich_without_Stealing.pdf")
                        .defaultPage(0)
                        .enableAnnotationRendering(true)
                        .scrollHandle(new DefaultScrollHandle(this))
                        .spacing(0)
                        .enableSwipe(true)
                        .swipeHorizontal(false)
                        .enableAntialiasing(true)
                        .onPageError(new OnPageErrorListener() {
                            @Override
                            public void onPageError(int page, Throwable t) {
                                Toast.makeText(getApplicationContext(), "Page error!", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .onError(new OnErrorListener() {
                            @Override
                            public void onError(Throwable t) {
                                Toast.makeText(LocalPdfViewerActivity.this, "Error occurred!", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .onRender(new OnRenderListener() {
                            @Override
                            public void onInitiallyRendered(int nbPages, float pageWidth, float pageHeight) {
                                mPDFView.fitToWidth();
                            }
                        })
                        .load();
                break;
            case "The Richest Man In Babylon":
                mPDFView.fromAsset("The Richest Man In Babylon by George S Classon.pdf")
                        .defaultPage(0)
                        .enableAnnotationRendering(true)
                        .scrollHandle(new DefaultScrollHandle(this))
                        .spacing(0)
                        .enableSwipe(true)
                        .swipeHorizontal(false)
                        .enableAntialiasing(true)
                        .onPageError(new OnPageErrorListener() {
                            @Override
                            public void onPageError(int page, Throwable t) {
                                Toast.makeText(getApplicationContext(), "Page error!", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .onError(new OnErrorListener() {
                            @Override
                            public void onError(Throwable t) {
                                Toast.makeText(LocalPdfViewerActivity.this, "Error occurred!", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .onRender(new OnRenderListener() {
                            @Override
                            public void onInitiallyRendered(int nbPages, float pageWidth, float pageHeight) {
                                mPDFView.fitToWidth();
                            }
                        })
                        .load();
                break;
            case "How To Win Friends And Influence People":
                mPDFView.fromAsset("How To Win Friends And Influence People by Dale Carnegie.pdf")
                        .defaultPage(0)
                        .enableAnnotationRendering(true)
                        .scrollHandle(new DefaultScrollHandle(this))
                        .spacing(0)
                        .enableSwipe(true)
                        .swipeHorizontal(false)
                        .enableAntialiasing(true)
                        .onPageError(new OnPageErrorListener() {
                            @Override
                            public void onPageError(int page, Throwable t) {
                                Toast.makeText(getApplicationContext(), "Page error!", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .onError(new OnErrorListener() {
                            @Override
                            public void onError(Throwable t) {
                                Toast.makeText(LocalPdfViewerActivity.this, "Error occurred!", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .onRender(new OnRenderListener() {
                            @Override
                            public void onInitiallyRendered(int nbPages, float pageWidth, float pageHeight) {
                                mPDFView.fitToWidth();
                            }
                        })
                        .load();
                break;
            case "Act Like a Leader, Think Like a Leader":
                mPDFView.fromAsset("Act Like a Leader, Think Like a Leader.pdf")
                        .defaultPage(0)
                        .enableAnnotationRendering(true)
                        .scrollHandle(new DefaultScrollHandle(this))
                        .spacing(0)
                        .enableSwipe(true)
                        .swipeHorizontal(false)
                        .enableAntialiasing(true)
                        .onPageError(new OnPageErrorListener() {
                            @Override
                            public void onPageError(int page, Throwable t) {
                                Toast.makeText(getApplicationContext(), "Page error!", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .onError(new OnErrorListener() {
                            @Override
                            public void onError(Throwable t) {
                                Toast.makeText(LocalPdfViewerActivity.this, "Error occurred!", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .onRender(new OnRenderListener() {
                            @Override
                            public void onInitiallyRendered(int nbPages, float pageWidth, float pageHeight) {
                                mPDFView.fitToWidth();
                            }
                        })
                        .load();
                break;
            case "The Story of My Life":
                mPDFView.fromAsset("The Story of My Life, by Helen Keller.pdf")
                        .defaultPage(0)
                        .enableAnnotationRendering(true)
                        .scrollHandle(new DefaultScrollHandle(this))
                        .spacing(0)
                        .enableSwipe(true)
                        .swipeHorizontal(false)
                        .enableAntialiasing(true)
                        .onPageError(new OnPageErrorListener() {
                            @Override
                            public void onPageError(int page, Throwable t) {
                                Toast.makeText(getApplicationContext(), "Page error!", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .onError(new OnErrorListener() {
                            @Override
                            public void onError(Throwable t) {
                                Toast.makeText(LocalPdfViewerActivity.this, "Error occurred!", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .onRender(new OnRenderListener() {
                            @Override
                            public void onInitiallyRendered(int nbPages, float pageWidth, float pageHeight) {
                                mPDFView.fitToWidth();
                            }
                        })
                        .load();
                break;
            case "The Intelligent Investor":
                mPDFView.fromAsset("The Intelligent Investor by Benjamin Graham.pdf")
                        .defaultPage(0)
                        .enableAnnotationRendering(true)
                        .scrollHandle(new DefaultScrollHandle(this))
                        .spacing(0)
                        .enableSwipe(true)
                        .swipeHorizontal(false)
                        .enableAntialiasing(true)
                        .onPageError(new OnPageErrorListener() {
                            @Override
                            public void onPageError(int page, Throwable t) {
                                Toast.makeText(getApplicationContext(), "Page error!", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .onError(new OnErrorListener() {
                            @Override
                            public void onError(Throwable t) {
                                Toast.makeText(LocalPdfViewerActivity.this, "Error occurred!", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .onRender(new OnRenderListener() {
                            @Override
                            public void onInitiallyRendered(int nbPages, float pageWidth, float pageHeight) {
                                mPDFView.fitToWidth();
                            }
                        })
                        .load();
                break;
            case "Power Score":
                Toast.makeText(this, "Not available", Toast.LENGTH_SHORT).show();
                break;
            case "I kissed dating goodbye":
                mPDFView.fromAsset("I kissed dating goodbye.pdf")
                        .defaultPage(0)
                        .enableAnnotationRendering(true)
                        .scrollHandle(new DefaultScrollHandle(this))
                        .spacing(0)
                        .enableSwipe(true)
                        .swipeHorizontal(false)
                        .enableAntialiasing(true)
                        .onPageError(new OnPageErrorListener() {
                            @Override
                            public void onPageError(int page, Throwable t) {
                                Toast.makeText(getApplicationContext(), "Page error!", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .onError(new OnErrorListener() {
                            @Override
                            public void onError(Throwable t) {
                                Toast.makeText(LocalPdfViewerActivity.this, "Error occurred!", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .onRender(new OnRenderListener() {
                            @Override
                            public void onInitiallyRendered(int nbPages, float pageWidth, float pageHeight) {
                                mPDFView.fitToWidth();
                            }
                        })
                        .load();
                break;
            case "Money with a Mission":
                mPDFView.fromAsset("Money with a Mission - Thompson.pdf")
                        .defaultPage(0)
                        .enableAnnotationRendering(true)
                        .scrollHandle(new DefaultScrollHandle(this))
                        .spacing(0)
                        .enableSwipe(true)
                        .swipeHorizontal(false)
                        .enableAntialiasing(true)
                        .onPageError(new OnPageErrorListener() {
                            @Override
                            public void onPageError(int page, Throwable t) {
                                Toast.makeText(getApplicationContext(), "Page error!", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .onError(new OnErrorListener() {
                            @Override
                            public void onError(Throwable t) {
                                Toast.makeText(LocalPdfViewerActivity.this, "Error occurred!", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .onRender(new OnRenderListener() {
                            @Override
                            public void onInitiallyRendered(int nbPages, float pageWidth, float pageHeight) {
                                mPDFView.fitToWidth();
                            }
                        })
                        .load();
                break;
            default:
                mPDFView.setVisibility(View.INVISIBLE);
                nothingToShow.setText(R.string.not_available);
                nothingToShow.setVisibility(View.VISIBLE);
                break;
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