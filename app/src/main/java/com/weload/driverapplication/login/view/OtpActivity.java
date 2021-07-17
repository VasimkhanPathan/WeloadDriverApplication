package com.weload.driverapplication.login.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.weload.driverapplication.R;
import com.weload.driverapplication.api_instance.DriverApiInstance;
import com.weload.driverapplication.api_instance.DriverApiService;
import com.weload.driverapplication.login.model.BaseResponseModel;
import com.weload.driverapplication.util.AppConstants;
import com.weload.driverapplication.util.CustomeProgressDialog;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OtpActivity extends AppCompatActivity {
    @BindView(R.id.etMobileNo)
    EditText etMobileNo;
    @BindView(R.id.layoutBack)
    LinearLayout layoutBack;
    boolean isConnected;
    String apiKey;
    CustomeProgressDialog customeProgressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        ButterKnife.bind(this);
        customeProgressDialog = new CustomeProgressDialog();
        apiKey = AppConstants.Preferences.getStringPreference(getApplicationContext(),AppConstants.Keys.API_KEY,"");
    }
    @OnClick(R.id.btnSubmit)
    public void onSubmint(){
        isConnected = AppConstants.isConnected(getApplicationContext());
        if(isConnected){
            if(AppConstants.validateMobile(etMobileNo.getText().toString())){
                customeProgressDialog.showProgress(OtpActivity.this);
                if(AppConstants.validateMobile(etMobileNo.getText().toString())){
                    DriverApiService driverApiService = DriverApiInstance.getRetrofitInstance().create(DriverApiService.class);
                    Call<BaseResponseModel> callOtp = driverApiService.forgotPassword(apiKey,etMobileNo.getText().toString());
                    callOtp.enqueue(new Callback<BaseResponseModel>() {
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
                                    Intent intent= new Intent(getApplicationContext(),ResetPasswordActivity.class);
                                    intent.putExtra("mobileNo",etMobileNo.getText().toString());
                                    startActivity(intent);
                                }else {
                                    Toast.makeText(OtpActivity.this, ""+response.body().message, Toast.LENGTH_SHORT).show();
                                }
                            }

                        }

                        @Override
                        public void onFailure(Call<BaseResponseModel> call, Throwable t) {
                            Log.d("Log",t.getMessage());
                            customeProgressDialog.hideProgress();
                        }
                    });
                }
            }else {
                etMobileNo.setError("Enter valid number");
            }

        }

    }
    @OnClick(R.id.layoutBack)
    public void onBack(){
        finish();
    }

}