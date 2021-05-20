package com.vasim.driverapplication.home.adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.vasim.driverapplication.JobDescription.JobDescriptionFragment;
import com.vasim.driverapplication.R;
import com.vasim.driverapplication.home.HomeActivity;
import com.vasim.driverapplication.home.interfaces.ChangesInActivies;
import com.vasim.driverapplication.home.model.JobsResponse;
import com.vasim.driverapplication.home.view.HomeFragement;

import java.util.ArrayList;

public class JobsAdapters extends RecyclerView.Adapter<JobsAdapters.MyViewHolder>{
    public Context context;
    public ArrayList<JobsResponse> jobsResponseArrayList;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    ChangesInActivies changesInActivies;

    public JobsAdapters(Context context, ArrayList<JobsResponse> jobsResponseArrayList,ChangesInActivies changesInActivies) {
        this.context = context;
        this.jobsResponseArrayList = jobsResponseArrayList;
        this.changesInActivies = changesInActivies;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(context).inflate(R.layout.layout_jobs_adapter,parent,false);
       return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tvPickupLocation.setText(jobsResponseArrayList.get(position).pickUpLocation);
        holder.tvPickupDate.setText(jobsResponseArrayList.get(position).pickUpDate);
        holder.tvJobId.setText(jobsResponseArrayList.get(position).jobsId);
        holder.tvDropLocation.setText(jobsResponseArrayList.get(position).dropLocation);
        holder.tvDeliveryDate.setText(jobsResponseArrayList.get(position).deliveryDate);
    }

    @Override
    public int getItemCount() {
        return jobsResponseArrayList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tvJobId,tvPickupDate,tvPickupLocation,tvDeliveryDate,tvDropLocation;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDeliveryDate=itemView.findViewById(R.id.tvDelivetyDate);
            tvDropLocation=itemView.findViewById(R.id.tvDropLocation);
            tvJobId=itemView.findViewById(R.id.tvJobId);
            tvPickupDate=itemView.findViewById(R.id.tvPickupDate);
            tvPickupLocation=itemView.findViewById(R.id.tvPickupLocation);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  changesInActivies.changesInActivity("description");
                }
            });
        }
    }
}
