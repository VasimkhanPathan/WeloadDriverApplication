package com.weload.driverapplication.login.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.weload.driverapplication.R;
import com.weload.driverapplication.api_instance.DriverApiInstance;
import com.weload.driverapplication.api_instance.DriverApiService;
import com.weload.driverapplication.home.HomeActivity;
import com.weload.driverapplication.login.model.BaseResponseModel;
import com.weload.driverapplication.login.model.LoginResponseModel;
import com.weload.driverapplication.util.AppConstants;
import com.weload.driverapplication.util.CustomeProgressDialog;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.btnLogin)
    Button btnLogin;
    @BindView(R.id.etMobileNo)
    EditText etMobileNo;
    @BindView(R.id.etPassword)
    EditText etPassword;
    @BindView(R.id.tvForgotPassword)
    TextView tvForgotPassword;
    Boolean isConnected;
    String apiKey;
    String sFcmToken="FH";
    CustomeProgressDialog progressDialog;
    LoginResponseModel loginResponseModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        progressDialog = new CustomeProgressDialog();
        AppConstants.Preferences.setStringPreferences(getApplicationContext(),AppConstants.Keys.API_KEY,"TVBBMndJNDFZWHRVZUdtOWtMbzZUOHVjYm1IQ2NPR3R3SGFGb25Pa1lCRT0=");
        apiKey = AppConstants.Preferences.getStringPreference(getApplicationContext(),AppConstants.Keys.API_KEY,"");
        getToken();

    }
    @OnClick(R.id.btnLogin)
    public void onLogin(){
        if(validation(etMobileNo.getText().toString(),etPassword.getText().toString())){

            isConnected = AppConstants.isConnected(getApplicationContext());
            if(isConnected){
                progressDialog.showProgress(LoginActivity.this);
                DriverApiService driverApiService = DriverApiInstance.getRetrofitInstance().create(DriverApiService.class);
                Call<LoginResponseModel> callLogin = driverApiService.Login(apiKey,etMobileNo.getText().toString(),
                        etPassword.getText().toString());
                callLogin.enqueue(new Callback<LoginResponseModel>() {
                    @Override
                    public void onResponse(Call<LoginResponseModel> call, Response<LoginResponseModel> response) {
                        progressDialog.hideProgress();
                        JSONObject jsonObjectResponse=null;
                        try{
                            jsonObjectResponse= new JSONObject(new Gson().toJson(response.body()));
                            Log.d("Log","Response "+jsonObjectResponse);
                            Toast.makeText(LoginActivity.this, ""+jsonObjectResponse.get("message"), Toast.LENGTH_SHORT).show();
                        }catch (Exception e){
                            Log.d("Log",e.getMessage());
                        }
                        if(response.body()!=null){

                           if(response.body().status==AppConstants.Status.SUCCESS){
                               loginResponseModel=response.body();

                               registerToken(loginResponseModel.data.session_token);
                           }else {
                               Toast.makeText(LoginActivity.this, ""+response.body().message, Toast.LENGTH_SHORT).show();
                           }
                        }


                    }

                    @Override
                    public void onFailure(Call<LoginResponseModel> call, Throwable t) {
                        progressDialog.hideProgress();
                        Toast.makeText(LoginActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }else {
            Toast.makeText(this, "not validate", Toast.LENGTH_SHORT).show();
        }

    }
    @OnClick(R.id.tvForgotPassword)
    public void tvForgotPassword(){
        Intent intent = new Intent(getApplicationContext(),OtpActivity.class);
        startActivity(intent);
    }
    boolean validation(String strMobile,String password){
        if(validateMobile(strMobile) && validatePassword(password)){
            return true;
        }else {

            return  false;
        }
    }
    boolean validateMobile(String strMobileNo){
        if(strMobileNo!=null && strMobileNo!=""){
            if(strMobileNo.length()>=8 && strMobileNo.length()<=10){
                return  true;
            }else {
                etMobileNo.setError("Invalid Mobile Number");
                return false;
            }
        }else{
            etMobileNo.setError("Invalid Mobile Number");
            return false;
        }

    }
    boolean validatePassword(String strPassword){
        if(strPassword!=null && !strPassword.isEmpty()){
            return true;
        }else
            etPassword.setError("enter valid password");
            return  false;
    }

 public void getToken(){
     FirebaseMessaging.getInstance().getToken()
             .addOnCompleteListener(new OnCompleteListener<String>() {
                 @Override
                 public void onComplete(@NonNull Task<String> task) {
                     if (!task.isSuccessful()) {
                      //   Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                         Toast.makeText(LoginActivity.this, ""+task.getException(), Toast.LENGTH_SHORT).show();
                         return;
                     }

                     // Get new FCM registration token
                     String token = task.getResult();
                     AppConstants.Preferences.setStringPreferences(getApplicationContext(),AppConstants.Keys.FIREBASE_TOKEN,token);
                     // Log and toast
                   //  String msg = getString(R.string.msg_token_fmt, token);
                  //   Log.d(TAG, msg);
                     sFcmToken = AppConstants.Preferences.getStringPreference(getApplicationContext(),
                             AppConstants.Keys.FIREBASE_TOKEN,"");
                   /*  Toast.makeText(getApplicationContext(), ""+AppConstants.Preferences.getStringPreference(getApplicationContext(),
                             AppConstants.Keys.FIREBASE_TOKEN,""), Toast.LENGTH_SHORT).show();
                     Toast.makeText(getApplicationContext(), token, Toast.LENGTH_SHORT).show();*/
                     Log.d("Log","Token="+sFcmToken);
                 }
             });
 }
 public void registerToken(String sessionToken){
     isConnected = AppConstants.isConnected(getApplicationContext());
     if(isConnected){
         progressDialog.showProgress(LoginActivity.this);
         DriverApiService driverApiService = DriverApiInstance.getRetrofitInstance().create(DriverApiService.class);
         Call<BaseResponseModel> callLogin = driverApiService.registerToken(apiKey,sessionToken,sFcmToken);
        callLogin.enqueue(new Callback<BaseResponseModel>() {
            @Override
            public void onResponse(Call<BaseResponseModel> call, Response<BaseResponseModel> response) {
                progressDialog.hideProgress();
                if(response!=null){
                    if(response.body().status==AppConstants.Status.SUCCESS){
                        AppConstants.Preferences.setStringPreferences(getApplicationContext(),AppConstants.Keys.SESSION_TOKEN,
                                loginResponseModel.data.session_token);
                        AppConstants.Preferences.setStringPreferences(getApplicationContext(),AppConstants.Keys.DRIVE_ID,
                                loginResponseModel.data.id);
                        AppConstants.Preferences.setStringPreferences(getApplicationContext(),AppConstants.Keys.DRIVE_NAME,
                                loginResponseModel.data.name);
                        AppConstants.Preferences.setStringPreferences(getApplicationContext(),AppConstants.Keys.DRIVE_MOBILE,
                                loginResponseModel.data.mobile);
                        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                        startActivity(intent);
                    }else {
                        Toast.makeText(LoginActivity.this, "Please try agin..", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);
                    }
                    finish();
                }
            }

            @Override
            public void onFailure(Call<BaseResponseModel> call, Throwable t) {
                progressDialog.hideProgress();
            }
        });
     }
 }
}