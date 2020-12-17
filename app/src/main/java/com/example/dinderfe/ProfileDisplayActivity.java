package com.example.dinderfe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProfileDisplayActivity extends AppCompatActivity {
    Button edit, dinder;
    ListView diets, interests;
    ArrayList<String> dietList, interestList;
    Profile display;
    ArrayAdapter<String> adapterD, adapterI;
    TextView name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_display);
        setTitle("Profile");

        edit = findViewById(R.id.buttonEdit);
        dinder = findViewById(R.id.buttonFindDinder);
        diets = findViewById(R.id.listDiet);
        interests = findViewById(R.id.listInterest);
         name = findViewById(R.id.profileName);

        final String appid = getIntent().getExtras().getString("USER_ID");
        final String cookie = getIntent().getExtras().getString("COOKIE");

        dietList = new ArrayList<>();
        interestList = new ArrayList<>();


        sendGetRequest(appid, cookie);


        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("USER_ID", appid);
                intent.putExtra("COOKIE", cookie);
                startActivity(intent);
            }
        });

        dinder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), FindDinderActivity.class);
                intent.putExtra("USER_ID", appid);
                intent.putExtra("COOKIE", cookie);
                startActivity(intent);
            }
        });
    }

    private void getDiets() {
        int i = display.getDiet();
        dietList.clear();

        if (i == 0) {
            dietList.add("+None");
        } else {
            if (i >= 16) {
                dietList.add("+Lactose Free");
                i -= 16;
            }
            if (i >= 8) {
                dietList.add("+Gluten Free");
                i -= 8;
            }
            if (i >= 4) {
                dietList.add("+Pescetarian");
                i -= 4;
            }
            if (i >= 2) {
                dietList.add("+Vegetarian");
                i -= 2;
            }
            if (i == 1) {
                dietList.add("+Vegan");
            }
        }
        adapterD = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,
                android.R.id.text1, dietList);
        diets.setAdapter(adapterD);
    }

    private void getInterest() {
        int i = display.getInterest();
        interestList.clear();

        if (i == 0) {
            interestList.add("+None");
        } else {

            if (i >= 8) {
                interestList.add("+Outdoors");
                i -= 8;
            }
            if (i >= 4) {
                interestList.add("+Movies");
                i -= 4;
            }
            if (i >= 2) {
                interestList.add("+Music");
                i -= 2;
            }
            if (i == 1) {
                interestList.add("+Sports");
            }
        }
        adapterI = new ArrayAdapter<>(ProfileDisplayActivity.this, android.R.layout.simple_list_item_1,
                android.R.id.text1, interestList);
        interests.setAdapter(adapterI);
    }

    private void sendGetRequest(String id, String cookies) {

        RequestQueue queue = Volley.newRequestQueue(ProfileDisplayActivity.this);
        StringBuffer url = new StringBuffer("https://drewcav.com/api/Profile/Detail/");
        url.append(id);

        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, url.toString(), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    display = new Profile(response);
                    Log.d("onCreate", display.toString());
                    name.setText(display.getFirst()+" "+display.getLast());
                    getDiets();
                    getInterest();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Cookie", cookies);
                return headers;
            }
        };

        queue.add(objectRequest);
    }
}