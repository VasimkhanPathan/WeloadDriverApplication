package com.weload.driverapplication.home.view.activites;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.weload.driverapplication.R;
import com.weload.driverapplication.custom_ui.CalendarView;

import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;

public class CalenderViewAcitvity extends AppCompatActivity implements CalendarView.EventHandler{
    public static final String ARG_PARAM_1 = "type";
    public String type = "";

    @BindView(R.id.tvTitle)
    TextView title;

    @BindView(R.id.calendar_view)
    CalendarView calendarView;

    @BindView(R.id.btnOk)
    Button btnOk;

    public static CalenderViewAcitvity newInstance(String type) {
        CalenderViewAcitvity f = new CalenderViewAcitvity();

        Bundle bundle = new Bundle();
        bundle.putString(ARG_PARAM_1,type);
     //   f.setArguments(bundle);


        return f;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender_view_acitvity);
    }

    @Override
    public void onDayLongPress(Date date) {
        Log.e("date","date::" + date.toString());
    }
    @OnClick(R.id.btnOk)
    public void onClick(){
        Toast.makeText(getApplicationContext(),"dismiss dialog",Toast.LENGTH_SHORT).show();
        finish();
    }
}