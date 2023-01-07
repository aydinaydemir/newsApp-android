package com.sabanciuniv.cs310hw2_v0;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;



import android.graphics.Bitmap;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;

public class ActivityDetails extends AppCompatActivity {

    ImageView imgDetails;
    TextView txtTitleDetails;
    TextView txtDateDetails;
    TextView txtTextDetails;
    int newsId;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu1,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // create intent


        if (item.getItemId() == R.id.mn_makeComment){
            Intent i = new Intent(ActivityDetails.this,CommentsActivity.class);
            Log.i("DEV","------------------onOptionsItemSelected" + String.valueOf(newsId));
            i.putExtra("id",newsId);
            ActivityDetails.this.startActivity(i);
            return true;
        }
        else{
            Log.i("DEV","------------------onOptionsItemSelected" + String.valueOf(item.getItemId()));
            finish();
            return true;
        }

    }

    Handler dataHandler = new Handler(new Handler.Callback(){
        @Override
        public boolean handleMessage(@NonNull Message message) {
            News news = (News) message.obj;

            txtTitleDetails.setText(news.getTitle());
            txtDateDetails.setText(news.getDate().toString());
            txtTextDetails.setText(news.getText());
            setTitle(news.getCategoryName());

            NewsRepository repo = new NewsRepository();
            repo.downloadImage(((NewsApp)getApplication()).srv,imgHandler,news.getImage());
            return true;
        }
    });

    Handler imgHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {

            Bitmap img = (Bitmap) msg.obj;
            imgDetails.setImageBitmap(img);

            return true;
        }
    });



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        ActionBar currentBar = getSupportActionBar();
        currentBar.setHomeButtonEnabled(true);
        currentBar.setDisplayHomeAsUpEnabled(true);
        currentBar.setHomeAsUpIndicator(R.drawable.left_arrow_back_svgrepo_com);

        int id = getIntent().getIntExtra("id",1);

        newsId = id;
        imgDetails = findViewById(R.id.imgDetails);
        txtTitleDetails = findViewById(R.id.txtTitleDetail);
        txtDateDetails = findViewById(R.id.txtDateDetail);
        txtTextDetails = findViewById(R.id.txtTextDetail);

        NewsRepository repo = new NewsRepository();
        repo.getNewsByNewsId(((NewsApp)getApplication()).srv,dataHandler,id);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }



}