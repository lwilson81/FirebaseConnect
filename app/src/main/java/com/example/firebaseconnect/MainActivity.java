package com.example.firebaseconnect;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private EditText fname;
    private EditText lname;
    private EditText phone;
    private DatabaseReference myDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button logout = findViewById(R.id.logout);
        fname = findViewById(R.id.firstname);
        lname = findViewById(R.id.lastname);
        Button add = findViewById(R.id.add);
        phone = findViewById(R.id.phonenumber);
        myDatabase = FirebaseDatabase.getInstance().getReference("ContactInfo");

        logout.setOnClickListener(view -> {
            FirebaseAuth.getInstance().signOut();
            Toast.makeText(MainActivity.this, "Logging Out", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(MainActivity.this, StartActivity.class));
        });
        add.setOnClickListener(v -> checkValidity(fname.getText().toString(), lname.getText().toString(), phone.getText().toString()));
    }

    private void checkValidity(String fname, String lname, String phone) {
        if (fname.isEmpty() || lname.isEmpty() || phone.isEmpty()) {
            Toast.makeText(MainActivity.this, "Enter a name and number", Toast.LENGTH_SHORT).show();
        } else if (phone.length() < 10) {
            Toast.makeText(MainActivity.this, "Enter 10 digit phone number", Toast.LENGTH_SHORT).show();
        }else {
            addDataToFirebase(fname, lname, phone);
        }
    }

    private void addDataToFirebase(String fname, String lname, String phone) {
        String contactKey = myDatabase.push().getKey();
        HashMap<String, Object> contactMap = new HashMap<>();
        contactMap.put("fName", fname);
        contactMap.put("lName", lname);
        contactMap.put("contactNumber", phone);
        myDatabase.child(contactKey).setValue(contactMap)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(MainActivity.this, "Contact has been added", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(MainActivity.this, "Failed to add contact: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
}

