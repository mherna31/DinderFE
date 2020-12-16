package com.example.dinderfe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class LogInActivity extends AppCompatActivity {

    Button login, signup;
    EditText email, password;
    boolean success;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        login = findViewById(R.id.buttonLogin);
        signup = findViewById(R.id.buttonSignUp);
        email = findViewById(R.id.inEmail);
        password = findViewById(R.id.inPassword);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogInActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendGetRequest(email.getText().toString(), password.getText().toString());

            }
        });


    }

    private void sendPostRequest(String email, String password) {
        Log.d("LoginFailure", "Inside Post method");
        String url = "https://drewcav.com/api/Identity/Login";
        RequestQueue request = Volley.newRequestQueue(LogInActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("PostSuccess", response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("LoginFailure", error.toString());
                if (error instanceof NetworkError) {
                    Log.d("LoginFailure", "Network Error");
                } else if (error instanceof AuthFailureError) {
                    Log.d("LoginFailure", "Authentication Error");
                } else if (error instanceof ParseError) {
                    Log.d("LoginFailure", "Parse Error");
                } else if (error instanceof NoConnectionError) {
                    Log.d("LoginFailure", "Communication Error");
                } else if (error instanceof TimeoutError) {
                    Log.d("LoginFailure", "TimeOut Error");
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", email);
                params.put("password", password);
                return params;
            }
                /*@Override
                public Map<String,String> getHeaders() throws AuthFailureError{

                }*/
        };

        request.add(stringRequest);
    }

    private void sendGetRequest(String cemail, String cpassword) {

        RequestQueue queue = Volley.newRequestQueue(LogInActivity.this);
        String url = "https://drewcav.com/api/Identity/Login";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("LoginSuccess", response);
                sendPostRequest(cemail,cpassword);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("LoginFail", error.toString());

            }
        });

        queue.add(stringRequest);
    }

}