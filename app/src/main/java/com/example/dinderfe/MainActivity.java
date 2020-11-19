package com.example.dinderfe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<String> diet;
    ArrayList<String> interest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Create Profile");

        Button dinder = findViewById(R.id.buttonSave);
        CheckBox vegetarian = findViewById(R.id.boxVegetarian);
        CheckBox vegan = findViewById(R.id.boxVegan);
        CheckBox kosher = findViewById(R.id.boxKosher);
        CheckBox halal = findViewById(R.id.boxHalal);
        CheckBox glutenFree = findViewById(R.id.boxGlutten);
        CheckBox allergies = findViewById(R.id.boxAllergies);

        CheckBox sports = findViewById(R.id.boxSport);
        CheckBox movies = findViewById(R.id.boxMovies);
        CheckBox outdoor = findViewById(R.id.boxOutdoors);
        CheckBox music = findViewById(R.id.boxMusic);
        CheckBox books = findViewById(R.id.boxBooks);
        CheckBox games = findViewById(R.id.boxGames);


        dinder.setBackgroundColor(getResources().getColor(R.color.red_theme));

        dinder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                diet.clear();
                interest.clear();

                if(vegetarian.isChecked()){
                    diet.add("+Vegetarian");
                }
                if(vegan.isChecked()){
                    diet.add("+Vegan");
                }
                if(kosher.isChecked()){
                    diet.add("+Kosher");
                }
                if(halal.isChecked()){
                    diet.add("+Halal");
                }
                if(glutenFree.isChecked()){
                    diet.add("+Gluten-Free");
                }
                if(allergies.isChecked()){
                    diet.add("+Allergies");
                }

                if(sports.isChecked()){
                    interest.add("+Sports");
                }
                if(movies.isChecked()){
                    interest.add("+Movies");
                }
                if(outdoor.isChecked()){
                    interest.add("+Outdoors");
                }
                if(music.isChecked()){
                    interest.add("+Music");
                }
                if(books.isChecked()){
                    interest.add("+Books");
                }
                if(games.isChecked()){
                    interest.add("+Games");
                }

                Intent intent = new Intent(MainActivity.this,ProfileDisplayActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("DIET", diet);
                bundle.putSerializable("INTEREST", interest);
                intent.putExtras(bundle);

                startActivity(intent);
            }
        });

        
    }
}