package com.example.dinderfe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ProfileDisplayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_display);
        setTitle("Profile");

        Button edit = findViewById(R.id.buttonEdit);
        Button dinder = findViewById(R.id.buttonFindDinder);
        ListView diets = findViewById(R.id.listDiet);
        ListView interests = findViewById(R.id.listInterest);


        Bundle bundle = getIntent().getExtras();
        ArrayList diet = (ArrayList) bundle.getSerializable("DIET");
        ArrayList interest = (ArrayList) bundle.getSerializable("INTEREST");

        ArrayAdapter adapter1 = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, diet);
        ArrayAdapter adapter2 = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, interest);

        edit.setBackgroundColor(getResources().getColor(R.color.red_theme));
        dinder.setBackgroundColor(getResources().getColor(R.color.red_theme));

        diets.setAdapter(adapter1);
        interests.setAdapter(adapter2);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });

        dinder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),FindDinderActivity.class);
                startActivity(intent);
            }
        });



    }
}