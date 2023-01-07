package com.sabanciuniv.cs310hw2_v0;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.concurrent.ExecutorService;

public class NewsRecViewAdapter extends RecyclerView.Adapter<NewsRecViewAdapter.NewsViewHolder> {

    private Context ctx;
    private List<News> data;
    ProgressBar pbFrag;

    public NewsRecViewAdapter(Context ctx, List<News> data, ProgressBar pbFrag) {
        this.ctx = ctx;
        this.data = data;
        this.pbFrag = pbFrag;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        pbFrag.setVisibility(View.VISIBLE);
        View root = LayoutInflater.from(ctx).inflate(R.layout.news_row_layout,parent,false);
        NewsViewHolder holder = new NewsViewHolder(root);
        holder.setIsRecyclable(false);
        return new NewsViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        holder.txtListTitle.setText(data.get(holder.getAdapterPosition()).getTitle());
        holder.txtListDate.setText(data.get(holder.getAdapterPosition()).getDate().toString());

        NewsApp app = (NewsApp)((AppCompatActivity)ctx).getApplication();

        holder.downloadImage(app.srv, data.get(holder.getAdapterPosition()).getImage());

        holder.row.setOnClickListener((v) -> {
            Intent i = new Intent(ctx,ActivityDetails.class);
            i.putExtra("id",data.get(holder.getAdapterPosition()).getId());
            ctx.startActivity(i);


        });


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class NewsViewHolder extends RecyclerView.ViewHolder{

        ImageView imgList;
        TextView txtListDate;
        TextView txtListTitle;
        ConstraintLayout row;
        boolean imageDownloaded;

        Handler imgHandler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message message) {

                Bitmap img = (Bitmap)message.obj;
                imgList.setImageBitmap(img);
                imageDownloaded = true;
                pbFrag.setVisibility(View.INVISIBLE);
                return true;
            }
        });


        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            imgList = itemView.findViewById(R.id.imgList);
            txtListDate = itemView.findViewById(R.id.txtListDate);
            txtListTitle = itemView.findViewById(R.id.txtListTitle);
            row = itemView.findViewById(R.id.newsRow);
        }

        public void downloadImage(ExecutorService srv, String path){

            if (!imageDownloaded){

                NewsRepository repo = new NewsRepository();
                repo.downloadImage(srv,imgHandler,path);
            }

        }

    }

}
