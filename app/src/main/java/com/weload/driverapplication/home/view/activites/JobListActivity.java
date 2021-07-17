package com.weload.driverapplication.home.view.activites;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.weload.driverapplication.R;
import com.weload.driverapplication.api_instance.DriverApiInstance;
import com.weload.driverapplication.api_instance.DriverApiService;
import com.weload.driverapplication.home.adapters.JobsAdapters;
import com.weload.driverapplication.home.interfaces.ChageDate;
import com.weload.driverapplication.home.model.JobsResponse;
import com.weload.driverapplication.home.view.fragments.FragmentDialogCalendarView;
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

public class JobListActivity extends AppCompatActivity implements ChageDate {
    @BindView(R.id.rvJobs)
    RecyclerView rvJobs;
    @BindView(R.id.topDivider)
    TableRow divider;
    @BindView(R.id.top)
    LinearLayout layoutTop;

    @BindView(R.id.tvFrom)
    TextView tvFrom;
    @BindView(R.id.tvTo)
    TextView tvTo;

    @BindView(R.id.layout_tool)
    RelativeLayout layout_tool;
    @BindView(R.id.layoutFrom)
    LinearLayout layoutFrom;
    @BindView(R.id.layoutTo)
    LinearLayout layoutTo;
    @BindView(R.id.toolTitleWhite)
    TextView toolTitleWhite;
    @BindView(R.id.layout_File_blue)
    LinearLayout layout_File_blue;
    @BindView(R.id.ivJobStatusWhiteBackground)
    ImageView ivJobStatusWhiteBackground;
    @BindView(R.id.ivMapMarkerWhite)
    ImageView ivMapMarkerWhite;
    @BindView(R.id.ivMapMarkerBlue)
    ImageView ivMapMarkerBlue;
    @BindView(R.id.ivBackwhite)
    ImageView ivBackwhite;
    @BindView(R.id.layoutBack)
    LinearLayout layoutBack;
    @BindView(R.id.layoutToolIcons)
    LinearLayout layoutToolIcons;
    @BindView(R.id.layoutSearch)
    LinearLayout layoutSearch;


