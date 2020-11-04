package com.example.dinderfe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Profile");

        Button dinder = findViewById(R.id.buttonFindDinder);
        dinder.setBackgroundColor(getResources().getColor(R.color.red_theme));

        dinder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this,FindDinderActivity.class);
                startActivity(intent);
            }
        });

        
    }
}