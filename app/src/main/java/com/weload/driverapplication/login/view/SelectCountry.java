package com.weload.driverapplication.login.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


import com.mukesh.countrypicker.CountryPicker;
import com.mukesh.countrypicker.CountryPickerListener;
import com.weload.driverapplication.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SelectCountry extends AppCompatActivity{

    private CountryPicker countryPicker;
    @BindView(R.id.tvSeachCountry)
    TextView tvSeachCountry;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_country);
        ButterKnife.bind(this);
        tvSeachCountry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCountry();
            }
        });

    }
    public void showCountry(){
        CountryPicker picker = CountryPicker.newInstance("Select Country");  // dialog title
        picker.setListener(new CountryPickerListener() {
            @Override
            public void onSelectCountry(String name, String code, String dialCode, int flagDrawableResID) {
                // Implement your code here
                picker.dismiss();
            }
        });
        picker.show(getSupportFragmentManager(), "COUNTRY_PICKER");
    }
}