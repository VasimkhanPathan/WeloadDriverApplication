package com.vasim.driverapplication.home.view.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.vasim.driverapplication.R;
import com.vasim.driverapplication.custom_ui.CalendarView;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FragmentDialogCalendarView  extends DialogFragment implements CalendarView.EventHandler {

    public static final String ARG_PARAM_1 = "type";
    public String type = "";

    @BindView(R.id.tvTitle)
    TextView  title;

    @BindView(R.id.calendar_view)
    CalendarView calendarView;

     @BindView(R.id.btnOk)
     Button btnOk;

    public static FragmentDialogCalendarView newInstance(String type) {
        FragmentDialogCalendarView f = new FragmentDialogCalendarView();

        Bundle bundle = new Bundle();
        bundle.putString(ARG_PARAM_1,type);
        f.setArguments(bundle);


        return f;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        type = bundle.getString(ARG_PARAM_1);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragmentdialog_calendar, container, false);
        ButterKnife.bind(this,v);
        title.setText(type);

        calendarView.setEventHandler(this);

        return v;
    }

    @Override
    public void onDayLongPress(Date date) {

        Log.e("date","date::" + date.toString());
    }

    @OnClick(R.id.btnOk)
    public void onClick(){
        Toast.makeText(getContext(),"dismiss dialog",Toast.LENGTH_SHORT).show();
        dismiss();
    }

}

