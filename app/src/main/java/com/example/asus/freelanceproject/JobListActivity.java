package com.example.asus.freelanceproject;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class JobListActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    FirebaseFirestore db;
    FloatingActionButton fab;
    Button Buttonlogout;
    RecyclerView recyclerView;
    JobsAdapter adapter;
    SwipeRefreshLayout mSwipeRefreshLayout;
    private ArrayList<BuildJobs> JobsArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_list);
        refresh();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),
                        JobsActivity.class));
            }
        });
        Buttonlogout = (Button) findViewById(R.id.logout);
        Buttonlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v == Buttonlogout){
                    logout();

                }
            }
        });


    }

    private void refresh() {
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        addData();

        mSwipeRefreshLayout = findViewById(R.id.refresh);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh items
                recycle();
                addData();
            }
        });
        recycle();


    }

    private void recycle() {
        recyclerView = (RecyclerView) findViewById(R.id.Recycler);
        adapter = new JobsAdapter(JobsArrayList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(JobListActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        mSwipeRefreshLayout.setRefreshing(false);
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

    public void logout() {
        mAuth.signOut();
        startActivity(new Intent(getApplicationContext(),
                NomemberJobListActivity.class));
    }









}
