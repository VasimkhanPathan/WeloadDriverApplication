package com.vasim.driverapplication.JobDescription.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.vasim.driverapplication.R;
import com.vasim.driverapplication.home.interfaces.ChangesInActivies;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PaymentSuccessFragment extends Fragment {

    @BindView(R.id.ivImageClose)
    ImageView ivClose;
    ChangesInActivies changesInActivies;

    public PaymentSuccessFragment(ChangesInActivies changesInActivies) {
        this.changesInActivies = changesInActivies;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_payment_success, container, false);
        ButterKnife.bind(this,view);

        return view;
    }
    @OnClick(R.id.ivImageClose)
    public void onClose(){
        changesInActivies.changesInActivity("paymentSucess");
    }
}