package com.example.dinderfe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SignUpActivity extends AppCompatActivity {
    Button create, back;
    EditText email, password, cPassword;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        create = findViewById(R.id.buttonConfirm);
        back = findViewById(R.id.buttonBack);
        email = findViewById(R.id.cEmail);
        password = findViewById(R.id.cPassword);
        cPassword = findViewById(R.id.confirmPassword);

        cPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!cPassword.getText().toString().equals(password.getText().toString())) {
                    cPassword.setError("Password Does not Match");
                }

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LogInActivity.class);
                startActivity(intent);
            }
        });

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (email.getText().toString().trim().equalsIgnoreCase("")) {

                    email.setError("Cannot be empty");

                } else if (!email.getText().toString().trim().matches(emailPattern)) {

                    email.setError("Invalid email");

                } else if (password.getText().toString().trim().equalsIgnoreCase("")) {

                    password.setError("Cannot be empty");

                } else if (cPassword.getText().toString().trim().equalsIgnoreCase("")) {

                    cPassword.setError("Cannot be empty");

                } else if (cPassword.getText().toString().equals(password.getText().toString())) {

                }
            }
        });
    }
}