package com.vasim.driverapplication.home.view.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.vasim.driverapplication.JobDescription.JobDescriptionFragment;
import com.vasim.driverapplication.R;
import com.vasim.driverapplication.home.HomeActivity;
import com.vasim.driverapplication.home.adapters.JobsAdapters;
import com.vasim.driverapplication.home.interfaces.ChangesInActivies;
import com.vasim.driverapplication.home.model.JobsResponse;
import com.vasim.driverapplication.home.view.HomeFragement;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class JobsFragment extends Fragment implements ChangesInActivies {
    @BindView(R.id.rvJobs)
    RecyclerView rvJobs;
    @BindView(R.id.topDivider)
    TableRow divider;
    @BindView(R.id.top)
    LinearLayout layoutTop;

    @BindView(R.id.tvFrom)
    TextView tvFrom;
    @BindView(R.id.tvTo)
    TextView tvTo;

    @BindView(R.id.layoutFrom)
    LinearLayout layoutFrom;
    @BindView(R.id.layoutTo)
    LinearLayout layoutTo;

    RecyclerView.LayoutManager layoutManager;
    JobsResponse jobsResponse;
    ArrayList<JobsResponse> jobsResponseArrayList;
    JobsAdapters jobsAdapters;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    String jobFlag;

    ChangesInActivies changesInActivies;
    public JobsFragment(String jobFlag,ChangesInActivies changesInActivies) {
        this.jobFlag = jobFlag;
        this.changesInActivies = changesInActivies;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_jobs, container, false);
        ButterKnife.bind(this,view);
        if(jobFlag.equalsIgnoreCase("job")){
            layoutTop.setVisibility(View.GONE);
            divider.setVisibility(View.GONE);
        }else {
            layoutTop.setVisibility(View.VISIBLE);
            divider.setVisibility(View.VISIBLE);
        }
        jobsResponseArrayList= new ArrayList<>();
        layoutManager = new LinearLayoutManager(getContext());
        rvJobs.setLayoutManager(layoutManager);
        rvJobs.setHasFixedSize(false);

        jobsResponse = new JobsResponse("WL12343","11 jan 2021,12:00",
                "Pune","13 jan 2021,11:00","Solapur");
        jobsResponseArrayList.add(jobsResponse);

        jobsResponse = new JobsResponse("WL12343","11 jan 2021,12:00",
                "Pune","13 jan 2021,11:00","Solapur");
        jobsResponseArrayList.add(jobsResponse);
        jobsResponse = new JobsResponse("WL12343","11 jan 2021,12:00",
                "Pune","13 jan 2021,11:00","Solapur");
        jobsResponseArrayList.add(jobsResponse);

        jobsResponse = new JobsResponse("WL12343","11 jan 2021,12:00",
                "Pune","13 jan 2021,11:00","Solapur");
        jobsResponseArrayList.add(jobsResponse);

        jobsResponse = new JobsResponse("WL12343","11 jan 2021,12:00",
                "Pune","13 jan 2021,11:00","Solapur");
        jobsResponseArrayList.add(jobsResponse);

        jobsResponse = new JobsResponse("WL12343","11 jan 2021,12:00",
                "Pune","13 jan 2021,11:00","Solapur");
        jobsResponseArrayList.add(jobsResponse);

        jobsAdapters = new JobsAdapters(getActivity(),jobsResponseArrayList,this);
        rvJobs.setAdapter(jobsAdapters);
        jobsAdapters.notifyDataSetChanged();
        return view;
    }
    @OnClick(R.id.layoutFrom)
    public void onClick(){


        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag("from_dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        // Create and show the dialog.
        FragmentDialogCalendarView newFragment = FragmentDialogCalendarView.newInstance("From");
        newFragment.show(ft, "from_dialog");

    }

    @OnClick(R.id.layoutTo)
    public void onTo(){


        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag("from_dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        // Create and show the dialog.
        FragmentDialogCalendarView newFragment = FragmentDialogCalendarView.newInstance("To");
        newFragment.show(ft, "from_dialog");

    }

    @Override
    public void changesInActivity(String fragment) {
        changesInActivies.changesInActivity("jobDescription");
        JobDescriptionFragment jobDescriptionFragment = new JobDescriptionFragment();
        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        Bundle bundle= new Bundle();
        fragmentTransaction.replace(R.id.fragment_container,jobDescriptionFragment).commit();

    }
}