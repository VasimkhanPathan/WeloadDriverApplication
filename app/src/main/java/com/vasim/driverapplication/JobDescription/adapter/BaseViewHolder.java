package com.vasim.driverapplication.JobDescription.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by lin18 on 2017/8/23.
 */

public class BaseViewHolder extends RecyclerView.ViewHolder {

    Unbinder unbinder;

    public BaseViewHolder(@NonNull ViewGroup parent, @LayoutRes int layoutResId) {
        this(LayoutInflater.from(parent.getContext()).inflate(layoutResId, parent, false));
    }

    public BaseViewHolder(View itemView) {
        super(itemView);
        unbinder = ButterKnife.bind(this, itemView);
    }
}
