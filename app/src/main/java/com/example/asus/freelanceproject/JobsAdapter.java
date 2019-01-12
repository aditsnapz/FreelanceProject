package com.example.asus.freelanceproject;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class JobsAdapter extends RecyclerView.Adapter<JobsAdapter.JobsViewHolder> {
    private ArrayList<BuildJobs> dataList;

    public JobsAdapter(ArrayList<BuildJobs> dataList) {
        this.dataList = dataList;
    }

    @Override
    public JobsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.joblist, parent, false);
        return new JobsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(JobsViewHolder holder, int position) {
        holder.name.setText(dataList.get(position).name);
        holder.phone.setText(dataList.get(position).phone);
        //holder.email.setText(dataList.get(position).email);
        //holder.emailp.setText(dataList.get(position).emailp);
        holder.jobs.setText(dataList.get(position).jobs);
        holder.status.setText(dataList.get(position).status);
    }

    @Override
    public int getItemCount() {
        return (dataList != null) ? dataList.size() : 0;
    }

    public class JobsViewHolder extends RecyclerView.ViewHolder{
        private TextView name, phone, email, emailp, jobs, detail, status,gaji;
        public JobsViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            phone = (TextView) itemView.findViewById(R.id.phone);
            jobs = (TextView) itemView.findViewById(R.id.jobs);
            status = (TextView) itemView.findViewById(R.id.status);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int post = getAdapterPosition();
                    String element = dataList.get(post).name;
                    String element2 = dataList.get(post).phone;
                    String element3 = dataList.get(post).email;
                    String element5 = dataList.get(post).jobs;
                    String element6 = dataList.get(post).detail;
                    String element7 = dataList.get(post).status;
                    String element8 = dataList.get(post).gaji;
                    Intent intent = new Intent(v.getContext(), JobsDetailActivity.class);
                    intent.putExtra("Name", element);
                    intent.putExtra("Phone", element2);
                    intent.putExtra("Email", element3);
                    intent.putExtra("Jobs", element5);
                    intent.putExtra("Detail", element6);
                    intent.putExtra("Status", element7);
                    intent.putExtra("Gaji", element8);
                    v.getContext().startActivity(intent);
                    Toast.makeText(v.getContext(), "CLICKED " + element + ", " + element2 + ", " + element3, Toast.LENGTH_SHORT).show();
                }
            });
        }


    }
}
