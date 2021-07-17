package com.weload.driverapplication.home.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.weload.driverapplication.JobDescription.view.activities.JobDescriptionActivity;
import com.weload.driverapplication.R;
import com.weload.driverapplication.home.model.JobsResponse;

import java.util.ArrayList;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class JobsAdapters extends RecyclerView.Adapter<JobsAdapters.MyViewHolder>{
    public Context context;
    public ArrayList<JobsResponse.Datum> jobsResponseArrayList;
    String jobFlag;
    String strStatus="";
   /* FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    ChangesInActivies changesInActivies;*/

    public JobsAdapters(Context context, ArrayList<JobsResponse.Datum> jobsResponseArrayList/*,ChangesInActivies changesInActivies*/,String jobFlag) {
        this.context = context;
        this.jobsResponseArrayList = jobsResponseArrayList;
        this.jobFlag=jobFlag;
       /* this.changesInActivies = changesInActivies;*/
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(context).inflate(R.layout.layout_jobs_adapter,parent,false);
       return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tvPickupLocation.setText(jobsResponseArrayList.get(position).pickup_unitno+" "+jobsResponseArrayList.get(position).pickup_address);
        holder.tvPickupDate.setText(jobsResponseArrayList.get(position).pickup_date);
        holder.tvPickupTime.setText(jobsResponseArrayList.get(position).pickup_time);
        holder.tvJobId.setText(jobsResponseArrayList.get(position).jobid);
        holder.tvDropLocation.setText(jobsResponseArrayList.get(position).drop_unitno+" "+jobsResponseArrayList.get(position).drop_address);
        holder.tvDeliveryDate.setText(jobsResponseArrayList.get(position).pickup_date);
        holder.tvDropUnitNo.setText(jobsResponseArrayList.get(position).drop_unitno);
        holder.tvPickupUnitNo.setText(jobsResponseArrayList.get(position).pickup_unitno);
        holder.tvCustomerName.setText(jobsResponseArrayList.get(position).customer_name);
        holder.tvJobStatus.setText(""+getJobStatus(jobsResponseArrayList.get(position).job_status));

    }

    @Override
    public int getItemCount() {
        return jobsResponseArrayList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tvJobId,tvPickupDate,tvPickupLocation,tvDeliveryDate,tvDropLocation,tvPickupUnitNo,tvDropUnitNo,tvJobStatus;
        TextView tvCustomerName,tvPickupTime;
        ImageView viewIcon;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDeliveryDate=itemView.findViewById(R.id.tvDelivetyDate);
            tvDropLocation=itemView.findViewById(R.id.tvDropLocation);
            tvJobId=itemView.findViewById(R.id.tvJobId);
            tvPickupDate=itemView.findViewById(R.id.tvPickupDate);
            tvPickupLocation=itemView.findViewById(R.id.tvPickupLocation);
            tvPickupUnitNo=itemView.findViewById(R.id.tvPickupUnitNo);
            tvDropUnitNo=itemView.findViewById(R.id.tvDropUnitNo);
            tvJobStatus=itemView.findViewById(R.id.tvJobStatus);
            tvCustomerName=itemView.findViewById(R.id.tvCustomerName);
            viewIcon=itemView.findViewById(R.id.viewIcon);
            tvPickupTime=itemView.findViewById(R.id.tvPickupTime);
            if(jobFlag.equals("job")){
                tvJobStatus.setVisibility(View.VISIBLE);
                viewIcon.setVisibility(View.VISIBLE);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
              //    changesInActivies.changesInActivity("description",jobsResponseArrayList.get(getAdapterPosition()).job_token);
                    Intent intent = new Intent(context, JobDescriptionActivity.class);
                    intent.putExtra("jobToken",jobsResponseArrayList.get(getAdapterPosition()).job_token);
                    intent.putExtra("jobId",jobsResponseArrayList.get(getAdapterPosition()).jobid);
                    intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });
            }else {
                tvJobStatus.setVisibility(View.GONE);
                viewIcon.setVisibility(View.GONE);
            }
        }

    }
    public String getJobStatus(int status){
        switch (status){
            case 1:
                strStatus="Assigned";
                break;
            case 2:
                strStatus="Started";
                break;
            case 3:
                strStatus="Completed";
                break;
            default:
                strStatus="";
        }
        return strStatus;
    }

}
