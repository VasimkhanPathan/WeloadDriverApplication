package com.vasim.driverapplication.JobDescription.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vasim.driverapplication.JobDescription.model.WalletModel;
import com.vasim.driverapplication.R;

import java.util.ArrayList;

public class WalletAdapter extends  RecyclerView.Adapter<WalletAdapter.MyViewHolder>{
    Context context;
    ArrayList<WalletModel> walletModelArrayList;

    public WalletAdapter(Context context, ArrayList<WalletModel> walletModelArrayList) {
        this.context = context;
        this.walletModelArrayList = walletModelArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_wallet,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tvJobId.setText(walletModelArrayList.get(position).strJobID);
        holder.tvDropLocation.setText(walletModelArrayList.get(position).strDropLocation);
        holder.tvPickupLocation.setText(walletModelArrayList.get(position).strPickupLocation);
        holder.tvPaymentStatus.setText(walletModelArrayList.get(position).strPaymentStatus);
    }

    @Override
    public int getItemCount() {
        return walletModelArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tvJobId,tvPickupLocation,tvDropLocation,tvPaymentStatus;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvJobId = itemView.findViewById(R.id.tvJobId);
            tvPickupLocation = itemView.findViewById(R.id.tvPickuuLocation);
            tvDropLocation = itemView.findViewById(R.id.tvDropLocation);
            tvPaymentStatus = itemView.findViewById(R.id.tvPaymentStatus);
        }
    }
}
