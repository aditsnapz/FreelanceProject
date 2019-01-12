package com.example.asus.freelanceproject;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class JobsActivity extends AppCompatActivity {

    private static final String NAME_KEY = "Name";
    private static final String EMAILP_KEY = "EmailP";
    private static final String EMAIL_KEY = "Email";
    private static final String PHONE_KEY = "Phone";
    private static final String JOBS_KEY = "Jobs";
    private static final String DETAIL_KEY = "Detail";
    private static final String STATUS_KEY = "Status";
    private static final String GAJI_KEY = "Gaji";
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    FirebaseFirestore db;
    String name,email,emailp,phone,jobs,detail, status, gaji;
    TextView temp;
    Button add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jobs);
        db = FirebaseFirestore.getInstance();

        add = findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewContact();
            }
        });
    }
    private void addNewContact() {
        Map<String, Object> newContact = new HashMap<>();
        mAuth = FirebaseAuth.getInstance();
        email = mAuth.getCurrentUser().getEmail();
        temp = findViewById(R.id.name);
        name = temp.getText().toString();
        temp = findViewById(R.id.emailp);
        emailp = temp.getText().toString();
        temp = findViewById(R.id.phone);
        phone = temp.getText().toString();
        temp = findViewById(R.id.jobs);
        jobs = temp.getText().toString();
        temp = findViewById(R.id.detail);
        detail = temp.getText().toString();
        status = "Belum Diambil";
        gaji = "0";
        newContact.put(NAME_KEY, name);
        newContact.put(EMAIL_KEY, email);
        newContact.put(EMAILP_KEY, emailp);
        newContact.put(PHONE_KEY, phone);
        newContact.put(JOBS_KEY, jobs);
        newContact.put(DETAIL_KEY, detail);
        newContact.put(STATUS_KEY,status);
        newContact.put(GAJI_KEY,gaji);
        db.collection("JobsList").document(email+"_"+name+"_"+jobs).set(newContact)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(JobsActivity.this, "User Registered",
                                Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(),
                                JobListActivity.class));
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(JobsActivity.this, "ERROR" + e.toString(),
                                Toast.LENGTH_SHORT).show();
                        Log.d("TAG", e.toString());
                    }
                });
    }
}
