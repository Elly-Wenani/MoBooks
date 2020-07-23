package com.example.mobooks.Books;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.mobooks.Adapters.OnlineBooksAdapter;
import com.example.mobooks.BookInsertActivity;
import com.example.mobooks.FirebaseUtil;
import com.example.mobooks.HomeActivity;
import com.example.mobooks.InfoActivity;
import com.example.mobooks.R;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;

public class NovelsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    private AppBarConfiguration mAppBarConfiguration;
    public int admin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novels);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Novels");

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
        navigationView.setCheckedItem(R.id.nav_novels);

        //Show items if admin and hide if not admin
        Menu menu = navigationView.getMenu();
        menu.findItem(R.id.nav_insert).setVisible(false);

//        if (FirebaseUtil.isAdmin) {
//            menu.findItem(R.id.nav_insert).setVisible(true);
//        } else {
//            menu.findItem(R.id.nav_insert).setVisible(false);
//        }

        if (FirebaseUtil.isAdmin) {
            admin = 1;
        } else {
            admin = 0;
        }
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.insert_menu, menu);

        if (admin == 1) {
            menu.findItem(R.id.action_addBook).setVisible(true);
        } else if(admin == 0) {
            menu.findItem(R.id.action_addBook).setVisible(false);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_addBook) {
            Intent insert = new Intent(this, BookInsertActivity.class);
            startActivity(insert);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.nav_novels:
                break;

            case R.id.nav_home:
                Intent home = new Intent(this, HomeActivity.class);
                startActivity(home);
                finish();
                break;

            case R.id.nav_biography:
                Intent bio = new Intent(this, BiographyActivity.class);
                startActivity(bio);
                finish();
                break;

            case R.id.nav_business:
                Intent bus = new Intent(this, BusinessActivity.class);
                startActivity(bus);
                finish();
                break;

            case R.id.nav_compTech:
                Intent tech = new Intent(this, TechnologyActivity.class);
                startActivity(tech);
                finish();
                break;

            case R.id.nav_leadership:
                Intent lead = new Intent(this, LeadershipActivity.class);
                startActivity(lead);
                finish();
                break;

            case R.id.nav_history:
                Intent hist = new Intent(this, HistoryActivity.class);
                startActivity(hist);
                finish();
                break;

            case R.id.nav_insert:
                Intent insert = new Intent(this, BookInsertActivity.class);
                startActivity(insert);
                break;

            case R.id.nav_share:
                shareApp(getString(R.string.share_message));
                break;

            case R.id.nav_email:
                mailTo(new String[]{getString(R.string.mobooks_email_address)}, getString(R.string.mobooks_subject));
                break;

            case R.id.nav_logout:
                logOut();
                break;

            case R.id.nav_info:
                Intent about = new Intent(this, InfoActivity.class);
                startActivity(about);
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return false;
    }

    private void logOut() {
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.d("Logout", "User logged out");
                        FirebaseUtil.attachListener();
                    }
                });
        FirebaseUtil.detachListener();
    }

    private void shareApp(String textMessage) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, textMessage);
        shareIntent.setType("text/plain");
        if (shareIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(shareIntent);
        }
    }

    //This method directs the user to their default mailing app to send
    // the email
    private void mailTo(String[] emailAddresses, String subject) {
        Intent sendEmail = new Intent(Intent.ACTION_SENDTO);
        sendEmail.setData(Uri.parse("mailto:"));
        sendEmail.putExtra(Intent.EXTRA_EMAIL, emailAddresses);
        sendEmail.putExtra(Intent.EXTRA_SUBJECT, subject);

        if (sendEmail.resolveActivity(getPackageManager()) != null) {
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

        FirebaseUtil.openFbReferenceNov("novelsBooks", this);
        FirebaseUtil.attachListener();
        RecyclerView rvNovelsBooks = findViewById(R.id.rvNovelsBooks);
        final OnlineBooksAdapter adapter = new OnlineBooksAdapter();
        rvNovelsBooks.setAdapter(adapter);
        LinearLayoutManager booksLayoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvNovelsBooks.setLayoutManager(booksLayoutManager);
    }
}