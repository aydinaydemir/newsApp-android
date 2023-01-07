package com.sabanciuniv.cs310hw2_v0;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    TabLayout tlMain;
    ViewPager2 vpMain;
    VpAdapter vpadp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("CS310 News");
        tlMain = findViewById(R.id.tlMain);
        vpMain = findViewById(R.id.vpMain);


        ActionBar currentBar = getSupportActionBar();
        currentBar.setHomeButtonEnabled(true);
        currentBar.setDisplayHomeAsUpEnabled(true);
        currentBar.setHomeAsUpIndicator(R.drawable.home_interface_button_symbol_svgrepo_com);




        //NewsRepository repo = new NewsRepository();
        //repo.getAllCategories(((NewsApp) getApplication()).srv, catDataHandler);
        tlMain.addTab(tlMain.newTab().setText("Economics"));
        tlMain.addTab(tlMain.newTab().setText("Sports"));
        tlMain.addTab(tlMain.newTab().setText("Politics"));

        FragmentManager fman = getSupportFragmentManager();
        vpadp = new VpAdapter(fman,getLifecycle(),(NewsApp) getApplication());
        vpMain.setAdapter(vpadp);


        tlMain.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                vpadp.notifyDataSetChanged();
                vpMain.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        vpMain.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                tlMain.selectTab(tlMain.getTabAt(position));
            }
        });












    }




}