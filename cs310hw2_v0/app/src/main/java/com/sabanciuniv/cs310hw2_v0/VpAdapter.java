package com.sabanciuniv.cs310hw2_v0;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.List;

public class VpAdapter extends FragmentStateAdapter {
    NewsApp myApp;
    public VpAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, NewsApp app)
    {
        super(fragmentManager, lifecycle);
        this.myApp = app;
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Log.i("DEV","create fragment called! with pos = " + String.valueOf(position));
        MainFragment dataFrag = new MainFragment(position+1);
        return dataFrag;
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
