package com.example.dinderfe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LookingActivity extends AppCompatActivity {

    TextView found;
    Button map;
    int cuisine;
    String eCuisine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_looking);

        found = findViewById(R.id.textFound);
        map = findViewById(R.id.buttonMap);
        final String appid = getIntent().getExtras().getString("USER_ID");
        final String cookie = getIntent().getExtras().getString("COOKIE");
        sendGetRequest(appid,cookie);

        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), FoodMapsActivity.class);
                intent.putExtra("USER_ID",appid);
                intent.putExtra("COOKIE",cookie);
                intent.putExtra("CUISINE",eCuisine);

                startActivity(intent);
            }
        });


    }
    private void sendGetRequest(String id, String cookies) {

        RequestQueue queue = Volley.newRequestQueue(LookingActivity.this);
        StringBuffer url = new StringBuffer("https://drewcav.com/api/Experience/Detail/");
        url.append(id);

        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, url.toString(), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Log.d("onResponse",response.toString());
                sendGetRequestMatches(id, cookies);

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

    private void sendGetRequestMatches(String id, String cookies) {

        RequestQueue queue = Volley.newRequestQueue(LookingActivity.this);
        StringBuffer url = new StringBuffer("https://drewcav.com/api/Match/Find/");
        url.append(id);

        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, url.toString(), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Log.d("onResponse",response.toString());
                try {
                    found.setText(response.getString("message"));
                    sendGetRequestDetail(response.getInt("id"),cookies);
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

    private void sendGetRequestDetail(int id, String cookies) {

        RequestQueue queue = Volley.newRequestQueue(LookingActivity.this);
        StringBuffer url = new StringBuffer("https://drewcav.com/api/Match/Detail/");
        url.append(id);

        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, url.toString(), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    cuisine = response.getInt("cuisineType");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Log.d("onResponse",response.toString());

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