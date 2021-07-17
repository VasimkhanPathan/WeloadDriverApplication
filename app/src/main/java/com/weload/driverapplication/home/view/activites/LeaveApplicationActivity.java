package com.weload.driverapplication.home.view.activites;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.weload.driverapplication.JobDescription.view.LeaveCalenderFragement;
import com.weload.driverapplication.R;
import com.weload.driverapplication.api_instance.DriverApiInstance;
import com.weload.driverapplication.api_instance.DriverApiService;
import com.weload.driverapplication.home.interfaces.ChageDate;
import com.weload.driverapplication.home.view.fragments.FragmentDialogCalendarView;
import com.weload.driverapplication.login.model.BaseResponseModel;
import com.weload.driverapplication.util.AppConstants;
import com.weload.driverapplication.util.CustomeProgressDialog;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LeaveApplicationActivity extends AppCompatActivity implements ChageDate {
    @BindView(R.id.btnSelectDate)
    Button btnSelectDate;
    @BindView(R.id.btnSubmit)
    Button btnSubmit;
    @BindView(R.id.etNote)
    EditText etReson;
    String datest="";
    @BindView(R.id.ivBack)
    ImageView ivBackblack;
    @BindView(R.id.ivBackwhite)
    ImageView ivBack;
    @BindView(R.id.layout_File_blue)
    LinearLayout layoutfile;
    @BindView(R.id.layoutMap)
            LinearLayout layoutMap;
    @BindView(R.id.toolTitleWhite)
    TextView toolTitleWhite;
    @BindView(R.id.tvFrom)
    TextView tvFrom;
    @BindView(R.id.tvTo)
    TextView tvTo;
    ArrayList<String> dateList= new ArrayList<>();
  //   MaterialDatePicker materialDatePicker;
  SimpleDateFormat formatter;
  String id="",strApiKey="",strSessionToken="",jobID="";
    String strFromDate="",strToDate="";
    String reson="";
    boolean isConnected=false;
    CustomeProgressDialog customeProgressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave_application);
        ButterKnife.bind(this);
        formatter = new SimpleDateFormat("yyyy-MM-dd");
        customeProgressDialog = new CustomeProgressDialog();
        ivBackblack.setVisibility(View.GONE);
        layoutfile.setVisibility(View.GONE);
        layoutMap.setVisibility(View.GONE);
        toolTitleWhite.setText("Leave Application");
        strApiKey = AppConstants.Preferences.getStringPreference(getApplicationContext(),AppConstants.Keys.API_KEY,"");
        strSessionToken = AppConstants.Preferences.getStringPreference(getApplicationContext(),AppConstants.Keys.SESSION_TOKEN,"");
    }
    @OnClick(R.id.ivBackwhite)
    public void onBack(){
        finish();
    }

    @OnClick(R.id.layoutFrom)
    public void onClick(){

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentDialogCalendarView newFragment = new  FragmentDialogCalendarView(this,"From");
        newFragment.show(fragmentManager, "from_dialog");
    }

    @OnClick(R.id.layoutTo)
    public void onTo(){

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentDialogCalendarView newFragment = new  FragmentDialogCalendarView(this,"To");
        newFragment.show(fragmentManager, "to_dialog");
    }


    @OnClick(R.id.btnSelectDate)
    public void selectDate(){
     //   materialDatePicker.show(getSupportFragmentManager(),"DATE_PICKER");
        FragmentManager fragmentManager = getSupportFragmentManager();
        LeaveCalenderFragement newFragment = new  LeaveCalenderFragement(this,"Leave");
        newFragment.show(fragmentManager, "leave");
    }
    @OnClick(R.id.btnSubmit)
    public void applyLeave(){
     /*   String fromDate=dateList.get(0);
        String toDate=dateList.get(dateList.size()-1);*/
        Date fromDate=null;
        Date toDate=null;
        try{
            fromDate= formatter.parse(strFromDate);
            toDate  = formatter.parse(strToDate);
        }catch (Exception e){

        }

     if(fromDate.compareTo(toDate)<=0){
         reson=etReson.getText().toString();
         if(reson.isEmpty()){
             etReson.setError("Enter Reason");
         }else {
             leavApply();
         }
     }else {
         Toast.makeText(this, "Select correct form date and to date..", Toast.LENGTH_SHORT).show();
     }


       /* Log.d("Log","fromdate "+fromDate);
        Log.d("Log","Todate "+toDate);*/

    }

    @Override
    public void fromDate(Date fromDate) {
       /* String date=formatter.format(fromDate).toString();
        dateList.add(formatter.format(fromDate).toString());
      datest=datest+formatter.format(fromDate)+" ";
      tvShowDate.setText(datest);
        Log.d("Log", "date sie "+dateList.size());*/
        tvFrom.setText(formatter.format(fromDate).toString());
        strFromDate=formatter.format(fromDate).toString();
    }

    @Override
    public void toDate(Date toDate) {
        tvTo.setText(formatter.format(toDate).toString());
        strToDate=formatter.format(toDate).toString();
    }

    public void leavApply(){
        isConnected = AppConstants.isConnected(getApplicationContext());
        if(isConnected){
            customeProgressDialog.showProgress(this);
            DriverApiService driverApiService = DriverApiInstance.getRetrofitInstance().create(DriverApiService.class);
            Call<BaseResponseModel> callLeaveApplicton = driverApiService.leaveApplication(strApiKey,strSessionToken,strFromDate,strToDate,reson);
            callLeaveApplicton.enqueue(new Callback<BaseResponseModel>() {
                @Override
                public void onResponse(Call<BaseResponseModel> call, Response<BaseResponseModel> response) {
                    customeProgressDialog.hideProgress();
                    JSONObject jsonObjectResponse=null;
                    try{
                        jsonObjectResponse= new JSONObject(new Gson().toJson(response.body()));
                        Log.d("Log","Response "+jsonObjectResponse);

                    }catch (Exception e){
                        Log.d("Log",e.getMessage());
                    }
                    if(response!=null){
                        Toast.makeText(LeaveApplicationActivity.this, ""+response.body().message, Toast.LENGTH_LONG).show();
                        strFromDate="";
                        strToDate="";
                        tvFrom.setText("From");
                        tvTo.setText("To");
                        etReson.setText("");
                        sucessDialog(response.body().message);

                    }
                }

                @Override
                public void onFailure(Call<BaseResponseModel> call, Throwable t) {
                    customeProgressDialog.hideProgress();
                    Log.d("Log","eror "+t.getMessage());
                }
            });
        }

    }
    public void sucessDialog(String message){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_job_started);
        dialog.setCancelable(false);

        Button btnSubmit = dialog.findViewById(R.id.btnSubmit);
        Button btnStart = dialog.findViewById(R.id.btnStart);
        TextView tvMessage= dialog.findViewById(R.id.tvMessage);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;
        tvMessage.setText(message);
        btnStart.setVisibility(View.GONE);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();

            }
        });

        dialog.show();
    }
}