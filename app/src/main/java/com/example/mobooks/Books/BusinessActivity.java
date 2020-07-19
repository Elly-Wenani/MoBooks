package com.example.mobooks.Books;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobooks.Adapters.BusinessAdapter;
import com.example.mobooks.BookInsertActivity;
import com.example.mobooks.Books.BiographyActivity;
import com.example.mobooks.FirebaseUtil;
import com.example.mobooks.HomeActivity;
import com.example.mobooks.R;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;

public class BusinessActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    private AppBarConfiguration mAppBarConfiguration;

//    ProgressDialog mProgressDialog;
//    CountDownTimer mCountDownTimer;
//    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Business Books");

//        //Fore progress dialog timing
//        mProgressDialog = new ProgressDialog(this);
//        mProgressDialog.setMessage("Loading...");
//        mProgressDialog.setCancelable(false);
//        mProgressDialog.setProgress(i);
//        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//        mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.GRAY));

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
        navigationView.setCheckedItem(R.id.nav_business);

        //Show items if admin and hide if not admin
        Menu menu = navigationView.getMenu();
        if (FirebaseUtil.isAdmin) {
            menu.findItem(R.id.nav_insert).setVisible(true);
        } else {
            menu.findItem(R.id.nav_insert).setVisible(false);
        }

//        //Progress dialog timing
//        mProgressDialog.show();
//        mCountDownTimer = new CountDownTimer(2000, 1000) {
//            @Override
//            public void onTick(long millisUntilFinished) {
//                mProgressDialog.setMessage("Loading...");
//            }
//
//            @Override
//            public void onFinish() {
//                mProgressDialog.dismiss();
//            }
//        }.start();
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
            case R.id.nav_business:
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

            case R.id.nav_insert:
                Intent insert = new Intent(this, BookInsertActivity.class);
                startActivity(insert);

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

        }

        drawer.closeDrawer(GravityCompat.START);
        return false;
    }

    @Override
    protected void onPause() {
        super.onPause();
        FirebaseUtil.detachListener();
    }

    @Override
    protected void onResume() {
        super.onResume();

        FirebaseUtil.openFbReferenceBus("businessBooks", this);
        FirebaseUtil.attachListener();
        RecyclerView rvBusinessBooks = findViewById(R.id.rvBusinessBooks);
        final BusinessAdapter adapter = new BusinessAdapter();
        rvBusinessBooks.setAdapter(adapter);
        LinearLayoutManager booksLayoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvBusinessBooks.setLayoutManager(booksLayoutManager);
        //FirebaseUtil.attachListener();
    }
}