package com.weload.driverapplication.home.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.weload.driverapplication.JobDescription.view.activities.WalletActivity;
import com.weload.driverapplication.R;
import com.weload.driverapplication.api_instance.DriverApiInstance;
import com.weload.driverapplication.api_instance.DriverApiService;
import com.weload.driverapplication.home.interfaces.ChangesInActivies;
import com.weload.driverapplication.home.model.JobCountResponse;
import com.weload.driverapplication.home.view.activites.JobListActivity;
import com.weload.driverapplication.util.AppConstants;
import com.weload.driverapplication.util.CustomeProgressDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragement extends Fragment {


    @BindView(R.id.layoutJobs)
    LinearLayout layoutJobs;
    @BindView(R.id.layoutWallet)
    LinearLayout layoutWallet;
    @BindView(R.id.layoutJobHistory)
    LinearLayout layoutJobHistory;

    String jobFlag="",strApiKey="",strSessionToken="";
    boolean isConnectd=false;
    CustomeProgressDialog customeProgressDialog;
    @BindView(R.id.tvPeningJob)
    TextView tvPendingJob;
    @BindView(R.id.tvOngoingJob)
    TextView tvOngoingJob;
    @BindView(R.id.tvViewall)
    TextView tvViewAll;
    @BindView(R.id.tvCompletedJob)
            TextView tvCompletedJob;
    ChangesInActivies changesInActivies;
    public HomeFragement(ChangesInActivies changesInActivies){
        this.changesInActivies = changesInActivies;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_fragement, container, false);
        ButterKnife.bind(this,view);
        customeProgressDialog = new CustomeProgressDialog();
        strApiKey = AppConstants.Preferences.getStringPreference(getContext(),AppConstants.Keys.API_KEY,"");
        strSessionToken = AppConstants.Preferences.getStringPreference(getContext(),AppConstants.Keys.SESSION_TOKEN,"");
        getDriverJobCount();
        return view;
    }
    @OnClick(R.id.layoutJobs)
    public void onJobClick(){
       // Toast.makeText(getContext(), "Jobs", Toast.LENGTH_SHORT).show();
       // changesInActivies.changesInActivity("jobs","");
        Intent intent = new Intent(getContext(), JobListActivity.class);
        intent.putExtra("pageFlag","job");
        startActivity(intent);
    }
    @OnClick(R.id.tvViewall)
    public void onViewAll(){
        // Toast.makeText(getContext(), "Jobs", Toast.LENGTH_SHORT).show();
        // changesInActivies.changesInActivity("jobs","");
        Intent intent = new Intent(getContext(), JobListActivity.class);
        intent.putExtra("pageFlag","job");
        startActivity(intent);
    }
    @OnClick(R.id.layoutWallet)
    public void onWallet(){
        // Toast.makeText(getContext(), "Jobs", Toast.LENGTH_SHORT).show();
        //changesInActivies.changesInActivity("wallet","");
        Intent intent = new Intent(getContext(), WalletActivity.class);
        startActivity(intent);
    }
    @OnClick(R.id.layoutJobHistory)
    public void onJobHistory(){
        // Toast.makeText(getContext(), "Jobs", Toast.LENGTH_SHORT).show();
       // changesInActivies.changesInActivity("history","");
        Intent intent = new Intent(getContext(), JobListActivity.class);
        intent.putExtra("pageFlag","job_history");
        startActivity(intent);
    }

    public void getDriverJobCount(){
        isConnectd= AppConstants.isConnected(getContext());
        customeProgressDialog.showProgress(HomeFragement.this.getContext());
        DriverApiService driverApiService = DriverApiInstance.getRetrofitInstance().create(DriverApiService.class);
        Call<JobCountResponse> callGetJobCount = driverApiService.getJobCount(strApiKey,strSessionToken);
        callGetJobCount.enqueue(new Callback<JobCountResponse>() {
            @Override
            public void onResponse(Call<JobCountResponse> call, Response<JobCountResponse> response) {
                customeProgressDialog.hideProgress();
                if(response!=null){
                    if(response.body().status==AppConstants.Status.SUCCESS){
                        tvCompletedJob.setText(response.body().data.completed);
                        tvOngoingJob.setText(response.body().data.ongoing);
                        tvPendingJob.setText(response.body().data.pending);
                    }
                }
            }

            @Override
            public void onFailure(Call<JobCountResponse> call, Throwable t) {
                customeProgressDialog.hideProgress();
                Log.d("Log","error "+t.getMessage());
            }
        });
    }
}