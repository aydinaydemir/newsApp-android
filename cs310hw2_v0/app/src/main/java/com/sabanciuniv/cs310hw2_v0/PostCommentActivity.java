package com.sabanciuniv.cs310hw2_v0;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class PostCommentActivity extends AppCompatActivity {

    EditText txtName,txtComment;
    Button btnPost;
    ProgressBar progressBar;
    int newsId;

    Handler dataHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            Intent i = new Intent(PostCommentActivity.this,CommentsActivity.class);
            i.putExtra("id",newsId);
            finish();
            PostCommentActivity.this.startActivity(i);
            progressBar.setVisibility(View.INVISIBLE);
            return true;


        }
    });

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        finish();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_comment);
        setTitle("Post Comment");
        ActionBar currentBar = getSupportActionBar();
        currentBar.setHomeButtonEnabled(true);
        currentBar.setDisplayHomeAsUpEnabled(true);
        currentBar.setHomeAsUpIndicator(R.drawable.left_arrow_back_svgrepo_com);
        txtName = findViewById(R.id.txtName);
        txtComment = findViewById(R.id.txtComment);
        btnPost = findViewById(R.id.btnPost);
        TextView txtInform = findViewById(R.id.txtInform);
        progressBar = findViewById(R.id.progressBar3);

        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = txtName.getText().toString();
                String comment = txtComment.getText().toString();

                if (name.isEmpty() || comment.isEmpty()){
                    Toast.makeText(PostCommentActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                    txtInform.setText("Please fill all fields");
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                newsId = getIntent().getIntExtra("newsId",0);
                Log.i("DEV","--------onclicklistener newsID is: " + String.valueOf(newsId));
                Comment c = new Comment();
                c.setNews_id(newsId);
                c.setText(comment);
                c.setName(name);
                Log.i("DEV","--------onclicklistener commentText is: " + String.valueOf(c.getText()));
                NewsRepository repo = new NewsRepository();
                repo.saveComment(((NewsApp)getApplication()).srv,dataHandler,c);


            }
        });



    }
}