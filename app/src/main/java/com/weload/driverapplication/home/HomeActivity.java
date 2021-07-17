package com.weload.driverapplication.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.internal.NavigationMenuView;
import com.google.android.material.navigation.NavigationView;
import com.weload.driverapplication.JobDescription.view.activities.WalletActivity;
import com.weload.driverapplication.R;
import com.weload.driverapplication.home.interfaces.ChangesInActivies;
import com.weload.driverapplication.home.view.HomeFragement;
import com.weload.driverapplication.home.view.activites.JobListActivity;
import com.weload.driverapplication.home.view.activites.LeaveApplicationActivity;
import com.weload.driverapplication.login.view.LoginActivity;
import com.weload.driverapplication.util.AppConstants;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeActivity extends AppCompatActivity implements ChangesInActivies {

    NavigationView navigationView;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    ImageView imageView, ivMapwhite;
    ImageView ivMenu;
    Toolbar toolbar;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    ConstraintLayout profileLayout;
    LinearLayout bottomLayout;
    RelativeLayout layoutTool;
    TextView tvToolTitle;
    LinearLayout layoutToolabr, layoutMap;
    @BindView(R.id.layout_home)
    LinearLayout layoutHome;
    @BindView(R.id.ivProfileImage)
    ImageView ivProfileImage;
    @BindView(R.id.ivMapMarkerBlue)
    ImageView ivMapMarkerBlue;
    @BindView(R.id.ivMapMarkerWhite)
    ImageView ivMapMarkerWhite;
    @BindView(R.id.ivJobStatusWhite)
    ImageView ivJobStatusWhite;

    @BindView(R.id.walletLayout)
    LinearLayout layoutWallet;
    @BindView(R.id.ivHome)
            ImageView ivHome;
    @BindView(R.id.ivFileWhite)
    ImageView ivFile;
    TextView tvToolarTilte;
    @BindView(R.id.tvDriverName)
            TextView tvDriverName;
    @BindView(R.id.tvDriverId)
            TextView tvDriverId;
    @BindView(R.id.tvAppVersion)
    TextView tvAppVersion;

    boolean isDrawerOpen = false;
    String strDriverId="";
    String strDriverName="";
    String strMobileNo="";

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ButterKnife.bind(this);

        ivMenu = findViewById(R.id.ivMenu);

        initializeToolbar();

        imageView = findViewById(R.id.imgClose);
        ivMapwhite = findViewById(R.id.ivMapMarkerWhite);
        layoutTool = findViewById(R.id.layout_tool);
        tvToolarTilte = findViewById(R.id.tvTooltitle);
        layoutMap = findViewById(R.id.layoutMap);


        navigationView = findViewById(R.id.navigation);
        drawerLayout = findViewById(R.id.drawerLayout);
        profileLayout = findViewById(R.id.profileLayout);
        bottomLayout = findViewById(R.id.bottomLayout);
        tvToolTitle = findViewById(R.id.toolTitleWhite);
        layoutToolabr = findViewById(R.id.layoutToolbar);

        layoutHome = findViewById(R.id.layout_home);
        layoutToolabr.setVisibility(View.GONE);
        tvToolarTilte.setVisibility(View.VISIBLE);

        NavigationMenuView navMenuView = (NavigationMenuView) navigationView.getChildAt(0);
//        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);

//        toggle.setDrawerIndicatorEnabled(false);

        toggle = new ActionBarDrawerToggle(this, drawerLayout,
               toolbar, R.string.open, R.string.close) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                // Do whatever you want here
                isDrawerOpen = false;
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                isDrawerOpen = true;
                // Do whatever you want here
            }
        };
