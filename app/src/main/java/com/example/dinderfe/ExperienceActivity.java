package com.example.dinderfe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import java.util.HashMap;
import java.util.Map;

public class ExperienceActivity extends AppCompatActivity {

    Button back, findMatch;
    RadioGroup gender, size;
    RadioButton small, large, none, men, women, any;
    int eGender, lat, lon, eSize, eCuisine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_experience);

        back = findViewById(R.id.buttonBack2);
        findMatch = findViewById(R.id.buttonFindMatches);

        gender = findViewById(R.id.radioGroupGender);
        size = findViewById(R.id.radioGroupSize);

        small = findViewById(R.id.radioSmall);
        large = findViewById(R.id.radioLarge);
        none = findViewById(R.id.radioNoP);
        men = findViewById(R.id.radioMen);
        women = findViewById(R.id.radioWomen);
        any = findViewById(R.id.radioAny);

        final String appid = getIntent().getExtras().getString("USER_ID");
        final String cookie = getIntent().getExtras().getString("COOKIE");
         eCuisine = getIntent().getExtras().getInt("ENUM_CUISINE");

        gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (men.isChecked()) {
                    eGender = 0;
                } else if (women.isChecked()) {
                    eGender = 1;
                } else {
                    eGender = 2;
                }
            }
        });

        size.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (small.isChecked()) {
                    eSize = 0;
                } else if (large.isChecked()) {
                    eSize = 1;
                } else {
                    eSize = 2;
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), FindDinderActivity.class);
                intent.putExtra("USER_ID",appid);
                intent.putExtra("COOKIE",cookie);
                startActivity(intent);
            }
        });

        findMatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    postRequest(appid,cookie,eCuisine);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });


    }
    private void postRequest( String id, String cookies, int cuisine) throws JSONException {
        RequestQueue requestQueue = Volley.newRequestQueue(ExperienceActivity.this);
        StringBuffer url = new StringBuffer("https://drewcav.com/api/Experience/Create/");
        url.append(id);

        JSONObject jsonBody = new JSONObject();
        jsonBody.put("cuisineType", cuisine);
        jsonBody.put("groupSize", eSize);
        jsonBody.put("genderPreference", eGender);
        jsonBody.put("Longitude", 35);
        jsonBody.put("Latitude", -80 );

        // Log.d("onPostRequest", "url "+ url.toString());

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url.toString(), jsonBody, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("onResponse", "Success");
                Log.d("Login", "Success" + response.toString());
                Intent intent = new Intent(ExperienceActivity.this, LookingActivity.class);
                intent.putExtra("USER_ID",id);
                intent.putExtra("COOKIE",cookies);
                startActivity(intent);

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
                }else if(error instanceof AuthFailureError){
                    Log.d("onError","Auth Error");
                }else if(error instanceof NetworkError){
                    Log.d("onError","Network Error");
                }else if(error instanceof ParseError){
                    if(networkResponse != null){
                        try {
                            String jsonerror = new String(networkResponse.data,
                                    HttpHeaderParser.parseCharset(networkResponse.headers, "utf-8"));

                            JSONObject message = new JSONObject(jsonerror);
                            Log.d("on error", " parseError" + networkResponse.statusCode);
                            Toast.makeText(getApplicationContext(), message.getString("message"), Toast.LENGTH_LONG).show();

                        } catch (JSONException | UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }
                    assert networkResponse != null;
                    Log.d("on error", " parseError" + networkResponse.statusCode);

                    Log.d("onError","Parse Error");
                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                headers.put("Cookie", cookies);
                return headers;
            }
        };
        requestQueue.add(request);
        //Log.d("afterRequest", "true ");
    }

}