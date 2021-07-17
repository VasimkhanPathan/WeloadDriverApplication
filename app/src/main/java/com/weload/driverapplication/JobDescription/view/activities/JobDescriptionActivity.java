package com.weload.driverapplication.JobDescription.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.weload.driverapplication.JobDescription.model.CompletJobResponse;
import com.weload.driverapplication.JobDescription.model.JobDescriptionRespons;
import com.weload.driverapplication.JobDescription.view.MapActivity;
import com.weload.driverapplication.R;
import com.weload.driverapplication.api_instance.DriverApiInstance;
import com.weload.driverapplication.api_instance.DriverApiService;
import com.weload.driverapplication.home.adapters.AddtionAddressAdapter;
import com.weload.driverapplication.login.model.BaseResponseModel;
import com.weload.driverapplication.util.AppConstants;
import com.weload.driverapplication.util.CustomeProgressDialog;

import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JobDescriptionActivity extends AppCompatActivity {
    @BindView(R.id.tvViewImage)
    TextView tvViewImage;
    @BindView(R.id.tvJobId)
    TextView tvJobId;
    @BindView(R.id.tvPickupDate)
    TextView tvPickupDate;
    @BindView(R.id.tvPickupTime)
    TextView tvPickupTime;
    @BindView(R.id.tvPickupLocation)
    TextView tvPickupLocation;
    @BindView(R.id.tvDropLocation)
    TextView tvDropLocation;
    @BindView(R.id.tvItemDescription)
    TextView tvItemDescription;
    @BindView(R.id.tvDeliveryDescription)
    TextView tvDeliveryDescription;
    @BindView(R.id.expandableListView)
    ExpandableListView expandableListView;
    @BindView(R.id.tvStart)
    TextView tvStartJob;
    @BindView(R.id.tvEndJob)
    TextView tvEndJob;
    @BindView(R.id.rvAdditionLocation)
    RecyclerView rvAddtionalAddress;
    @BindView(R.id.layout_tool)
    RelativeLayout layout_tool;
    @BindView(R.id.toolTitleWhite)
    TextView toolTitleWhite;
    @BindView(R.id.tvCustomerName)
    TextView tvCustomerName;
    @BindView(R.id.layout_File_blue)
    LinearLayout layout_File_blue;
    @BindView(R.id.ivJobStatusWhiteBackground)
    ImageView ivJobStatusWhiteBackground;
    @BindView(R.id.ivMapMarkerWhite)
    ImageView ivMapMarkerWhite;
    @BindView(R.id.ivBackwhite)
    ImageView ivBackwhite;
    @BindView(R.id.ivMapMarkerBlue)
    ImageView ivMapMarkerBlue;
    @BindView(R.id.ivFileWhite)
    ImageView ivFileWhite;
    @BindView(R.id.layoutBack)
    LinearLayout layoutBack;
    @BindView(R.id.tvPickupCall)
    TextView tvPickupCall;
    @BindView(R.id.tvDropCall)
    TextView tvDropCall;
    @BindView(R.id.layoutImage)
    LinearLayout layoutImage;
    @BindView(R.id.layoutAdditonalAddress)
    LinearLayout layoutAdditonalAddress;

    List<String> listDataParent;
    HashMap<String,List<JobDescriptionRespons.Data.Product.Option>> listDataChild;

    String id="",strApiKey="",strSessionToken="",jobID="";
    boolean isConnected=false;
    CustomeProgressDialog customeProgressDialog;
    JobDescriptionRespons jobDescriptionRespons;
    ExpandableListAdapter expandableListAdapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<JobDescriptionRespons.Data.AdditionalLocation> additionalLocationArrayList;
    AddtionAddressAdapter addtionAddressAdapter;
    Dialog dialog;

    public static ArrayList<JobDescriptionRespons.Data.GeoEncodes> geoEncodesArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_description);
        ButterKnife.bind(this);

        layout_tool.setBackgroundColor(Color.parseColor("#ffffff"));
        toolTitleWhite.setText("Job Details");
        toolTitleWhite.setTextColor(Color.parseColor("#255392"));
        ivJobStatusWhiteBackground.setVisibility(View.VISIBLE);
        ivFileWhite.setVisibility(View.GONE);
        ivBackwhite.setVisibility(View.GONE);
        additionalLocationArrayList = new ArrayList<>();
        customeProgressDialog= new CustomeProgressDialog();
        geoEncodesArrayList = new ArrayList<>();
        id= getIntent().getStringExtra("jobToken");
        jobID=getIntent().getStringExtra("jobId");
        Log.d("Log","id "+id);
        strApiKey = AppConstants.Preferences.getStringPreference(getApplicationContext(),AppConstants.Keys.API_KEY,"");
        strSessionToken = AppConstants.Preferences.getStringPreference(getApplicationContext(),AppConstants.Keys.SESSION_TOKEN,"");
        layoutManager = new LinearLayoutManager(getApplicationContext());
        rvAddtionalAddress.setLayoutManager(layoutManager);
        rvAddtionalAddress.setHasFixedSize(false);

        getJobInfoAPi();
    }
    @OnClick(R.id.tvViewImage)
    public void viewImage(){
        final Dialog dialog = new Dialog(this);
        Log.d("Log","url "+jobDescriptionRespons.data.image);
        URL url = null;
        try {
            url = new URL(jobDescriptionRespons.data.image);
        } catch (Exception e) {
            e.printStackTrace();
        }
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.layout_image_view);
        ImageView imageView = dialog.findViewById(R.id.ivImage);
        /*ImageView ivCloseDialog = dialog.findViewById(R.id.ivImageClose);*/


        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;

        Glide.with(getApplicationContext())
                .load(url)
                .into(imageView);
     /*   ivCloseDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });*/
        dialog.show();
    }
    @OnClick(R.id.ivMapMarkerWhite)
    public void onMap(){
//        Intent intent = new Intent(getApplicationContext(), WebviewMapAcitvity.class);
//        intent.putExtra("jobId",jobID);
//        startActivity(intent);
        if(jobDescriptionRespons!=null && jobDescriptionRespons.data.geo_encodes.size()>0){
            Intent intent = new Intent(getApplicationContext(), MapActivity.class);
            intent.putParcelableArrayListExtra("location",jobDescriptionRespons.data.geo_encodes);
            startActivity(intent);
        }


    }
    @OnClick(R.id.tvPickupCall)
    public void onPickupCall(){
        String contact_number=jobDescriptionRespons.data.pickup_phone;
        //if(isPermissionGranted()){
          callAction(contact_number);
        //}
    }
    @OnClick(R.id.tvDropCall)
    public void onDropCall(){
        String contact_number=jobDescriptionRespons.data.drop_phone;
       // if(isPermissionGranted()){
          callAction(contact_number);
      //  }
    }
    @OnClick(R.id.tvStart)
   public void onStartJob(){
        sucessDialog(0);
    }
    @OnClick(R.id.tvEndJob)
    public void callEndJob(){
        viewPayment();
    }
    @OnClick(R.id.layoutBack)
    public void onBack(){
        finish();
    }

    public void getJobInfoAPi(){
        isConnected= AppConstants.isConnected(getApplicationContext());

        if(isConnected){
            customeProgressDialog.showProgress(this);
            Log.d("Log","api ="+strApiKey);
            Log.d("Log","sees ="+strSessionToken);
            Log.d("Log","api ="+id);
            DriverApiService driverApiService = DriverApiInstance.getRetrofitInstance().create(DriverApiService.class);
            Call<JobDescriptionRespons> callGetJobInfo = driverApiService.getJobInfo(strApiKey,strSessionToken,id);
            callGetJobInfo.enqueue(new Callback<JobDescriptionRespons>() {
                @Override
                public void onResponse(Call<JobDescriptionRespons> call, Response<JobDescriptionRespons> response) {
                    JSONObject jsonObjectResponse=null;
                    try{
                        jsonObjectResponse= new JSONObject(new Gson().toJson(response.body()));
                        Log.d("Log","Job descrip "+jsonObjectResponse);

                    }catch (Exception e){
                        Log.d("Log",e.getMessage());
                    }
                    customeProgressDialog.hideProgress();

                    if(response!=null){
                        if(response.body().status==AppConstants.Status.SUCCESS){
                            jobDescriptionRespons = response.body();
                            tvJobId.setText(jobDescriptionRespons.data.jobid);
                            tvCustomerName.setText(jobDescriptionRespons.data.customer_name);
                            tvPickupDate.setText(jobDescriptionRespons.data.pickup_date);
                            tvPickupTime.setText(jobDescriptionRespons.data.pickup_time);
                            tvJobId.setText(jobDescriptionRespons.data.jobid);
                            tvPickupLocation.setText(jobDescriptionRespons.data.pickup_unitno+","+jobDescriptionRespons.data.pickup_address);
                            tvDropLocation.setText(jobDescriptionRespons.data.drop_unitno+","+jobDescriptionRespons.data.drop_address);
                            tvItemDescription.setText(jobDescriptionRespons.data.item_comment);
                            tvDeliveryDescription.setText(jobDescriptionRespons.data.comment);
                            geoEncodesArrayList=jobDescriptionRespons.data.geo_encodes;
                            Log.d("Log","job stat = "+jobDescriptionRespons.data.job_status);
                            if(jobDescriptionRespons.data.image==null||jobDescriptionRespons.data.image.isEmpty()){
                                layoutImage.setVisibility(View.GONE);
                            }else {
                                layoutImage.setVisibility(View.VISIBLE);
                            }
                            if(jobDescriptionRespons.data.job_status==3){
                                tvStartJob.setVisibility(View.GONE);
                                tvEndJob.setVisibility(View.GONE);
                            }else
                            if(jobDescriptionRespons.data.job_status==2){
                                tvEndJob.setVisibility(View.VISIBLE);
                                tvStartJob.setVisibility(View.GONE);
                            }else {
                                tvEndJob.setVisibility(View.GONE);
                                tvStartJob.setVisibility(View.VISIBLE);
                            }
                            additionalLocationArrayList = jobDescriptionRespons.data.additional_location;
                            Log.d("Log","Size = "+additionalLocationArrayList.size());
                            if(additionalLocationArrayList.size()>0){
                                setAddress();
                                layoutAdditonalAddress.setVisibility(View.VISIBLE);
                            }else {
                                layoutAdditonalAddress.setVisibility(View.GONE);
                            }
                           if(jobDescriptionRespons.data.products.size()>0){
                               crateListData();
                           }

                        }
                    }
                }

                @Override
                public void onFailure(Call<JobDescriptionRespons> call, Throwable t) {
                    customeProgressDialog.hideProgress();
                }
            });

        }
    }
    public void crateListData(){
        listDataParent = new ArrayList<>();
        listDataChild = new HashMap<>();
        for(int i=0;i<jobDescriptionRespons.data.products.size();i++){
            listDataParent.add(jobDescriptionRespons.data.products.get(i).product_name);
            for(int j=0;j<jobDescriptionRespons.data.products.get(i).options.size();j++){
                Log.d("Log","data = "+jobDescriptionRespons.data.products.get(i).options.get(j).name);
                //listDataChild.put(listDataParent.get(i), jobDescriptionRespons.data.products.get(i).options);
                addToList(listDataParent.get(i),jobDescriptionRespons.data.products.get(i).options.get(j));
            }


        }
        Log.d("Log","child size = "+listDataChild.size());
        expandableListAdapter = new com.weload.driverapplication.JobDescription.adapter.ExpandableListAdapter(getApplicationContext(),
                listDataParent,listDataChild);
        expandableListView.setAdapter(expandableListAdapter);

    }
    public synchronized void addToList(String mapKey, JobDescriptionRespons.Data.Product.Option myItem) {
        List<JobDescriptionRespons.Data.Product.Option> itemsList = listDataChild.get(mapKey);

        // if list does not exist create it
        if(itemsList == null) {
            itemsList = new ArrayList<JobDescriptionRespons.Data.Product.Option>();
            itemsList.add(myItem);
            listDataChild.put(mapKey, itemsList);
        } else {
            // add if item is not already in list
            if(!itemsList.contains(myItem)) itemsList.add(myItem);
        }
    }

    public void endJob(String strAmount,String strDriverNote){
        isConnected= AppConstants.isConnected(getApplicationContext());
        if(isConnected){
            customeProgressDialog.showProgress(this);
            DriverApiService driverApiService = DriverApiInstance.getRetrofitInstance().create(DriverApiService.class);
            Call<CompletJobResponse> callCompleteJob = driverApiService.completeJob(strApiKey,strSessionToken,
                    "completed",strAmount,strDriverNote,id);
            Log.d("Log","apikay "+strApiKey);
            Log.d("Log","strSessionToken "+strSessionToken);
            Log.d("Log","completed ");
            Log.d("Log","strAmount "+strAmount);
            Log.d("Log","strDriverNote "+strDriverNote);
            Log.d("Log","id "+id);
            callCompleteJob.enqueue(new Callback<CompletJobResponse>() {
                @Override
                public void onResponse(Call<CompletJobResponse> call, Response<CompletJobResponse> response) {
                    JSONObject jsonObjectResponse=null;
                    try{
                        jsonObjectResponse= new JSONObject(new Gson().toJson(response.body()));
                        Log.d("Log","Response "+jsonObjectResponse);

                    }catch (Exception e){
                        Log.d("Log",e.getMessage());
                    }
                    customeProgressDialog.hideProgress();
                    if(response.body()!=null){
                        if(response.body().status==AppConstants.Status.SUCCESS){
                         //   PaymentSuccessFragment paymentSuccessFragment = new PaymentSuccessFragment(JobDescriptionFragment.this);
                          /*  fragmentManager = getFragmentManager();
                            fragmentTransaction = fragmentManager.beginTransaction();
                            Bundle bundle = new Bundle();
                            bundle.putParcelable("paymentData",response.body().data);
                            paymentSuccessFragment.setArguments(bundle);
                            fragmentTransaction.replace(R.id.fragment_container, paymentSuccessFragment).addToBackStack(null).commit();*/
                            Intent intent = new Intent(getApplicationContext(),PaymentSuccessActivity.class);
                            intent.putExtra("paymentData",response.body().data);
                            startActivity(intent);
                        }else {
                            Toast.makeText(getApplicationContext(), "Try Again..", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<CompletJobResponse> call, Throwable t) {
                    customeProgressDialog.hideProgress();
                    Toast.makeText(getApplicationContext(), "Try Again..", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    public void viewPayment(){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_payment);
        dialog.setCancelable(false);
        TextView tvTotalAmount = dialog.findViewById(R.id.tvTotalAmount);
        TextView tvDepositedAmount= dialog.findViewById(R.id.tvDepositedAmount);
        EditText etAmount= dialog.findViewById(R.id.etAmount);
        EditText etDriverNote= dialog.findViewById(R.id.etDriverNote);
        Button btnSubmit = dialog.findViewById(R.id.btnSubmit);
        Button btnCancel = dialog.findViewById(R.id.btnCancel);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;
        Log.d("Log","totla amoutn = "+jobDescriptionRespons.data.total_amount);
        tvTotalAmount.setText("Total Amount $ "+jobDescriptionRespons.data.total_amount);
        tvDepositedAmount.setText("Deposited Amount $ "+jobDescriptionRespons.data.paid_amount);
        etAmount.setText(jobDescriptionRespons.data.tobe_paid_amount);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etAmount.getText().toString()==null || etAmount.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), "Enter Amount..", Toast.LENGTH_SHORT).show();
                    etAmount.setError("Enter Amount..");
                }else if(etDriverNote.getText().toString()==null || etDriverNote.getText().toString().isEmpty()){
                    etDriverNote.setError("Enter Note..");
                }else {
                    endJob(etAmount.getText().toString().trim(),etDriverNote.getText().toString());
                    dialog.dismiss();
                }

            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
    public void sucessDialog(int flag){
          dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_job_started);
        dialog.setCancelable(false);

        Button btnSubmit = dialog.findViewById(R.id.btnSubmit);
        Button btnStart = dialog.findViewById(R.id.btnStart);

        TextView tvMessaage = dialog.findViewById(R.id.tvMessage);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;
        if(flag==0){
            btnStart.setVisibility(View.VISIBLE);
            tvMessaage.setText("You are about to start the job.");
        }else {
            btnStart.setVisibility(View.GONE);
            tvMessaage.setText("Job Started Sucessfully!!");
        }

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();

            }
        });
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startJob();
            }
        });

        dialog.show();
    }

    public void callAction(String number){
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setPackage("com.android.server.telecom");
        callIntent.setData(Uri.parse("tel:" + number));
        startActivity(callIntent);
    }

    public void setAddress(){

        addtionAddressAdapter = new AddtionAddressAdapter(getApplicationContext(),additionalLocationArrayList);
        rvAddtionalAddress.setAdapter(addtionAddressAdapter);
        addtionAddressAdapter.notifyDataSetChanged();
    }

    public void startJob(){
        isConnected= AppConstants.isConnected(getApplicationContext());
        dialog.dismiss();
        if(isConnected){
            customeProgressDialog.showProgress(this);
            DriverApiService driverApiService = DriverApiInstance.getRetrofitInstance().create(DriverApiService.class);
            Call<BaseResponseModel> callStartJob = driverApiService.startJob(strApiKey,strSessionToken,"started",id);
            callStartJob.enqueue(new Callback<BaseResponseModel>() {
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
                    if(response.body()!=null){
                        if(response.body().status==AppConstants.Status.SUCCESS){
                            sucessDialog(1);
                            tvStartJob.setVisibility(View.GONE);
                            tvEndJob.setVisibility(View.VISIBLE);
                        }else {
                            Toast.makeText(getApplicationContext(), ""+response.body().message, Toast.LENGTH_SHORT).show();
                        }
                    }


                }

                @Override
                public void onFailure(Call<BaseResponseModel> call, Throwable t) {
                    customeProgressDialog.hideProgress();
                    Toast.makeText(getApplicationContext(), "Try Again..", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}