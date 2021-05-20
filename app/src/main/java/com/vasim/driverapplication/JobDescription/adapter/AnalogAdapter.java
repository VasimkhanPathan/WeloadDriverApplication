package com.vasim.driverapplication.JobDescription.adapter;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.lin.poweradapter.PowerViewHolder;
import com.lin.poweradapter.SingleAdapter;
import com.vasim.driverapplication.JobDescription.model.Analog;
import com.vasim.driverapplication.R;
import com.vasim.driverapplication.home.adapters.JobsAdapters;

import butterknife.BindView;
import butterknife.ButterKnife;


public class AnalogAdapter extends SingleAdapter<Analog, AnalogAdapter.ChildViewHolder> {
    Context context;
    public AnalogAdapter(@Nullable Object listener,Context context) {
        super(listener);
        this.context=context;
    }

    @Override
    public ChildViewHolder onCreateVHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.analog_item,parent,false);
        return new ChildViewHolder(view);
     //   return new ChildViewHolder(parent, R.layout.analog_item);
    }

    @Override
    public void onBindVHolder(ChildViewHolder holder, int position) {
        final Context context = holder.itemView.getContext();
        final Analog analog = getItem(position);
        final int color = ContextCompat.getColor(context,
                analog.isHead ? R.color.splash_color : android.R.color.darker_gray);
         holder.title.setTextColor(color);
        holder.title.setText(analog.text);

        holder.time.setTextColor(color);
        holder.time.setText(analog.time);
    }

    public static class ChildViewHolder extends PowerViewHolder {

        @BindView(R.id.title)
        TextView title ;

        @BindView(R.id.time)
        TextView time;

      /*  ChildViewHolder(@NonNull ViewGroup parent, @LayoutRes int layoutResId) {
            super(parent, layoutResId);
            ButterKnife.bind(parent);
            title = parent.findViewById(R.id.title);
            time = parent.findViewById(R.id.time);
        }*/

        ChildViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            time = itemView.findViewById(R.id.time);
        }

    }

}
