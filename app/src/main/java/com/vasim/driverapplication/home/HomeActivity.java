package com.vasim.driverapplication.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
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
import com.vasim.driverapplication.JobDescription.view.ShowMapFragment;
import com.vasim.driverapplication.JobDescription.view.TimelineFragment;
import com.vasim.driverapplication.JobDescription.view.WalletFragment;
import com.vasim.driverapplication.R;
import com.vasim.driverapplication.home.interfaces.ChangesInActivies;
import com.vasim.driverapplication.home.view.HomeFragement;
import com.vasim.driverapplication.home.view.fragments.JobsFragment;

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

    boolean isDrawerOpen = false;

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

//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        toggle.setDrawerIndicatorEnabled(false);
//        toggle.setHomeAsUpIndicator(R.drawable.ic_menu_bars);

        // toolbar.setNavigationIcon(R.drawable.ic_menu_bars);
//        drawerLayout.addDrawerListener(toggle);
//        layoutToolabr.setVisibility(View.GONE);
//        tvToolarTilte.setVisibility(View.VISIBLE);
//        toggle.syncState();


        navigationView.setItemIconTintList(null);
        navMenuView.addItemDecoration(new DividerItemDecoration(HomeActivity.this, DividerItemDecoration.VERTICAL));
//        toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
//                    drawerLayout.closeDrawer(GravityCompat.START);
//                } else {
//                    drawerLayout.openDrawer(GravityCompat.START);
//                }
//            }
//        });
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
                        toolbar.setBackgroundColor(Color.parseColor("#ffffff"));
                        drawerLayout.closeDrawer(GravityCompat.START);
                     //  toggle.setHomeAsUpIndicator(R.drawable.ic_menu_bars_blue);
                        ivMenu.setImageResource(R.drawable.ic_menu_bars_blue);
                        layoutTool.setBackgroundColor(Color.WHITE);
                        tvToolTitle.setTextColor(Color.parseColor("#255392"));
                        profileLayout.setVisibility(View.GONE);
                        bottomLayout.setVisibility(View.GONE);
                        layoutWallet.setVisibility(View.GONE);
                        JobsFragment jobsFragment = new JobsFragment("job",HomeActivity.this);
                        fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.fragment_container, jobsFragment).commit();
                        layoutToolabr.setVisibility(View.GONE);
                       tvToolarTilte.setVisibility(View.VISIBLE);
                       tvToolarTilte.setTextColor(Color.parseColor("#255392"));
                      /*  ivMapMarkerBlue.setVisibility(View.VISIBLE);
                        ivMapMarkerWhite.setVisibility(View.GONE);*/
                        ivMapMarkerWhite.setVisibility(View.VISIBLE);
                      ivMapMarkerWhite.setImageResource(R.drawable.ic_location_blue);
                    //  ivMapMarkerWhite.setBackgroundResource(R.drawable.icon_background);
                  //      ivMapMarkerWhite.setPadding(15,0,0,0);

                        ivJobStatusWhite.setVisibility(View.VISIBLE);
                        ivJobStatusWhite.setImageResource(R.drawable.ic_job_proccess_blue);
                      //  ivJobStatusWhite.setBackgroundResource(R.drawable.icon_background);
                       // ivJobStatusWhite.setPadding(15,0,0,0);

                        ivFile.setVisibility(View.VISIBLE);
                        ivFile.setImageResource(R.drawable.ic_job_details_blue);
                        ivFile.setBackgroundResource(R.drawable.icon_background);
                        //ivFile.setPadding(15,0,0,0);
                        break;

                    case R.id.menuWallet:
                        drawerLayout.closeDrawer(GravityCompat.START);
                      /*  toolbar.setBackgroundColor(Color.parseColor("#255392"));
                        layoutTool.setBackgroundColor(Color.parseColor("#255392"));
                        ivMenu.setImageResource(R.drawable.ic_menu_bars);
                        profileLayout.setVisibility(View.GONE);
                        layoutWallet.setVisibility(View.VISIBLE);
                        layoutToolabr.setVisibility(View.GONE);
                        tvToolarTilte.setVisibility(View.VISIBLE);
                        bottomLayout.setVisibility(View.GONE);
                        WalletFragment walletFragment = new WalletFragment();
                        fragmentTransaction = fragmentManager.beginTransaction();

                        fragmentTransaction.replace(R.id.fragment_container, walletFragment).commit();*/
                      selectWallet();
                        break;

                    case R.id.menuJobHistory:
                        drawerLayout.closeDrawer(GravityCompat.START);
                      /*  toolbar.setBackgroundColor(Color.parseColor("#255392"));
                        layoutTool.setBackgroundColor(Color.parseColor("#255392"));
                        ivMenu.setImageResource(R.drawable.ic_menu_bars);
                        profileLayout.setVisibility(View.GONE);
                        layoutWallet.setVisibility(View.VISIBLE);
                        layoutToolabr.setVisibility(View.GONE);
                        tvToolarTilte.setVisibility(View.VISIBLE);
                        bottomLayout.setVisibility(View.GONE);
                        WalletFragment walletFragment = new WalletFragment();
                        fragmentTransaction = fragmentManager.beginTransaction();

                        fragmentTransaction.replace(R.id.fragment_container, walletFragment).commit();*/
                        slectJobHistory();
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

        layoutMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HomeActivity.this, "Map Clicked", Toast.LENGTH_SHORT).show();
                ShowMapFragment mapFragment = new ShowMapFragment();
                fragmentTransaction = fragmentManager.beginTransaction();
               fragmentTransaction.replace(R.id.fragment_container, mapFragment).commit();
               ivMapMarkerBlue.setVisibility(View.GONE);
               ivMapMarkerWhite.setVisibility(View.VISIBLE);

            }
        });

        ivJobStatusWhite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimelineFragment timelineFragment = new TimelineFragment(HomeActivity.this);
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, timelineFragment).commit();
                drawerLayout.closeDrawer(GravityCompat.START);
                //  toggle.setHomeAsUpIndicator(R.drawable.ic_menu_bars_blue);
                toolbar.setBackgroundColor(Color.parseColor("#255392"));
                layoutTool.setBackgroundColor(Color.parseColor("#255392"));
                ivJobStatusWhite.setImageResource(R.drawable.ic_job_proccess_outline_white);
              //  ivJobStatusWhite.setBackgroundResource(R.drawable.icon_background);
             //   ivJobStatusWhite.setPaddingRelative(5,5,5,5);
                ivMenu.setImageResource(R.drawable.ic_menu_bars);
                profileLayout.setVisibility(View.GONE);
                layoutWallet.setVisibility(View.GONE);
                layoutToolabr.setVisibility(View.GONE);
                tvToolTitle.setTextColor(Color.parseColor("#ffffff"));

                ivHome.setImageResource(R.drawable.ic_home_white);

               // ivHome.setBackgroundResource(R.drawable.text_view_background);
              //  ivHome.setPaddingRelative(5,5,5,5);

                ivFile.setImageResource(R.drawable.ic_file_alt_solid);
                ivFile.setBackgroundResource(R.drawable.text_view_background);
                ivFile.setPaddingRelative(15,5,5,5);

                ivMapMarkerWhite.setImageResource(R.drawable.ic_location_white);
               // ivMapMarkerWhite.setBackgroundResource(R.drawable.text_view_background);
              //  ivMapMarkerWhite.setPaddingRelative(5,5,5,5);


                layoutToolabr.setVisibility(View.VISIBLE);


            }
        });


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
        toolbar.setBackgroundColor(Color.parseColor("#255392"));
        layoutTool.setBackgroundColor(Color.parseColor("#255392"));
        ivMenu.setImageResource(R.drawable.ic_menu_bars);
        profileLayout.setVisibility(View.GONE);
        layoutWallet.setVisibility(View.GONE);
        layoutToolabr.setVisibility(View.VISIBLE);
        tvToolTitle.setTextColor(Color.WHITE);
        tvToolarTilte.setVisibility(View.GONE);
        bottomLayout.setVisibility(View.GONE);
        ivFile.setVisibility(View.GONE);
        ivHome.setImageResource(R.drawable.ic_home_white);
        ivJobStatusWhite.setVisibility(View.GONE);
        ivMapMarkerWhite.setVisibility(View.GONE);
        JobsFragment jobsFragment = new JobsFragment("jobHistory",this);
        fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.fragment_container, jobsFragment).commit();
    }

    private void initializeToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        toolbar.setBackgroundColor(ContextCompat.getColor(this,R.color.splash_color));

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
                    drawerLayout.closeDrawer(Gravity.END);
                    isDrawerOpen = false;
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


    @Override
    public void changesInActivity(String fragment) {
        if (fragment.equalsIgnoreCase("jobs")) {
              toolbar.setBackgroundColor(Color.WHITE);
            layoutTool.setBackgroundColor(Color.WHITE);
            ivMenu.setImageResource(R.drawable.ic_menu_bars_blue);
            tvToolTitle.setTextColor(Color.parseColor("#255392"));
//            toggle.setHomeAsUpIndicator(R.drawable.ic_menu_bars_blue);
            //    toolbar.setTitle("WELOAD DRIVER");
            toolbar.setTitleTextColor(Color.parseColor("#255392"));
            profileLayout.setVisibility(View.GONE);
            bottomLayout.setVisibility(View.GONE);
            layoutToolabr.setVisibility(View.GONE);
           tvToolarTilte.setVisibility(View.VISIBLE);
           tvToolarTilte.setTextColor(Color.parseColor("#255392"));
           ivHome.setVisibility(View.VISIBLE);
           ivFile.setVisibility(View.VISIBLE);
           ivMapwhite.setVisibility(View.VISIBLE);
           ivJobStatusWhite.setVisibility(View.VISIBLE);
           ivJobStatusWhite.setImageResource(R.drawable.ic_job_proccess_blue);
            JobsFragment jobsFragment = new JobsFragment("job",this);
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, jobsFragment).commit();

        }else if(fragment.equalsIgnoreCase("timeline")){
            toolbar.setBackgroundColor(Color.WHITE);
            layoutTool.setBackgroundColor(Color.WHITE);
            ivMenu.setImageResource(R.drawable.ic_menu_bars_blue);
            tvToolTitle.setTextColor(Color.parseColor("#255392"));
//            toggle.setHomeAsUpIndicator(R.drawable.ic_menu_bars_blue);
            //    toolbar.setTitle("WELOAD DRIVER");
            toolbar.setTitleTextColor(Color.parseColor("#255392"));
            profileLayout.setVisibility(View.GONE);
            bottomLayout.setVisibility(View.GONE);
            layoutToolabr.setVisibility(View.VISIBLE);
            tvToolarTilte.setVisibility(View.GONE);
            ivJobStatusWhite.setVisibility(View.GONE);
            ivMapwhite.setVisibility(View.GONE);
            ivFile.setVisibility(View.GONE);
            ivHome.setImageResource(R.drawable.ic_home_blue_);
         //   ivHome.setBackgroundResource(R.drawable.icon_background);
        }else if(fragment.equalsIgnoreCase("paymentSucess")){
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
        }else if(fragment.equalsIgnoreCase("wallet")){
            selectWallet();
        }else if(fragment.equalsIgnoreCase("history")){
            slectJobHistory();
        }else if(fragment.equalsIgnoreCase("jobDescription")){
            jobDescription();
        }
    }
    public void selectWallet(){
        toolbar.setBackgroundColor(Color.parseColor("#255392"));
        layoutTool.setBackgroundColor(Color.parseColor("#255392"));
        ivMenu.setImageResource(R.drawable.ic_menu_bars);
        profileLayout.setVisibility(View.GONE);
        layoutWallet.setVisibility(View.VISIBLE);
        layoutToolabr.setVisibility(View.GONE);
        tvToolarTilte.setVisibility(View.VISIBLE);
        bottomLayout.setVisibility(View.GONE);
        WalletFragment walletFragment = new WalletFragment();
        fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.fragment_container, walletFragment).commit();
    }
    public void jobDescription(){
            toolbar.setBackgroundColor(Color.WHITE);
            layoutTool.setBackgroundColor(Color.WHITE);
            ivMenu.setImageResource(R.drawable.ic_menu_bars_blue);
            tvToolTitle.setTextColor(Color.parseColor("#255392"));
//            toggle.setHomeAsUpIndicator(R.drawable.ic_menu_bars_blue);
            //    toolbar.setTitle("WELOAD DRIVER");
            toolbar.setTitleTextColor(Color.parseColor("#255392"));
            profileLayout.setVisibility(View.GONE);
            bottomLayout.setVisibility(View.GONE);
            layoutToolabr.setVisibility(View.VISIBLE);
            tvToolarTilte.setVisibility(View.GONE);
            tvToolarTilte.setTextColor(Color.parseColor("#255392"));
            ivHome.setVisibility(View.VISIBLE);
            ivHome.setImageResource(R.drawable.ic_back_);
            ivFile.setVisibility(View.VISIBLE);
              ivFile.setPaddingRelative(0,0,0,0);
              ivFile.setBackgroundResource(0);
            ivFile.setImageResource(R.drawable.ic_job_details_blue_out_line);

                     ivMapwhite.setVisibility(View.VISIBLE);
            ivMapwhite.setImageResource(R.drawable.ic_location_blue);
            ivJobStatusWhite.setVisibility(View.VISIBLE);
            ivJobStatusWhite.setImageResource(R.drawable.ic_job_proccess_blue);
            JobsFragment jobsFragment = new JobsFragment("job",this);
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, jobsFragment).commit();


    }
}