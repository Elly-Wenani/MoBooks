package com.example.mobooks;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnErrorListener;
import com.github.barteksc.pdfviewer.listener.OnPageErrorListener;
import com.github.barteksc.pdfviewer.listener.OnRenderListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.google.android.material.navigation.NavigationView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity implements BooksAdapter.OnBookClickListener {

    private RecyclerView recyclerView;
    private BooksAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private PDFView mPDFView;
    private ArrayList<DataManager> bookTile;

    private AppBarConfiguration mAppBarConfiguration;
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Most Read Books");

        //Hooks
        mPDFView = findViewById(R.id.pdfViewer);
        recyclerView = findViewById(R.id.rvBooks);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setCheckedItem(R.id.nav_home);

        //Populate recycler view
        bookTile = new ArrayList<>();

        bookTile.add(new DataManager("How To Win Friends And Influence People", "Dale Carnegie"));
        bookTile.add(new DataManager("Wealth Without Theft", "Kolawole Oyeyemi"));
        bookTile.add(new DataManager("Act Like a Leader, Think Like a Leader", "Herminia Ibarra"));
        bookTile.add(new DataManager("The Richest Man In Babylon", "George S Classon"));
        bookTile.add(new DataManager("The Story of My Life", "Helen Keller"));
        bookTile.add(new DataManager("The Intelligent Investor", "Benjamin Graham"));
        bookTile.add(new DataManager("Power Score", "Alan Foster"));
        bookTile.add(new DataManager("I kissed dating goodbye", "Joshua Harris"));
        bookTile.add(new DataManager("Money with a Mission", "Dr. Leroy Thompson Sr"));
        bookTile.add(new DataManager("Bob Marley Biography", "Iuliana Cosmina"));

        // Linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // specifying an adapter
        mAdapter = new BooksAdapter(this, bookTile, this);
        recyclerView.setAdapter(mAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //Click listener
    @Override
    public void onBookClick(View v, int position) {

        final DataManager title = bookTile.get(position);
        String mTitlle = title.getBookTitle();

        Intent intent = new Intent(v.getContext(), PdfViewerActivity.class);
        intent.putExtra("pdfFileName", mTitlle);
        v.getContext().startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}