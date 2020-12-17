package com.example.dinderfe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class LogInActivity extends AppCompatActivity {

    Button login, signup;
    EditText email, password;
    String cookie;

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

    //Post request for logging in
    private void sendPostRequest(String email, String password) throws JSONException {
       // Log.d("Login", "Inside Post method");
        String url = "https://drewcav.com/api/Identity/Login";
        JSONObject jsonBody = new JSONObject();
        jsonBody.put("email", email);
        jsonBody.put("password", password);
        jsonBody.put("persistentLogin", false);
        RequestQueue request = Volley.newRequestQueue(LogInActivity.this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonBody, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(getApplicationContext(), "Success!", Toast.LENGTH_SHORT).show();
                try {
                    String ids = response.getString("id");
                    //Log.d("Login", "id: " + ids);
                    Log.d("Login", "Success" + response.toString());
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra("USER_ID",ids);
                    intent.putExtra("COOKIE",cookie);
                    startActivity(intent);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkResponse networkResponse = error.networkResponse;
                Log.d("on error", " onError");
                if (error instanceof ServerError && networkResponse != null) {
                    try {
                        String jsonerror = new String(networkResponse.data,
                                HttpHeaderParser.parseCharset(networkResponse.headers, "utf-8"));

                        JSONObject message = new JSONObject(jsonerror);
                        Log.d("on error", " hey:" + message);
                        Toast.makeText(getApplicationContext(), message.getString("message"), Toast.LENGTH_LONG).show();

                    } catch (JSONException | UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                } else if (error instanceof AuthFailureError && networkResponse != null) {
                    try {
                        String jsonerror = new String(networkResponse.data,
                                HttpHeaderParser.parseCharset(networkResponse.headers, "utf-8"));

                        JSONObject message = new JSONObject(jsonerror);
                        Log.d("on error", " hey:" + message);
                        Toast.makeText(getApplicationContext(), message.getString("message"), Toast.LENGTH_LONG).show();

                    } catch (JSONException | UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");

                return headers;
            }
            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {

                Map<String, String> responseHeaders = response.headers;
                 cookie = responseHeaders.get("Set-Cookie");
                //Log.i("cookies",cookie);
                return super.parseNetworkResponse(response);
            }
        };

        request.add(jsonObjectRequest);

    }

    //Get request for Logging in
    private void sendGetRequest(String cemail, String cpassword) {

        RequestQueue queue = Volley.newRequestQueue(LogInActivity.this);
        String url = "https://drewcav.com/api/Identity/Login";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("LoginSuccess", response);
                try {
                    sendPostRequest(cemail, cpassword);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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