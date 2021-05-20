package com.vasim.driverapplication.splash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.vasim.driverapplication.R;
import com.vasim.driverapplication.home.HomeActivity;
import com.vasim.driverapplication.login.view.LoginActivity;
import com.vasim.driverapplication.login.view.SelectCountry;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
      new Handler().postDelayed(new Runnable() {
          @Override
          public void run() {
              Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
              startActivity(intent);
              finish();
          }
      },1000);
    }
}