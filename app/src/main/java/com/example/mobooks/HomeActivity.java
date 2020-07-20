package com.example.mobooks;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobooks.Adapters.LocalBooksAdapter;
import com.example.mobooks.Books.BiographyActivity;
import com.example.mobooks.Books.BusinessActivity;
import com.example.mobooks.Books.LeadershipActivity;
import com.example.mobooks.DataModels.LocalBooksMode;
import com.firebase.ui.auth.AuthUI;
import com.github.barteksc.pdfviewer.PDFView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity implements LocalBooksAdapter.OnBookClickListener, NavigationView.OnNavigationItemSelectedListener {

    private RecyclerView recyclerView;
    private LocalBooksAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private PDFView mPDFView;
    private ArrayList<LocalBooksMode> bookTile;

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
        recyclerView = findViewById(R.id.rvHomeBooks);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_profile, R.id.nav_logout)
                .setDrawerLayout(drawer)
                .build();

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);

        //Show items if admin and hide if not admin
        Menu menu = navigationView.getMenu();
        if (FirebaseUtil.isAdmin) {
            menu.findItem(R.id.nav_insert).setVisible(true);
        } else {
            menu.findItem(R.id.nav_insert).setVisible(false);
        }

        //Populate recycler view
        bookTile = new ArrayList<>();

        bookTile.add(new LocalBooksMode("How To Win Friends And Influence People", "Dale Carnegie"));
        bookTile.add(new LocalBooksMode("Wealth Without Theft", "Kolawole Oyeyemi"));
        bookTile.add(new LocalBooksMode("Act Like a Leader, Think Like a Leader", "Herminia Ibarra"));
        bookTile.add(new LocalBooksMode("The Richest Man In Babylon", "George S Classon"));
        bookTile.add(new LocalBooksMode("The Story of My Life", "Helen Keller"));
        bookTile.add(new LocalBooksMode("The Intelligent Investor", "Benjamin Graham"));
        bookTile.add(new LocalBooksMode("Power Score", "Alan Foster"));
        bookTile.add(new LocalBooksMode("I kissed dating goodbye", "Joshua Harris"));
        bookTile.add(new LocalBooksMode("Money with a Mission", "Dr. Leroy Thompson Sr"));
        bookTile.add(new LocalBooksMode("Bob Marley Biography", "Iuliana Cosmina"));

        // Linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // specifying an adapter
        mAdapter = new LocalBooksAdapter(this, bookTile, this);
        recyclerView.setAdapter(mAdapter);
    }

    //Click listener
    @Override
    public void onBookClick(View v, int position) {

        final LocalBooksMode title = bookTile.get(position);
        String mTitle = title.getBookTitle();

        Intent intent = new Intent(v.getContext(), LocalPdfViewerActivity.class);
        intent.putExtra("pdfFileName", mTitle);
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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.nav_home:
                break;

            case R.id.nav_business:
                Intent bus = new Intent(this, BusinessActivity.class);
                startActivity(bus);
                finish();
                break;

            case R.id.nav_biography:
                Intent bio = new Intent(this, BiographyActivity.class);
                startActivity(bio);
                finish();
                break;

            case R.id.nav_leadership:
                Intent lead = new Intent(this, LeadershipActivity.class);
                startActivity(lead);
                finish();
                break;

            case R.id.nav_logout:
                AuthUI.getInstance()
                        .signOut(this)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            public void onComplete(@NonNull Task<Void> task) {
                                Log.d("Logout", "User logged out");
                                FirebaseUtil.attachListener();
                            }
                        });
                FirebaseUtil.detachListener();
                //return true;
                break;

            case R.id.nav_insert:
                Intent insert = new Intent(this, BookInsertActivity.class);
                startActivity(insert);
                break;

            case R.id.nav_email:
                //Method to send email
                mailTo(new String[]{getString(R.string.mobooks_email_address)}, getString(R.string.mobooks_subject));
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return false;
    }

    //This method directs the user to their default mailing app to send
    // the email
    private void mailTo(String[] emailAddresses, String subject) {
        Intent sendEmail = new Intent(Intent.ACTION_SENDTO);
        sendEmail.setData(Uri.parse("mailto:"));
        sendEmail.putExtra(Intent.EXTRA_EMAIL, emailAddresses);
        sendEmail.putExtra(Intent.EXTRA_SUBJECT, subject);

        if (sendEmail.resolveActivity(getPackageManager()) != null){
            startActivity(sendEmail);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        FirebaseUtil.detachListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        FirebaseUtil.openFbReferenceHome("homeBooks", this);
        FirebaseUtil.attachListener();
    }

    public void showMenu() {
        invalidateOptionsMenu();
    }
}