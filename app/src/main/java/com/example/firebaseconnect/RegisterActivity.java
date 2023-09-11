package com.example.firebaseconnect;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        email = findViewById(R.id.editEmail);
        password = findViewById(R.id.editPassword);
        Button register = findViewById(R.id.registerButton);
        auth = FirebaseAuth.getInstance();

        register.setOnClickListener(v -> checkValidity(email.getText().toString(), password.getText().toString()));
    }

    private void checkValidity(String email, String password) {
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(RegisterActivity.this, "You must fill in valid credentials", Toast.LENGTH_SHORT).show();
        } else if (password.length() < 8) {
            Toast.makeText(RegisterActivity.this, "Password must be 8 or more characters", Toast.LENGTH_SHORT).show();
        } else {
            registerUser(email, password);
        }
    }

    private void registerUser(String email, String password) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(RegisterActivity.this, task -> {
            if (task.isSuccessful()) {
                Toast.makeText(RegisterActivity.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(RegisterActivity.this, MainActivity.class));
            } else {
                Toast.makeText(RegisterActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}