package com.sabanciuniv.cs310hw2_v0;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.List;

public class MainFragment extends Fragment {
    RecyclerView rcFrag;
    int catId;
    ProgressBar pbFrag;




    Handler dataHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            List<News> data = (List<News>)msg.obj;
            NewsRecViewAdapter adp = new NewsRecViewAdapter(getActivity(),data,pbFrag);
            rcFrag.setAdapter(adp);
            pbFrag.setVisibility(View.INVISIBLE);
            return true;
        }
    });

    MainFragment(int catId){
        super();
        this.catId = catId;

    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        NewsRepository repo = new NewsRepository();
        View v = inflater.inflate(R.layout.fragment_main, container, false);
        pbFrag = v.findViewById(R.id.progressBar2);
        pbFrag.setVisibility(View.VISIBLE);
        rcFrag = v.findViewById(R.id.rcFrag);
        rcFrag.setLayoutManager(new LinearLayoutManager(getActivity()));

        repo.getNewsByCategoryId(((NewsApp)getActivity().getApplication()).srv, dataHandler,catId);
        return v;
    }
}