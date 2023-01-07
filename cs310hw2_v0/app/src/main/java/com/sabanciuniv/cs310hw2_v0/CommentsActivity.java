package com.sabanciuniv.cs310hw2_v0;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import java.util.List;

public class CommentsActivity extends AppCompatActivity {

    RecyclerView rvComments;
    int newsId;
    ProgressBar pbComments;


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        NewsRepository repo = new NewsRepository();

        repo.getCommentsByNewsId(((NewsApp)getApplication()).srv,dataHandler,newsId);

    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        ActionBar currentBar = getSupportActionBar();
        currentBar.setHomeButtonEnabled(true);
        currentBar.setDisplayHomeAsUpEnabled(true);
        currentBar.setHomeAsUpIndicator(R.drawable.left_arrow_back_svgrepo_com);
        MenuItem item = menu.findItem(R.id.mn_makeComment);
        item.setVisible(false);
        getMenuInflater().inflate(R.menu.menu2,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.mn_makeComment){
            Intent intent = new Intent(CommentsActivity.this,PostCommentActivity.class);
            intent.putExtra("newsId",newsId);
            startActivity(intent);
        }
        else{
            finish();
        }
        return true;


    }

    Handler dataHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            List<Comment> data = (List<Comment>)msg.obj;
            Log.i("DEV","------------------handleMessage" + String.valueOf(data.size()));
            CommentsRecViewAdapter adp = new CommentsRecViewAdapter(CommentsActivity.this,data);
            Log.i("DEV","adp set oncesi");
            rvComments.setAdapter(adp);
            Log.i("DEV","adp set sonrasi");
            adp.notifyDataSetChanged();
            pbComments.setVisibility(View.INVISIBLE);
            return true;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("DEV","------------------onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);
        setTitle("COMMENTS");
        pbComments = findViewById(R.id.progressBar4);
        pbComments.setVisibility(View.VISIBLE);

        rvComments = findViewById(R.id.rvComments);
        rvComments.setLayoutManager(new LinearLayoutManager(this));
        invalidateOptionsMenu();


        newsId = getIntent().getIntExtra("id",1);
        Log.i("DEV","------------------onCreate newsId:"+newsId);
        NewsRepository repo = new NewsRepository();
        repo.getCommentsByNewsId(((NewsApp)getApplication()).srv,dataHandler,newsId);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu1,menu);
        return true;
    }
}