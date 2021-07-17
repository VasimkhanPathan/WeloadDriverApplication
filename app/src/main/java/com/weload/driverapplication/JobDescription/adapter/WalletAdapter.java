package com.weload.driverapplication.JobDescription.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.weload.driverapplication.JobDescription.model.WalletResponse;
import com.weload.driverapplication.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class WalletAdapter extends  RecyclerView.Adapter<WalletAdapter.MyViewHolder>{
    Context context;
    ArrayList<WalletResponse.Datum> walletModelArrayList;
    DateFormat formatter;
    Date date;
    String[] splitDate=new String[3];
    public WalletAdapter(Context context, ArrayList<WalletResponse.Datum> walletModelArrayList) {
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
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate;
        try {
            startDate = df.parse(walletModelArrayList.get(position).job_date);
            DateFormat outputFormatter1 = new SimpleDateFormat("dd MMM yy");
            splitDate = outputFormatter1.format(startDate).split(" ");
            holder.tvDate.setText(splitDate[0]);
            holder.tvMonthYear.setText(splitDate[1]+" "+splitDate[2]);

        } catch (ParseException e) {
            e.printStackTrace();
        }


        holder.tvJobId.setText(walletModelArrayList.get(position).job_id);
        holder.tvDropLocation.setText(walletModelArrayList.get(position).transactionid);
        holder.tvPickupLocation.setText(walletModelArrayList.get(position).job_date);
        holder.tvPaymentStatus.setText(walletModelArrayList.get(position).diff_note);
        holder.tvCharges.setText("+ $ "+walletModelArrayList.get(position).driver_amount);
        if(walletModelArrayList.get(position).transacation_type.equals("1")){
            holder.tvPaymentStatus.setTextColor(Color.parseColor("#40C5A1"));
            }else
            if(walletModelArrayList.get(position).transacation_type.equals("2")){
                holder.tvPaymentStatus.setTextColor(Color.parseColor("#EE6C6C")); }

    }

    @Override
    public int getItemCount() {
        return walletModelArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tvJobId,tvPickupLocation,tvDropLocation,tvPaymentStatus,tvCharges,tvDate,tvMonthYear;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvJobId = itemView.findViewById(R.id.tvJobId);
            tvPickupLocation = itemView.findViewById(R.id.tvPickuuLocation);
            tvDropLocation = itemView.findViewById(R.id.tvDropLocation);
            tvPaymentStatus = itemView.findViewById(R.id.tvPaymentStatus);
            tvCharges = itemView.findViewById(R.id.tvCharges);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvMonthYear = itemView.findViewById(R.id.tvMontYear);
        }
    }
}
