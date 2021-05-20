package com.vasim.driverapplication.home.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.vasim.driverapplication.R;
import com.vasim.driverapplication.home.HomeActivity;
import com.vasim.driverapplication.home.interfaces.ChangesInActivies;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class HomeFragement extends Fragment {


    @BindView(R.id.layoutJobs)
    LinearLayout layoutJobs;
    @BindView(R.id.layoutWallet)
    LinearLayout layoutWallet;
    @BindView(R.id.layoutJobHistory)
    LinearLayout layoutJobHistory;

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
        return view;
    }
    @OnClick(R.id.layoutJobs)
    public void onJobClick(){
       // Toast.makeText(getContext(), "Jobs", Toast.LENGTH_SHORT).show();
        changesInActivies.changesInActivity("jobs");
    }
    @OnClick(R.id.layoutWallet)
    public void onWallet(){
        // Toast.makeText(getContext(), "Jobs", Toast.LENGTH_SHORT).show();
        changesInActivies.changesInActivity("wallet");
    }
    @OnClick(R.id.layoutJobHistory)
    public void onJobHistory(){
        // Toast.makeText(getContext(), "Jobs", Toast.LENGTH_SHORT).show();
        changesInActivies.changesInActivity("history");
    }
}