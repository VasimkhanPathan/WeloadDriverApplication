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

public class ResetPasswordActivity extends AppCompatActivity {

    @BindView(R.id.etMobileNo)
    EditText etMobileNo;
    @BindView(R.id.etOtp)
    EditText etOtp;
    @BindView(R.id.etNewPassword)
    EditText etNewPassword;
    @BindView(R.id.etConfirmPassword)
    EditText etConfirmPassword;
    @BindView(R.id.layoutBack)
    LinearLayout layoutBack;
    boolean isConnected;
    String apiKey;
    CustomeProgressDialog customeProgressDialog;
    String strMobileNo="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        ButterKnife.bind(this);
        strMobileNo = getIntent().getStringExtra("mobileNo");
        etMobileNo.setText(strMobileNo);
        customeProgressDialog = new CustomeProgressDialog();
        apiKey = AppConstants.Preferences.getStringPreference(getApplicationContext(),AppConstants.Keys.API_KEY,"");

    }
    @OnClick(R.id.btnSubmit)
    public void onSubmit(){
        isConnected = AppConstants.isConnected(getApplicationContext());
        if(validate()){

            if(isConnected){
                customeProgressDialog.showProgress(ResetPasswordActivity.this);
                DriverApiService driverApiService = DriverApiInstance.getRetrofitInstance().create(DriverApiService.class);
                Call<BaseResponseModel> callRestPassword = driverApiService.resetPassword(apiKey,
                        etMobileNo.getText().toString(),etOtp.getText().toString(),
                        etNewPassword.getText().toString(),etConfirmPassword.getText().toString());
                callRestPassword.enqueue(new Callback<BaseResponseModel>() {
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
                            if(response.body().status == AppConstants.Status.SUCCESS){
                                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                                startActivity(intent);
                                finish();
                            }else {
                                Toast.makeText(ResetPasswordActivity.this, ""+response.body().message, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseResponseModel> call, Throwable t) {
                        customeProgressDialog.hideProgress();
                        Log.d("Log",t.getMessage());
                    }
                });
            }
        }
    }

    private boolean validate(){
        if(AppConstants.validateMobile(etMobileNo.getText().toString()) &&
        AppConstants.validatePassword(etNewPassword.getText().toString())&&
                AppConstants.validatePassword(etConfirmPassword.getText().toString())&&
                        AppConstants.validatePassword(etOtp.getText().toString())){
            return true;
        }else {
            return false;
        }
    }
    @OnClick(R.id.layoutBack)
    public void onBack(){
        finish();
    }
}