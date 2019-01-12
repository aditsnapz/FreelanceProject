package com.example.asus.freelanceproject;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class NomemberJobListActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    FirebaseFirestore db;
    FloatingActionButton fab;
    private RecyclerView recyclerView;
    private JobsAdapter adapter;
    private ArrayList<BuildJobs> JobsArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nomember_job_list);
        mAuth = FirebaseAuth.getInstance();
        try {
            if (mAuth.getCurrentUser().getEmail().isEmpty() == false) {
                Intent i = new Intent(NomemberJobListActivity.this, JobListActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }
        } catch (Exception e) {

        }
        refresh();
    }
    public void addData() {
        JobsArrayList = new ArrayList<BuildJobs>();
        db.collection("JobsList").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {

                if (e != null) {

                }

                for (DocumentChange documentChange : documentSnapshots.getDocumentChanges()) {
                    String name =  documentChange.getDocument().getData().get("Name").toString();
                    String phone   =  documentChange.getDocument().getData().get("Phone").toString();
                    String email = documentChange.getDocument().getData().get("Email").toString();

                    String jobs = documentChange.getDocument().getData().get("Jobs").toString();
                    String detail = documentChange.getDocument().getData().get("Detail").toString();
                    String status = documentChange.getDocument().getData().get("Status").toString();
                    String gaji = documentChange.getDocument().getData().get("Gaji").toString();
                    JobsArrayList.add(new BuildJobs(name, phone, email, jobs, detail, status,gaji ));
                }
            }
        });
    }
    public void refresh() {
        db = FirebaseFirestore.getInstance();
        addData();
        recyclerView = (RecyclerView) findViewById(R.id.Recycler);
        adapter = new JobsAdapter(JobsArrayList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(NomemberJobListActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),
                        MainActivity.class));
            }
        });
    }
}
