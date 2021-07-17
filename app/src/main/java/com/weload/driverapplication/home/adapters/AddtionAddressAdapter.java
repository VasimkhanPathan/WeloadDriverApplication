package com.weload.driverapplication.home.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.weload.driverapplication.JobDescription.model.JobDescriptionRespons;
import com.weload.driverapplication.R;

import java.util.ArrayList;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class AddtionAddressAdapter extends RecyclerView.Adapter<AddtionAddressAdapter.MyViewAadater> {
    Context context;
    ArrayList<JobDescriptionRespons.Data.AdditionalLocation> additionalLocationArrayList;

    public AddtionAddressAdapter(Context context,
                                 ArrayList<JobDescriptionRespons.Data.AdditionalLocation> additionalLocationArrayList) {
        this.context = context;
        this.additionalLocationArrayList = additionalLocationArrayList;
    }


    @NonNull
    @Override
    public MyViewAadater onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_additional_location,parent,false);

        return new MyViewAadater(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewAadater holder, int position) {

        holder.tvAddress.setText(additionalLocationArrayList.get(position).unit_no +" "+additionalLocationArrayList.get(position).address);
        holder.tvMobileNo.setText(additionalLocationArrayList.get(position).phone);

        holder.ivCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setPackage("com.android.server.telecom");
                callIntent.setData(Uri.parse("tel:" +additionalLocationArrayList.get(position).phone));
                callIntent.setFlags(FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(callIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return additionalLocationArrayList.size();
    }


    public class MyViewAadater extends RecyclerView.ViewHolder{
        TextView tvAddress,tvMobileNo;
        ImageView ivCall;
        public MyViewAadater(@NonNull View itemView) {
            super(itemView);
            tvMobileNo = itemView.findViewById(R.id.tvMobileNo);
            tvAddress = itemView.findViewById(R.id.tvAddres);
            ivCall = itemView.findViewById(R.id.ivCall);
        }
    }
}