    RecyclerView.LayoutManager layoutManager;
    JobsResponse jobsResponse;
    ArrayList<JobsResponse.Datum> jobsResponseArrayList;
    JobsAdapters jobsAdapters;
    String jobFlag="",strApiKey="",strSessionToken="";
    boolean isConnectd=false;
    CustomeProgressDialog customeProgressDialog;
    String id;
    Date today;
    SimpleDateFormat formatter;
    String strFromDate="",strToDate="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_list);
        ButterKnife.bind(this);
        layout_tool.setBackgroundColor(Color.parseColor("#ffffff"));
         formatter = new SimpleDateFormat("yyyy-MM-dd");
        today = new Date();
       Log.d("Log",formatter.format(today).toString());
        customeProgressDialog = new CustomeProgressDialog();
        ivBackwhite.setVisibility(View.GONE);
        strApiKey = AppConstants.Preferences.getStringPreference(getApplicationContext(),AppConstants.Keys.API_KEY,"");
        strSessionToken = AppConstants.Preferences.getStringPreference(getApplicationContext(),AppConstants.Keys.SESSION_TOKEN,"");
        jobFlag = getIntent().getStringExtra("pageFlag");
        layoutToolIcons.setVisibility(View.INVISIBLE);
        if(jobFlag.equalsIgnoreCase("job")){
            toolTitleWhite.setText("Jobs");
            toolTitleWhite.setTextColor(Color.parseColor("#255392"));
            layoutTop.setVisibility(View.GONE);
            divider.setVisibility(View.GONE);
            getAllJobs();
        }else {
            toolTitleWhite.setText("Jobs History");
            toolTitleWhite.setTextColor(Color.parseColor("#255392"));
            layoutTop.setVisibility(View.VISIBLE);
            divider.setVisibility(View.VISIBLE);
            getJobHistory();
        }
        jobsResponseArrayList= new ArrayList<>();
        layoutManager = new LinearLayoutManager(getApplicationContext());
        rvJobs.setLayoutManager(layoutManager);
        rvJobs.setHasFixedSize(false);

    }
    @OnClick(R.id.layoutFrom)
    public void onClick(){


//        FragmentTransaction ft = getFragmentManager().beginTransaction();
//        Fragment prev = getFragmentManager().findFragmentByTag("from_dialog");
//        if (prev != null) {
//            ft.remove(prev);
//        }
//        ft.addToBackStack(null);
//
//        // Create and show the dialog.
//        FragmentDialogCalendarView newFragment = FragmentDialogCalendarView.newInstance("From");
//        newFragment.show(ft, "from_dialog");
      /*  FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentDialogCalendarView newFragment = FragmentDialogCalendarView.newInstance("From");
        newFragment.show(fragmentManager, "from_dialog");*/
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentDialogCalendarView newFragment = new  FragmentDialogCalendarView(this,"From");
        newFragment.show(fragmentManager, "from_dialog");
    }

    @OnClick(R.id.layoutTo)
    public void onTo(){


//        FragmentTransaction ft = getFragmentManager().beginTransaction();
//        Fragment prev = getFragmentManager().findFragmentByTag("from_dialog");
//        if (prev != null) {
//            ft.remove(prev);
//        }
//        ft.addToBackStack(null);
//
//        // Create and show the dialog.
//        FragmentDialogCalendarView newFragment = FragmentDialogCalendarView.newInstance("To");
//        newFragment.show(ft, "from_dialog");
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentDialogCalendarView newFragment = new  FragmentDialogCalendarView(this,"To");
        newFragment.show(fragmentManager, "to_dialog");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @OnClick(R.id.layoutBack)
    public void onBack(){
        finish();
    }
    void getAllJobs(){
        isConnectd=AppConstants.isConnected(getApplicationContext());
        if(isConnectd){
            customeProgressDialog.showProgress(this);
            DriverApiService driverApiService = DriverApiInstance.getRetrofitInstance().create(DriverApiService.class);
            Call<JobsResponse> callGetAllJobs = driverApiService.getAllJobs(strApiKey,strSessionToken,formatter.format(today).toString());
           // Call<JobsResponse> callGetAllJobs = driverApiService.getAllJobs(strApiKey,strSessionToken,"2021-06-30");
            callGetAllJobs.enqueue(new Callback<JobsResponse>() {
                @Override
                public void onResponse(Call<JobsResponse> call, Response<JobsResponse> response) {
                    customeProgressDialog.hideProgress();
                    JSONObject jsonObjectResponse=null;
                    try{
                        jsonObjectResponse= new JSONObject(new Gson().toJson(response.body()));
                        Log.d("Log","Response "+jsonObjectResponse);

                    }catch (Exception e){
                        Log.d("Log",e.getMessage());
                    }
                    if(response!=null){
                        if(response.body().status==AppConstants.Status.SUCCESS){
                            rvJobs.setVisibility(View.VISIBLE);
                            jobsResponseArrayList=response.body().data;
                            jobsAdapters = new JobsAdapters(getApplicationContext(),jobsResponseArrayList,jobFlag);
                            rvJobs.setAdapter(jobsAdapters);
                            jobsAdapters.notifyDataSetChanged();
                        }else {
                            if(response.body().status==AppConstants.Status.NO_DATA){
                                Toast.makeText(JobListActivity.this, ""+response.body().message, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<JobsResponse> call, Throwable t) {
                    customeProgressDialog.hideProgress();
                    Log.d("Log","error "+t.getMessage());
                }
            });
        }
    }
    void getJobHistory(){
        isConnectd=AppConstants.isConnected(getApplicationContext());
        if(isConnectd){
            customeProgressDialog.showProgress(this);
            DriverApiService driverApiService = DriverApiInstance.getRetrofitInstance().create(DriverApiService.class);
            // Call<JobsResponse> callGetAllJobs = driverApiService.getAllJobs(strApiKey,strSessionToken,formatter.format(today).toString());
            Call<JobsResponse> callGetAllJobs = driverApiService.getJobHistory(strApiKey,strSessionToken);
            callGetAllJobs.enqueue(new Callback<JobsResponse>() {
                @Override
                public void onResponse(Call<JobsResponse> call, Response<JobsResponse> response) {
                    customeProgressDialog.hideProgress();
                    JSONObject jsonObjectResponse=null;
                    try{
                        jsonObjectResponse= new JSONObject(new Gson().toJson(response.body()));
                        Log.d("Log","Response "+jsonObjectResponse);

                    }catch (Exception e){
                        Log.d("Log",e.getMessage());
                    }
                    if(response!=null){
                        if(response.body().status==AppConstants.Status.SUCCESS){
                            jobsResponseArrayList=response.body().data;
                            jobsAdapters = new JobsAdapters(getApplicationContext(),jobsResponseArrayList,jobFlag);
                            rvJobs.setAdapter(jobsAdapters);
                            jobsAdapters.notifyDataSetChanged();
                            rvJobs.setVisibility(View.VISIBLE);
                        }else {
                            if(response.body().status==AppConstants.Status.NO_DATA){
                                rvJobs.setVisibility(View.GONE);
                                Toast.makeText(JobListActivity.this, ""+response.body().message, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<JobsResponse> call, Throwable t) {
                    customeProgressDialog.hideProgress();
                    Log.d("Log","error "+t.getMessage());
                }
            });
        }
    }
    @OnClick(R.id.layoutSearch)
    public void onSerach(){
        strFromDate=tvFrom.getText().toString().trim();
        strToDate = tvTo.getText().toString().trim();
        if(strFromDate.isEmpty()&&strToDate.isEmpty()){
            Toast.makeText(this, "Select Date ..", Toast.LENGTH_SHORT).show();
        }else {
            getFilterJobHistory();
        }
    }

    void getFilterJobHistory(){
        isConnectd=AppConstants.isConnected(getApplicationContext());
        if(isConnectd){
            customeProgressDialog.showProgress(this);
            DriverApiService driverApiService = DriverApiInstance.getRetrofitInstance().create(DriverApiService.class);
            // Call<JobsResponse> callGetAllJobs = driverApiService.getAllJobs(strApiKey,strSessionToken,formatter.format(today).toString());
            Call<JobsResponse> callGetAllJobs = driverApiService.getJobHistoryFilter(strApiKey,strSessionToken,strFromDate,strToDate);
            callGetAllJobs.enqueue(new Callback<JobsResponse>() {
                @Override
                public void onResponse(Call<JobsResponse> call, Response<JobsResponse> response) {
                    customeProgressDialog.hideProgress();
                    JSONObject jsonObjectResponse=null;
                    try{
                        jsonObjectResponse= new JSONObject(new Gson().toJson(response.body()));
                        Log.d("Log","Response "+jsonObjectResponse);

                    }catch (Exception e){
                        Log.d("Log",e.getMessage());
                    }
                    if(response!=null){
                        if(response.body().status==AppConstants.Status.SUCCESS){
                            strFromDate="";
                            strToDate="";
                            rvJobs.setVisibility(View.VISIBLE);
                            jobsResponseArrayList= new ArrayList<>();
                            jobsResponseArrayList=response.body().data;
                            jobsAdapters = new JobsAdapters(getApplicationContext(),jobsResponseArrayList,jobFlag);
                            rvJobs.setAdapter(jobsAdapters);
                            jobsAdapters.notifyDataSetChanged();
                            Toast.makeText(JobListActivity.this, "get Data", Toast.LENGTH_SHORT).show();
                        }else {
                            if(response.body().status==AppConstants.Status.NO_DATA){
                                rvJobs.setVisibility(View.GONE);
                                Toast.makeText(JobListActivity.this, ""+response.body().message, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<JobsResponse> call, Throwable t) {
                    customeProgressDialog.hideProgress();
                    Log.d("Log","error "+t.getMessage());
                }
            });
        }
    }

    @Override
    public void fromDate(Date fromDate) {
        tvFrom.setText(formatter.format(fromDate).toString());
        strFromDate=formatter.format(fromDate).toString();
    }

    @Override
    public void toDate(Date toDate) {
        tvTo.setText(formatter.format(toDate).toString());
        strToDate=formatter.format(toDate).toString();
    }
}