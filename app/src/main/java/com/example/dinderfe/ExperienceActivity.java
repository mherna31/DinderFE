package com.example.dinderfe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class ExperienceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_experience);

        final String appid = getIntent().getExtras().getString("USER_ID");
        final String cookie = getIntent().getExtras().getString("COOKIE");
        int eCuisine = getIntent().getExtras().getInt("ENUM_CUISINE");


    }
}