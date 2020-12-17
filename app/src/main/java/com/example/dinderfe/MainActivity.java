package com.example.dinderfe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    int gender, interest, diet;
    CheckBox vegetarian, vegan, pescetarian, lactose, glutenFree;
    CheckBox sports, movies, outdoor, music;
    EditText first, last;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Create Profile");

        Button save = findViewById(R.id.buttonSave);
        vegetarian = findViewById(R.id.boxVegetarian);
        vegan = findViewById(R.id.boxVegan);
        pescetarian = findViewById(R.id.boxPescetarian);
        lactose = findViewById(R.id.boxLactose);
        glutenFree = findViewById(R.id.boxGlutten);

        sports = findViewById(R.id.boxSport);
        movies = findViewById(R.id.boxMovies);
        outdoor = findViewById(R.id.boxOutdoors);
        music = findViewById(R.id.boxMusic);

        first = findViewById(R.id.inFName);
        last = findViewById(R.id.inLName);

        RadioGroup genders = findViewById(R.id.radioGender);
        RadioButton male = findViewById(R.id.radioMale);
        RadioButton female = findViewById(R.id.radioFemale);
        RadioButton other = findViewById(R.id.radioOther);

        final String appid = getIntent().getExtras().getString("USER_ID");


        genders.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (male.isChecked()) {
                    gender = 0;
                } else if (female.isChecked()) {
                    gender = 1;
                } else {
                    gender = 2;
                }
                // Log.d("Radio", "Gender: "+ gender);
            }
        });


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getDiet();
                getInterest();

                if (first.getText().toString().trim().equalsIgnoreCase("")) {

                    first.setError("Cannot be empty");
                } else if (last.getText().toString().trim().equalsIgnoreCase("")) {

                    last.setError("Cannot be empty");
                } else {
                    try {
                        postRequest(first.getText().toString(), last.getText().toString(), appid);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                //Log.d("save", "Diet: " + gender);
                /*Intent intent = new Intent(MainActivity.this, ProfileDisplayActivity.class);
                startActivity(intent);*/
            }
        });
        /**/

    }

    //Post Request
   /* {
        "firstname": "Alice",
            "lastname": "Test",
            "gender": 1,
            "avatarUrl": null,
            "dietaryRestrictions": 18,
            "interests": 15
    }*/
    private void postRequest(String cfirst, String cLast, String id) throws JSONException {
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        StringBuffer url = new StringBuffer("https://drewcav.com/api/Profile/Update/");
        url.append(id);

        JSONObject jsonBody = new JSONObject();
        jsonBody.put("Firstname", cfirst);
        jsonBody.put("Lastname", cLast);
        jsonBody.put("Gender", gender);
        jsonBody.put("AvatarUrl", null);
        jsonBody.put("DietaryRestrictions", diet);
        jsonBody.put("Interests", interest);

       // Log.d("onPostRequest", "url "+ url.toString());

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url.toString(), jsonBody, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("onResponse", "Success");
                Log.d("Login", "Success" + response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                NetworkResponse networkResponse = error.networkResponse;
                 if (error instanceof ServerError && networkResponse != null) {
                    try {
                        String jsonerror = new String(networkResponse.data,
                                HttpHeaderParser.parseCharset(networkResponse.headers, "utf-8"));

                        JSONObject message = new JSONObject(jsonerror);
                        Log.d("on error", " " + message);
                        Toast.makeText(getApplicationContext(), message.getString("message"), Toast.LENGTH_LONG).show();

                    } catch (JSONException | UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }else if(error instanceof  AuthFailureError){
                     Log.d("onError","Auth Error");
                 }else if(error instanceof NetworkError){
                     Log.d("onError","Network Error");
                 }else if(error instanceof ParseError){
                     Log.d("onError","Parse Error");
                 }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");

                return headers;
            }
        };
        requestQueue.add(request);
        Log.d("afterRequest", "true ");
    }

    //adding to get enum 0,1,3,4, etc
    private void getDiet() {
        diet = 0;

        if (vegan.isChecked()) {
            diet += 1;
        }
        if (vegetarian.isChecked()) {
            diet += 2;
        }
        if (pescetarian.isChecked()) {
            diet += 4;
        }
        if (glutenFree.isChecked()) {
            diet += 8;
        }
        if (lactose.isChecked()) {
            diet += 16;
        }
    }

    //adding to get enum 0,1,3,4, etc
    private void getInterest() {
        interest = 0;

        if (sports.isChecked()) {
            interest += 1;
        }
        if (music.isChecked()) {
            interest += 2;
        }
        if (movies.isChecked()) {
            interest += 4;
        }
        if (outdoor.isChecked()) {
            interest += 8;
        }

    }
}