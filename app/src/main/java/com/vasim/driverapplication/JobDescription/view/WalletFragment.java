package com.vasim.driverapplication.JobDescription.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.vasim.driverapplication.JobDescription.adapter.WalletAdapter;
import com.vasim.driverapplication.JobDescription.model.WalletModel;
import com.vasim.driverapplication.R;
import com.vasim.driverapplication.home.view.fragments.FragmentDialogCalendarView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class WalletFragment extends Fragment {
    private WalletModel walletModel;
    private ArrayList<WalletModel> walletModelArrayList;
    @BindView(R.id.rvWallet)
    RecyclerView rvWallet;

    @BindView(R.id.tvFrom)
    TextView tvFrom;
    @BindView(R.id.tvTo)
    TextView tvTo;

    @BindView(R.id.layoutFrom)
    LinearLayout layoutFrom;
    @BindView(R.id.layoutTo)
    LinearLayout layoutTo;

    @BindView(R.id.spinner2)
    Spinner  spTransaction;

    RecyclerView.LayoutManager layoutManager;
    WalletAdapter walletAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_wallet, container, false);
        ButterKnife.bind(this,view);

        String[] years = {"Last 15 Tran. ","Last Month Trans.","Last 3 Months Trans"};
        ArrayAdapter<CharSequence> langAdapter = new ArrayAdapter<CharSequence>(getActivity(), R.layout.spinner_text, years );
        langAdapter.setDropDownViewResource(R.layout.simple_spinner_layout);
        spTransaction.setAdapter(langAdapter);

        walletModelArrayList = new ArrayList<>();
        walletModel= new WalletModel("01 jan 2021","WL100021","punesdfgsdfg sdfgsdf sdfgsd sasadfgsdf ","Solapu dfgsdf sdfgfdg sdfg fdg r","Collect Cash","120");
        walletModelArrayList.add(walletModel);
        walletModel= new WalletModel("01 jan 2021","WL100021","pune","Solapur","Collect Cash","120");
        walletModelArrayList.add(walletModel);
        walletModel= new WalletModel("01 jan 2021","WL100021","pune","Solapur","Collect Cash","120");
        walletModelArrayList.add(walletModel);     walletModel= new WalletModel("01 jan 2021","WL100021","pune","Solapur","Collect Cash","120");
        walletModelArrayList.add(walletModel);
        walletModel= new WalletModel("01 jan 2021","WL100021","pune","Solapur","Collect Cash","120");
        walletModelArrayList.add(walletModel);     walletModel= new WalletModel("01 jan 2021","WL100021","pune","Solapur","Collect Cash","120");
        walletModelArrayList.add(walletModel);

        layoutManager = new LinearLayoutManager(getActivity());
        rvWallet.setLayoutManager(layoutManager);
        walletAdapter = new WalletAdapter(getContext(),walletModelArrayList);
        rvWallet.setAdapter(walletAdapter);
        walletAdapter.notifyDataSetChanged();



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

}