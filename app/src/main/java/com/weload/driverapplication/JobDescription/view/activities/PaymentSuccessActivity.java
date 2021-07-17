package com.weload.driverapplication.JobDescription.view.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.weload.driverapplication.JobDescription.model.CompletJobResponse;
import com.weload.driverapplication.R;
import com.weload.driverapplication.home.HomeActivity;
import com.weload.driverapplication.home.interfaces.ChangesInActivies;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PaymentSuccessActivity extends AppCompatActivity {
    @BindView(R.id.ivImageClose)
    ImageView ivClose;
    CompletJobResponse.Data pyamentData;
    ChangesInActivies changesInActivies;
    @BindView(R.id.tvJobId)
    TextView tvJobId;
    @BindView(R.id.tvPickupDate)
    TextView tvJobDate;
    @BindView(R.id.tvTotalAmount)
    TextView tvTotalAmount;
    @BindView(R.id.tvDepositedAmount)
    TextView tvDepositedAmount;
    @BindView(R.id.tvCOD)
    TextView tvCOD;
    @BindView(R.id.tvExtraAmount)
    TextView tvExtraAmount;
    @BindView(R.id.layoutExtraAmount)
    LinearLayout layoutExtraAmount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_success);
        ButterKnife.bind(this);
        // pyamentData= (CompletJobResponse.Data) getArguments().getSerializable("paymentData");
        pyamentData = getIntent().getParcelableExtra("paymentData");
        tvJobId.setText(pyamentData.jobid);
        tvJobDate.setText(pyamentData.job_date);
        tvTotalAmount.setText(pyamentData.total_amount);
        tvDepositedAmount.setText(pyamentData.deposite);
        tvCOD.setText(pyamentData.cod);
        if(pyamentData.eaxtra_amount!=null&&!pyamentData.eaxtra_amount.equals("")){
            layoutExtraAmount.setVisibility(View.VISIBLE);
            tvExtraAmount.setText(pyamentData.eaxtra_amount);
        }
    }
    @OnClick(R.id.ivImageClose)
    public void onClose(){
       callHome();
    }
    private void callHome(){
        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        callHome();
    }
}