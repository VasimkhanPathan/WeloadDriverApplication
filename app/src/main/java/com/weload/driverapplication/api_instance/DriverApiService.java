package com.weload.driverapplication.api_instance;

import com.weload.driverapplication.JobDescription.model.CompletJobResponse;
import com.weload.driverapplication.JobDescription.model.JobDescriptionRespons;
import com.weload.driverapplication.JobDescription.model.WalletResponse;
import com.weload.driverapplication.home.model.JobCountResponse;
import com.weload.driverapplication.home.model.JobsResponse;
import com.weload.driverapplication.login.model.BaseResponseModel;
import com.weload.driverapplication.login.model.LoginResponseModel;
import com.weload.driverapplication.util.AppConstants;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface DriverApiService {
    @FormUrlEncoded
    @POST(AppConstants.ApiNames.LOGIN)
    Call<LoginResponseModel> Login(@Field("apikey") String sApikey,
                                   @Field("username") String sUserName,
                                   @Field("token") String sToken);

    @FormUrlEncoded
    @POST(AppConstants.ApiNames.REGISTER_FCM_TOKEN)
    Call<BaseResponseModel> registerToken(@Field("apikey") String sApikey,
                                          @Field("session_token") String session_token,
                                   @Field("fmctoken") String fcmToken);

    @FormUrlEncoded
    @POST(AppConstants.ApiNames.FORGOT_PASSWORD)
    Call<BaseResponseModel> forgotPassword(@Field("apikey") String sApikey,
                                  @Field("mobileno") String sUserName);

    @FormUrlEncoded
    @POST(AppConstants.ApiNames.RESET_PASSWORD)
    Call<BaseResponseModel> resetPassword(@Field("apikey") String sApikey,
                                           @Field("mobileno") String sUserName,
                                           @Field("otp") String sOtp,
                                           @Field("newpass") String sNewpass,
                                          @Field("cnewpass") String cNewpass);

    @FormUrlEncoded
    @POST(AppConstants.ApiNames.GET_ALL_JOBS)
    Call<JobsResponse> getAllJobs(@Field("apikey") String sApikey,
                                     @Field("session_token") String session_token,
                                     @Field("date") String date);
    @FormUrlEncoded
    @POST(AppConstants.ApiNames.GET_JOB_INFO)
    Call<JobDescriptionRespons> getJobInfo(@Field("apikey") String sApikey,
                                           @Field("session_token") String session_token,
                                           @Field("job_token") String job_token);

    @FormUrlEncoded
    @POST(AppConstants.ApiNames.START_JOB)
    Call<BaseResponseModel> startJob(@Field("apikey") String sApikey,
                                           @Field("session_token") String session_token,
                                           @Field("status") String status,
                                           @Field("job_token") String job_token);
    @FormUrlEncoded
    @POST(AppConstants.ApiNames.Job_COMPLETE)
    Call<CompletJobResponse> completeJob(@Field("apikey") String sApikey,
                                         @Field("session_token") String session_token,
                                         @Field("status") String status,
                                         @Field("amount_collected") String amount_collected,
                                         @Field("driver_note") String driver_note,
                                         @Field("job_token") String job_token);
    @FormUrlEncoded
    @POST(AppConstants.ApiNames.JOB_HISTORY)
    Call<JobsResponse> getJobHistory(@Field("apikey") String sApikey,
                                           @Field("session_token") String session_token);
    @FormUrlEncoded
    @POST(AppConstants.ApiNames.JOB_HISTORY_FILTER)
    Call<JobsResponse> getJobHistoryFilter(@Field("apikey") String sApikey,
                                     @Field("session_token") String session_token,
                                           @Field("job_start_date") String job_start_date,
                                           @Field("job_end_date") String job_end_date);

    @FormUrlEncoded
    @POST(AppConstants.ApiNames.JOB_TRANSCATION)
    Call<WalletResponse> getWallet(@Field("apikey") String sApikey,
                                             @Field("session_token") String session_token,
                                             @Field("trans_start_date") String trans_start_date,
                                             @Field("trans_end_date") String trans_end_date ,
                                   @Field("transation_set") String transation_set);
    @FormUrlEncoded
    @POST(AppConstants.ApiNames.JOB_COUNT)
    Call<JobCountResponse> getJobCount(@Field("apikey") String sApikey,
                                       @Field("session_token") String session_token
    );
    @FormUrlEncoded
    @POST(AppConstants.ApiNames.LEAVE_APPLICATION)
    Call<BaseResponseModel> leaveApplication(@Field("apikey") String sApikey,
                                           @Field("session_token") String session_token,
                                           @Field("form_date") String form_date,
                                           @Field("to_date") String to_date,
                                           @Field("leave_note") String leave_note);

}