// Set the drawer toggle as the DrawerListener
        drawerLayout.addDrawerListener(toggle);



        navigationView.setItemIconTintList(null);
        navMenuView.addItemDecoration(new DividerItemDecoration(HomeActivity.this, DividerItemDecoration.VERTICAL));

        checkAndRequestPermissions();
        strDriverId= AppConstants.Preferences.getStringPreference(getApplicationContext(),AppConstants.Keys.DRIVE_ID,"");
        strDriverName=AppConstants.Preferences.getStringPreference(getApplicationContext(),AppConstants.Keys.DRIVE_NAME,"");
        strMobileNo=AppConstants.Preferences.getStringPreference(getApplicationContext(),AppConstants.Keys.DRIVE_MOBILE,"");

        tvDriverId.setText(strMobileNo);
        tvDriverName.setText(strDriverName);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.menuHome:
                        toolbar.setBackgroundColor(Color.parseColor("#255392"));
                        drawerLayout.closeDrawer(GravityCompat.START);
                        layoutTool.setBackgroundColor(Color.parseColor("#255392"));
                        tvToolTitle.setTextColor(Color.parseColor("#ffffff"));
                       // toggle.setHomeAsUpIndicator(R.drawable.ic_menu_bars);
                        ivMenu.setImageResource(R.drawable.ic_menu_bars);
                        profileLayout.setVisibility(View.VISIBLE);
                        layoutWallet.setVisibility(View.GONE);
                        bottomLayout.setVisibility(View.VISIBLE);
                        layoutToolabr.setVisibility(View.GONE);
                        tvToolarTilte.setVisibility(View.VISIBLE);
                        HomeFragement homeFragement = new HomeFragement(HomeActivity.this);
                        fragmentManager = getSupportFragmentManager();
                        fragmentTransaction = fragmentManager.beginTransaction();
                        Bundle bundle = new Bundle();
                        fragmentTransaction.replace(R.id.fragment_container, homeFragement).commit();

                        break;
                    case R.id.menuJob:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        Intent intent = new Intent(getApplicationContext(), JobListActivity.class);
                        intent.putExtra("pageFlag","job");
                        startActivity(intent);
                        break;

                    case R.id.menuWallet:
                        drawerLayout.closeDrawer(GravityCompat.START);

                      selectWallet();
                        break;

                    case R.id.menuJobHistory:
                        drawerLayout.closeDrawer(GravityCompat.START);

                        slectJobHistory();
                        break;
                    case R.id.menuLeave:
                        leaveApp();
                        break;
                    case R.id.menuLogOut:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        logOut();
                        break;

                }
                return false;
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });

       /* layoutMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HomeActivity.this, "Map Clicked", Toast.LENGTH_SHORT).show();
                ShowMapFragment mapFragment = new ShowMapFragment();
                fragmentTransaction = fragmentManager.beginTransaction();
               fragmentTransaction.replace(R.id.fragment_container, mapFragment).addToBackStack(null).commit();
               ivMapMarkerBlue.setVisibility(View.GONE);
               ivMapMarkerWhite.setVisibility(View.VISIBLE);

            }
        });
*/


        layoutHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HomeActivity.this, "Home t", Toast.LENGTH_SHORT).show();
            }
        });
        HomeFragement homeFragement = new HomeFragement(HomeActivity.this);
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        Bundle bundle = new Bundle();
        fragmentTransaction.replace(R.id.fragment_container, homeFragement).commit();
    }

    private void slectJobHistory() {

        Intent intent = new Intent(getApplicationContext(), JobListActivity.class);
        intent.putExtra("pageFlag","job_history");
        startActivity(intent);
    }

    private void initializeToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        toolbar.setBackgroundColor(ContextCompat.getColor(this,R.color.splash_color));
        getVersion();
        getSupportActionBar().setHomeButtonEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(null);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);


        ivMenu.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View view) {
                if(!isDrawerOpen) {
                    drawerLayout.openDrawer(Gravity.START);
                    isDrawerOpen = true;
                }
                else {
                    try {
                        drawerLayout.closeDrawer(Gravity.END);
                        isDrawerOpen = false;
                    }catch (Exception e){
                        Log.d("Log","error "+e.getMessage());
                    }
                }
            }
        });


    }

    @OnClick(R.id.layout_home)
    public void layoutHome() {
        Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show();

    }

    @OnClick(R.id.ivProfileImage)
    public void layoutProfile() {
        Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show();
    }



    public void selectWallet(){
       Intent intent = new Intent(getApplicationContext(), WalletActivity.class);
       startActivity(intent);
    }
    public void logOut(){
        AppConstants.Preferences.setStringPreferences(getApplicationContext(),AppConstants.Keys.SESSION_TOKEN,"");
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);

        startActivity(intent);
        finish();
    }
    public void leaveApp(){
        Intent intent = new Intent(getApplicationContext(), LeaveApplicationActivity.class);
        startActivity(intent);
    }
    public void getVersion(){
            String versionName="";
            try {
                versionName = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
                tvAppVersion.setText("AppVersion: "+versionName);
            } catch (PackageManager.NameNotFoundException e) {

            }
    }

    private boolean checkAndRequestPermissions() {
        int callpermission = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE);
        int locationpermission = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION);

        List<String> listPermissionsNeeded = new ArrayList<>();

        if (locationpermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }


        if (callpermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CALL_PHONE);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            requestPermissions(listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), 1);
            return false;
        }
        return true;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {

            case 1: {

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(), "Permission granted", Toast.LENGTH_SHORT).show();
                    // call_action();
                } else {
                    Toast.makeText(getApplicationContext(), "Permission denied", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    @Override
    public void changesInActivity(String fragment, String id) {

    }
  /*  public void deleteToken(){
        new AsyncTask<Void,Void,Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try
                {
                    FirebaseMessaging.getInstance().deleteToken();
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                // Call your Activity where you want to land after log out
            }
        }.execute();
    }*/
}