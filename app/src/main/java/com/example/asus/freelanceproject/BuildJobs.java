package com.example.asus.freelanceproject;

public class BuildJobs {
    String name,email,phone,emailp,jobs,detail,status,gaji;
    public BuildJobs(String lname,String lphone, String lemail,  String ljobs,String ldetail, String lstatus, String lgaji) {
        this.name = lname;
        this.email = lemail;
        this.phone = lphone;
        //this.emailp = lemailp;
        this.jobs = ljobs;
        this.detail = ldetail;
        this.status = lstatus;
        this.gaji = lgaji;
    }
}
