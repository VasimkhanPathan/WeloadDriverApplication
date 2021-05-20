package com.vasim.driverapplication.JobDescription.view;

import android.app.Dialog;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.lin.timeline.TimeLineDecoration;
import com.vasim.driverapplication.JobDescription.JobDescriptionFragment;
import com.vasim.driverapplication.JobDescription.adapter.AnalogAdapter;
import com.vasim.driverapplication.JobDescription.model.Analog;
import com.vasim.driverapplication.R;
import com.vasim.driverapplication.home.HomeActivity;
import com.vasim.driverapplication.home.interfaces.ChangesInActivies;
import com.vasim.driverapplication.home.view.HomeFragement;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.lin.timeline.TimeLineDecoration.BEGIN;
import static com.lin.timeline.TimeLineDecoration.CUSTOM;
import static com.lin.timeline.TimeLineDecoration.END;
import static com.lin.timeline.TimeLineDecoration.END_FULL;
import static com.lin.timeline.TimeLineDecoration.GREEN;
import static com.lin.timeline.TimeLineDecoration.NORMAL;


public class TimelineFragment extends Fragment {

    @BindView(R.id.rvTimeline)
    RecyclerView recyclerView;
    @BindView(R.id.btnChangeStatus)
    Button btnChangeStatus;
    List<Analog> analogs;
    int count=0;
    TimeLineDecoration decoration;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    ChangesInActivies changesInActivies;

    AnalogAdapter adapter;
    boolean loading = false, inProgress = false, unLoading = false,isPicked=false,isReached=false,isPayment=false;

    public TimelineFragment(ChangesInActivies changesInActivies) {
        this.changesInActivies = changesInActivies;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_timeline, container, false);

        ButterKnife.bind(this,view);
        initView();
        initData();
        return view;
    }
    private void initView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        if(decoration!=null)
        recyclerView.removeItemDecoration(decoration);


        decoration  = new TimeLineDecoration(getContext())
                .setLineColor(android.R.color.black)
                .setLineWidth(1)
                .setLeftDistance(110)
                .setTopDistance(5)

                .setBeginMarker(R.drawable.begin_marker)
                .setCustomMarker(R.drawable.begin_marker)
                .setCustomGreenMarker(R.drawable.green_status)
                .setMarkerRadius(30)
                .setNormalMarker(R.drawable.second_marker)
                .setEndMarker(R.drawable.second_marker)
                .setCallback(new TimeLineDecoration.TimeLineAdapter() {

                    @Nullable
                    @Override
                    public Rect getRect(int position) {
                        return new Rect(0, 50, 0, 25);
                    }

                    @Override
                    public int getTimeLineType(int position) {
                       /* if (position == analogs.size()-1){
                            return END;
                        }
                        if(position==0){
                            return BEGIN;
                        }
                        else if (position >5) {
                            return CUSTOM;
                        }
                        else{
                            return NORMAL;
                        }*/
                        if (position == analogs.size()-1){
                            return END;
                        }
                        if(position==0){
                            return BEGIN;
                        }
                        else if (position ==1) {
                            if(loading) {
                                return GREEN;
                            }else {
                                return NORMAL;
                            }
                        }
                        else if (position ==2) {
                            if(isPicked){
                                return CUSTOM;
                            }else {
                                return NORMAL;
                            }

                        }
                        else if (position ==3) {
                            if(inProgress){
                                return GREEN;
                            }else
                            return NORMAL;
                        }
                        else if (position ==4) {
                            if(isReached)
                            return CUSTOM;
                            else
                                return NORMAL;
                        }
                        else if (position ==5) {
                            if(unLoading)
                                return GREEN;
                            else
                                return NORMAL;
                        }
                        else if (position ==6) {
                            if(isPayment)
                                return CUSTOM;
                            else
                                return NORMAL;
                        }else {
                            return NORMAL;
                        }
                    }
                });


        recyclerView.addItemDecoration(decoration);

        adapter = new AnalogAdapter(this,getContext());
        recyclerView.setAdapter(adapter);
    }

    private void initData() {

         analogs = new ArrayList<>();

        Analog analog0 = new Analog();
        analog0.isHead = true;
        analog0.text = "Heading towards pick up location";
        analog0.time = "2016-01-08 10:20:10";
        analogs.add(analog0);

        Analog analog1 = new Analog();
        analog1.isHead = true;
        analog1.text = "Loading";
        analog1.time = "2016-01-02 15:10:10";
        analogs.add(analog1);

        Analog analog2 = new Analog();
        analog2.isHead = false;
        analog2.text = "Picked up";
        analog2.time = "2016-01-01 10:10:10";
        analogs.add(analog2);

        Analog analog3= new Analog();
        analog3.isHead = false;
        analog3.text = "In Progress";
        analog3.time = "2016-01-01 10:10:10";
        analogs.add(analog3);
        Analog analog4 = new Analog();
        analog4.isHead = false;
        analog4.text = "Reached drop off location";
        analog4.time = "2016-01-01 10:10:10";
        analogs.add(analog4);

        Analog analog5 = new Analog();
        analog5.isHead = false;
        analog5.text = "Unloading";
        analog5.time = "2016-01-01 10:10:10";
        analogs.add(analog5);

        Analog analog6 = new Analog();
        analog6.isHead = false;
        analog6.text = "Payment";
        analog6.time = "2016-01-01 10:10:10";
        analogs.add(analog6);



        adapter.setItems(analogs);
    }
    @OnClick(R.id.btnChangeStatus)
    public void changStatus(){
        if(count==0){
            loading=true;
            initView();
            initData();
            btnChangeStatus.setText("Picked Up");

        }
        if (count==1) {
            isPicked=true;
            initView();
            initData();
            btnChangeStatus.setText("In Progress");
        }
        if (count==2) {
            inProgress=true;

            initView();
            initData();
            btnChangeStatus.setText("Reached");
        }
        if (count==3) {
            isReached=true;

            initView();
            initData();
            btnChangeStatus.setText("Reached");
        }
        if (count==4) {
            unLoading=true;

            initView();
            initData();
            btnChangeStatus.setText("Unloading");
        }
        if (count==5) {
            isPayment=true;

            initView();
            initData();
            btnChangeStatus.setText("Payment");
        }
        if (count==6) {

          viewPayment();
        }
        if(count==7){
            changesInActivies.changesInActivity("timeline");
            PaymentSuccessFragment paymentSuccessFragment = new PaymentSuccessFragment(changesInActivies);
            fragmentManager = getFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            Bundle bundle = new Bundle();
            fragmentTransaction.replace(R.id.fragment_container, paymentSuccessFragment).commit();
        }
count++;
    }
    public void viewPayment(){
        final Dialog dialog = new Dialog(this.getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_payment);
        dialog.setCancelable(false);
        Button btnSubmit = dialog.findViewById(R.id.btnSubmit);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
                btnChangeStatus.setText("End");
            }
        });

        dialog.show();
    }
}
