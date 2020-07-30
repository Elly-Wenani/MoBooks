package com.example.mobooks;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class InfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        TextView tvInfo = findViewById(R.id.tvInfo);
        TextView tvTerms = findViewById(R.id.tvTerms);
        TextView tvDeveloper = findViewById(R.id.tvDeveloper);
        tvInfo.setText(R.string.text_about);
        tvTerms.setText(R.string.text_terms);
        tvDeveloper.setText(R.string.text_developer);
    }
}