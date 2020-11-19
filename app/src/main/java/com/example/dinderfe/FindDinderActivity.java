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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_dinder);
        setTitle("Find Dinder");

        Spinner cuisine = findViewById(R.id.spinnerCuisine);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.cuisines, R.layout.spinner_layout);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cuisine.setAdapter(adapter);
        cuisine.setOnItemSelectedListener(this);

        Button surprise = findViewById(R.id.buttonSurpriseMe);
        Button next = findViewById(R.id.buttonNext);
        next.setBackgroundColor(getResources().getColor(R.color.red_theme));
        surprise.setBackgroundColor(getResources().getColor(R.color.red_theme));

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),FoodMapsActivity.class);
                startActivity(intent);
            }
        });

        surprise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),FoodMapsActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        Log.d("demo", "onItemSelected: "+text);
        if (text.endsWith("Select Cuisine") || text.matches( "------------")){
            Toast.makeText(parent.getContext(),"Make a selection",Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}