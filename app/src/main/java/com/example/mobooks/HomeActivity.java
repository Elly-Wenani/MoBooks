package com.example.mobooks;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity{

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    //BooksAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.rvBooks);
        ArrayList<DataManager> bookTile = new ArrayList<>();
        bookTile.add(new DataManager("Java for Absolute Beginners", "Iuliana Cosmina"));
        bookTile.add(new DataManager("Android Cookbook", "Ian F. Darwin"));
        bookTile.add(new DataManager("Linux Bible", "Christopher Negus"));
        bookTile.add(new DataManager("The Richest Man In Babylon", "George S Classon"));
        bookTile.add(new DataManager("The Story of My Life", "Helen Keller"));
        bookTile.add(new DataManager("The Intelligent Investor", "Benjamin Graham"));
        bookTile.add(new DataManager("How To Win Friends And Influence People", "Dale Carnegie"));
        bookTile.add(new DataManager("Bob Marley A Biography", "Greenwood Biographies"));
        bookTile.add(new DataManager("Act Like a Leader, Think Like a Leader", "Herminia Ibarra"));
        bookTile.add(new DataManager("Power Score", "Alan Foster"));
        bookTile.add(new DataManager("I kissed dating goodbye", "Joshua Harris"));
        bookTile.add(new DataManager("Money with a Mission", "Dr. Leroy Thompson Sr"));

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)
        mAdapter = new BooksAdapter(this, bookTile);
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
}