package com.example.asus.freelanceproject;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class JobsDetailActivity extends AppCompatActivity {

    private static final String NAME_KEY = "Name";
    private static final String EMAILP_KEY = "EmailP";
    private static final String EMAIL_KEY = "Email";
    private static final String PHONE_KEY = "Phone";
    private static final String JOBS_KEY = "Jobs";
    private static final String DETAIL_KEY = "Detail";
    private static final String STATUS_KEY = "Status";
    private static final String GAJI_KEY = "Gaji";
    String name, phone, email, emailp,jobs, detail,status,gaji;
    Button Buttoncall,Buttonemail,Buttonambil,Buttondelete;
    FirebaseAuth mAuth;
    FirebaseFirestore db;
    TextView temp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        try {
            if(mAuth.getCurrentUser().getEmail().isEmpty() == false){
                setContentView(R.layout.activity_jobs_detail);
            } else {
                setContentView(R.layout.activity_jobs_detail_nonmember);
            }
        } catch (Exception e)
        {
            setContentView(R.layout.activity_jobs_detail_nonmember);
        }

        Intent intent = getIntent();
        final String vname = intent.getStringExtra("Name");
        final String vjobsName = intent.getStringExtra("Jobs");
        final String vjobsDetail = intent.getStringExtra("Detail");
        final String vstatus = intent.getStringExtra("Status");
        final String vphone = intent.getStringExtra("Phone");
        final String vemail = intent.getStringExtra("Email");
        final String vemailp = intent.getStringExtra("EmailP");


        Toast.makeText(JobsDetailActivity.this, vname + ", " + vjobsName + ", " + vstatus , Toast.LENGTH_SHORT).show();
        TextView tname = (TextView) findViewById(R.id.name);
        tname.setText(vname);
        TextView tjobsname = (TextView) findViewById(R.id.jobs);
        tjobsname.setText(vjobsName);
        TextView tstatus = (TextView) findViewById(R.id.status);
        tstatus.setText(vstatus);
        TextView tphone = (TextView) findViewById(R.id.phone);
        TextView temail = (TextView) findViewById(R.id.email);
        email = vemail;
        jobs = vjobsName;
        name = vname;


        TextView tjobsdetail = (TextView) findViewById(R.id.detail);
        tjobsdetail.setText(vjobsDetail);
        try {
            if(mAuth.getCurrentUser().getEmail().isEmpty() == false){
                Buttoncall = (Button) findViewById(R.id.phone);
                Buttoncall.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(v == Buttoncall){
                            call(vphone);
                        }
                    }
                });
                Buttonemail = (Button) findViewById(R.id.email);
                Buttonemail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(v == Buttonemail){
                            sendemail(vemail);
                        }
                    }
                });
                Buttondelete = (Button) findViewById(R.id.delete);
                Buttondelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(v == Buttondelete){
                            delete();
                        }
                    }
                });
                Buttonambil = (Button) findViewById(R.id.take);
                if(vstatus.equals("Belum Diambil")) {
                    Buttonambil.setText("Take");
                } else {
                    Buttonambil.setText("UnTake");
                }

                Buttonambil.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(v == Buttonambil){
                            Map<String, Object> newContact = new HashMap<>();
                            mAuth = FirebaseAuth.getInstance();
                            email = mAuth.getCurrentUser().getEmail();
                            name = vname;
                            emailp = vemailp;
                            phone = vphone;
                            jobs = vjobsName;
                            detail = vjobsDetail;
                            if(vstatus.equals("Belum Diambil")){
                                status = "Diambil";
                            }
                            else {
                                status = "Belum Diambil";
                            }

                            gaji = "0";
                            newContact.put(NAME_KEY, name);
                            newContact.put(EMAIL_KEY, email);
                            newContact.put(EMAILP_KEY, emailp);
                            newContact.put(PHONE_KEY, phone);
                            newContact.put(JOBS_KEY, jobs);
                            newContact.put(DETAIL_KEY, detail);
                            newContact.put(STATUS_KEY,status);
                            newContact.put(GAJI_KEY,gaji);

                            Toast.makeText(JobsDetailActivity.this,email+"_"+name+"_"+jobs,
                                    Toast.LENGTH_SHORT).show();
                            db.collection("JobsList").document(email+"_"+name+"_"+jobs).update(newContact)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(JobsDetailActivity.this, "Job " + vstatus,
                                                    Toast.LENGTH_SHORT).show();

                                            startActivity(new Intent(JobsDetailActivity.this, JobListActivity.class));
                                            finish();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(JobsDetailActivity.this, "ERROR" + e.toString(),
                                                    Toast.LENGTH_SHORT).show();
                                            Log.d("TAG", e.toString());
                                        }
                                    });
                        }
                    }
                });

            }
        } catch(Exception e) {

        }
    }


    public void sendemail(String vemail) {
        Uri uri = Uri.parse("mailto:" +vemail);
        Intent it = new Intent(Intent.ACTION_VIEW,uri);
        startActivity(it);
    }

    public void call(String vphone) {
        Uri uri = Uri.parse("tel:"+vphone);
        Intent it = new Intent(Intent.ACTION_DIAL,uri);
        startActivity(it);
    }
    public void delete(){
        Map<String, Object> newContact = new HashMap<>();
        newContact.put(NAME_KEY, name);
        newContact.put(EMAIL_KEY, email);
        newContact.put(EMAILP_KEY, emailp);
        newContact.put(PHONE_KEY, phone);
        newContact.put(JOBS_KEY, jobs);
        newContact.put(DETAIL_KEY, detail);
        newContact.put(STATUS_KEY,status);
        newContact.put(GAJI_KEY,gaji);

        try {
            db.collection("JobsList").document(email+"_"+name+"_"+jobs).delete()
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(JobsDetailActivity.this, "Job Deleted" +email+ name+ jobs  ,
                                    Toast.LENGTH_SHORT).show();

                            startActivity(new Intent(JobsDetailActivity.this, JobListActivity.class));
                            finish();
                        }
                    });

        } catch (Exception e) {
            Toast.makeText(JobsDetailActivity.this, "Job Not Deleted", Toast.LENGTH_SHORT).show();
        }

    }
}
