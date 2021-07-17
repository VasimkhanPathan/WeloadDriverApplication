package com.weload.driverapplication.splash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.weload.driverapplication.R;
import com.weload.driverapplication.home.HomeActivity;
import com.weload.driverapplication.login.view.LoginActivity;
import com.weload.driverapplication.util.AppConstants;

public class MainActivity extends AppCompatActivity {
    String sessionKey="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sessionKey= AppConstants.Preferences.getStringPreference(getApplicationContext(),AppConstants.Keys.SESSION_TOKEN,"");

     /*   FirebaseMessaging.getInstance().subscribeToTopic("")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg ="Done";
                        if (!task.isSuccessful()) {
                            msg = "faild";
                        }
                        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                    }
                });*/

      new Handler().postDelayed(new Runnable() {
          @Override
          public void run() {
              if(sessionKey.equals("")){
                  Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                  startActivity(intent);
                  finish();
              }else {
                  Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                  startActivity(intent);
                  finish();
              }

          }
      },1000);
    }

}