package com.weload.driverapplication.JobDescription.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.weload.driverapplication.JobDescription.adapter.WalletAdapter;

import com.weload.driverapplication.JobDescription.model.WalletResponse;
import com.weload.driverapplication.R;
import com.weload.driverapplication.api_instance.DriverApiInstance;
import com.weload.driverapplication.api_instance.DriverApiService;
import com.weload.driverapplication.home.interfaces.ChageDate;
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

public class WalletActivity extends AppCompatActivity implements ChageDate {
    private ArrayList<WalletResponse.Datum> walletModelArrayList;
    @BindView(R.id.rvWallet)
    RecyclerView rvWallet;

    @BindView(R.id.tvFrom)
    TextView tvFrom;
    @BindView(R.id.tvTo)
    TextView tvTo;

    @BindView(R.id.layoutFrom)
    LinearLayout layoutFrom;
    @BindView(R.id.layoutTo)
    LinearLayout layoutTo;

   /* @BindView(R.id.spinner2)
    Spinner spTransaction;*/
    @BindView(R.id.ivJobStatusWhiteBackground)
    ImageView ivJobStatusWhiteBackground;
    @BindView(R.id.ivFileWhite)
    ImageView ivFileWhite;
    RecyclerView.LayoutManager layoutManager;
    WalletAdapter walletAdapter;

    @BindView(R.id.ivMapMarkerBlue)
    ImageView ivMapMarkerBlue;
    @BindView(R.id.ivMapMarkerWhite)
    ImageView ivMapMarkerWhite;
    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.ivBackwhite)
    ImageView ivBackwhite;
    @BindView(R.id.layoutBack)
    LinearLayout layoutBack;
    @BindView(R.id.toolTitleWhite)
    TextView toolTitleWhite;
    @BindView(R.id.tvCollection)
    TextView tvCollection;
    @BindView(R.id.tvDeleviedJob)
    TextView tvDeleviedJob;
    @BindView(R.id.tvCashCollected)
    TextView tvCashCollected;
    @BindView(R.id.tvTobeCollected)
    TextView tvTobeCollected;
    @BindView(R.id.layoutSearch)
    LinearLayout layoutSearch;
    Date today;
    SimpleDateFormat formatter;
    String strFromDate="",strToDate="";
    String id="",strApiKey="",strSessionToken="";
    boolean isConnected=false;
    CustomeProgressDialog customeProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);
        ButterKnife.bind(this);
        customeProgressDialog= new CustomeProgressDialog();
        ivJobStatusWhiteBackground.setVisibility(View.GONE);
        ivFileWhite.setVisibility(View.GONE);
        ivMapMarkerBlue.setVisibility(View.GONE);
        ivMapMarkerWhite.setVisibility(View.GONE);
        ivBack.setVisibility(View.GONE);
        ivBackwhite.setVisibility(View.VISIBLE);
        toolTitleWhite.setText("Wallet");
        toolTitleWhite.setTextColor(Color.parseColor("#ffffff"));
        formatter = new SimpleDateFormat("yyyy-MM-dd");

        strApiKey = AppConstants.Preferences.getStringPreference(getApplicationContext(),AppConstants.Keys.API_KEY,"");
        strSessionToken = AppConstants.Preferences.getStringPreference(getApplicationContext(),AppConstants.Keys.SESSION_TOKEN,"");
        String[] years = {"Last 15 Tran. ","Last Month Trans.","Last 3 Months Trans"};
        ArrayAdapter<CharSequence> langAdapter = new ArrayAdapter<CharSequence>(getApplicationContext(), R.layout.spinner_text, years );
        langAdapter.setDropDownViewResource(R.layout.simple_spinner_layout);
       // spTransaction.setAdapter(langAdapter);
        getWallet();
        layoutManager = new LinearLayoutManager(getApplicationContext());
        rvWallet.setLayoutManager(layoutManager);

    }
    @OnClick(R.id.layoutFrom)
    public void onClick(){


     /*   FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag("from_dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        // Create and show the dialog.
        FragmentDialogCalendarView newFragment = FragmentDialogCalendarView.newInstance("From");
        newFragment.show(ft, "from_dialog");*/
     /*   FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentDialogCalendarView newFragment = FragmentDialogCalendarView.newInstance("To");
        newFragment.show(fragmentManager, "to_dialog");*/
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentDialogCalendarView newFragment = new  FragmentDialogCalendarView(this,"From");
        newFragment.show(fragmentManager, "from_dialog");
    }

    @OnClick(R.id.layoutTo)
    public void onTo(){

       // FragmentManager fragmentManager = getSupportFragmentManager();
       /* FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag("from_dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);*/

        // Create and show the dialog.
//        FragmentDialogCalendarView newFragment = FragmentDialogCalendarView.newInstance("To");
//        newFragment.show(fragmentManager, "to_dialog");
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentDialogCalendarView newFragment = new  FragmentDialogCalendarView(this,"To");
        newFragment.show(fragmentManager, "to_dialog");
    }
    @OnClick(R.id.layoutBack)
    public void onBack(){
        finish();
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
    @OnClick(R.id.layoutSearch)
    public void onSerach(){
        strFromDate=tvFrom.getText().toString().trim();
        strToDate = tvTo.getText().toString().trim();
        if(strFromDate.isEmpty()&&strToDate.isEmpty()){
            Toast.makeText(this, "Select Date ..", Toast.LENGTH_SHORT).show();
        }else {
            getWallet();
        }
    }
    void getWallet(){
        isConnected= AppConstants.isConnected(getApplicationContext());
        if(isConnected){
            customeProgressDialog.showProgress(this);
            DriverApiService driverApiService = DriverApiInstance.getRetrofitInstance().create(DriverApiService.class);
            Call<WalletResponse> callWallet = driverApiService.getWallet(strApiKey,strSessionToken,strFromDate,strToDate,"");
            callWallet.enqueue(new Callback<WalletResponse>() {
                @Override
                public void onResponse(Call<WalletResponse> call, Response<WalletResponse> response) {
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
                            walletModelArrayList = response.body().data;
                          //  tvCollection.setText(response.body().data.);
                            if(walletModelArrayList.isEmpty()){
                                Toast.makeText(WalletActivity.this, "No Data Found", Toast.LENGTH_SHORT).show();
                                rvWallet.setVisibility(View.GONE);
                                tvCollection.setText("0");
                            }else {
                                tvCollection.setText("$"+response.body().totalamount);
                                tvTobeCollected.setText("$"+response.body().dues);
                                tvCashCollected.setText("$"+response.body().drivertotal);
                                tvDeleviedJob.setText(response.body().totaltrans+" Job Delivered ");
                                rvWallet.setVisibility(View.VISIBLE);
                                walletAdapter = new WalletAdapter(getApplicationContext(),walletModelArrayList);
                                rvWallet.setAdapter(walletAdapter);
                                walletAdapter.notifyDataSetChanged();
                            }

                        }
                    }
                }

                @Override
                public void onFailure(Call<WalletResponse> call, Throwable t) {
                    customeProgressDialog.hideProgress();
                    Log.d("Log","error "+t.getMessage());
                }
            });

        }
    }
}