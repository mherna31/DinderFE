package com.example.dinderfe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.service.voice.VoiceInteractionSession;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {
    Button create, back;
    EditText email, password, cpassword;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    String url = "https://drewcav.com/api/Identity/Create";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        create = findViewById(R.id.buttonConfirm);
        back = findViewById(R.id.buttonBack);
        email = findViewById(R.id.cEmail);
        password = findViewById(R.id.cPassword);
        cpassword = findViewById(R.id.confirmPassword);

        //Listener for confirm password
        cpassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!cpassword.getText().toString().equals(password.getText().toString())) {
                    cpassword.setError("Password Does not Match");
                }

            }
        });

        //listener for back button
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LogInActivity.class);
                startActivity(intent);
            }
        });

        //listener for create profile button
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Data validation
                if (email.getText().toString().trim().equalsIgnoreCase("")) {

                    email.setError("Cannot be empty");

                } else if (!email.getText().toString().trim().matches(emailPattern)) {

                    email.setError("Invalid email");

                } else if (password.getText().toString().trim().equalsIgnoreCase("")) {

                    password.setError("Cannot be empty");

                } else if (cpassword.getText().toString().trim().equalsIgnoreCase("")) {

                    cpassword.setError("Cannot be empty");

                } else if (cpassword.getText().toString().equals(password.getText().toString())) {

                    try {
                        postRequest(email.getText().toString(), password.getText().toString(), cpassword.getText().toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            }
        });
    }

    private void postRequest(String email, String password, String cPassword) throws JSONException {
        RequestQueue requestQueue = Volley.newRequestQueue(SignUpActivity.this);
        JSONObject jsonBody = new JSONObject();
        jsonBody.put("email", email);
        jsonBody.put("password", password);
        jsonBody.put("confirmPassword", cPassword);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonBody, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(getApplicationContext(), "Success!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(), LogInActivity.class);
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
                        Log.d("on error"," "+message);
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
        };

        requestQueue.add(jsonObjectRequest);
    }

}