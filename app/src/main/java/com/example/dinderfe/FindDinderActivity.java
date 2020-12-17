package com.example.dinderfe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class FindDinderActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    int eCuisine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_dinder);
        setTitle("Find Dinder");


        final String appid = getIntent().getExtras().getString("USER_ID");
        final String cookie = getIntent().getExtras().getString("COOKIE");

        Spinner cuisine = findViewById(R.id.spinnerCuisine);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.cuisines, R.layout.spinner_layout);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cuisine.setAdapter(adapter);
        cuisine.setOnItemSelectedListener(this);

        Button surprise = findViewById(R.id.buttonSurpriseMe);
        Button next = findViewById(R.id.buttonNext);


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ExperienceActivity.class);
                intent.putExtra("USER_ID", appid);
                intent.putExtra("COOKIE", cookie);
                intent.putExtra("ENUM_CUISINE",eCuisine);
                startActivity(intent);
            }
        });

        surprise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eCuisine = (int) (Math.random()*(11+1));

                Intent intent = new Intent(getApplicationContext(), ExperienceActivity.class);
                intent.putExtra("USER_ID", appid);
                intent.putExtra("COOKIE", cookie);
                intent.putExtra("ENUM_CUISINE",eCuisine);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        Log.d("demo", "onItemSelected: " + text);
        if (text.endsWith("Select Cuisine") || text.matches("------------")) {
            Toast.makeText(parent.getContext(), "Make a selection", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
            getECuisine(text);
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void getECuisine(String text) {
        switch (text) {
            case "Sushi":
                eCuisine = 0;
                break;
            case "Mexican":
                eCuisine = 1;
                break;
            case "Greek":
                eCuisine = 2;
                break;
            case "Japanese":
                eCuisine = 3;
                break;
            case "Thai":
                eCuisine = 4;
                break;
            case "Chinese":
                eCuisine = 5;
                break;
            case "Indian":
                eCuisine = 6;
                break;
            case "American":
                eCuisine = 7;
                break;
            case "Vietnamese":
                eCuisine = 8;
                break;
            case "Bakery":
                eCuisine = 9;
                break;
            case "Italian":
                eCuisine = 10;
                break;
        }
    }
}