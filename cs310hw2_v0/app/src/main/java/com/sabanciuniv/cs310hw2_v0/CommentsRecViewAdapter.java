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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.concurrent.ExecutorService;

public class CommentsRecViewAdapter extends RecyclerView.Adapter<CommentsRecViewAdapter.CommentsViewHolder> {

    private Context ctx;
    private List<Comment> data;

    public CommentsRecViewAdapter(Context ctx, List<Comment> data) {
        this.ctx = ctx;
        this.data = data;
        Log.i("DEV", "CommentsRecViewAdapter constructor data size: " + String.valueOf(data.size()));
    }

    @NonNull
    @Override
    public CommentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.i("DEV", "----------------onCreateViewHolder: ");
        View root = LayoutInflater.from(ctx).inflate(R.layout.comments_row_layout,parent,false);
        CommentsViewHolder holder = new CommentsViewHolder(root);
        holder.setIsRecyclable(false);
        return new CommentsViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentsViewHolder holder, int position) {
        holder.txtOwner.setText(data.get(holder.getAdapterPosition()).getName());
        holder.txtMsg.setText(data.get(holder.getAdapterPosition()).getText());

/*
        holder.row.setOnClickListener((v) -> {
            Intent i = new Intent(ctx,ActivityDetails.class);
            i.putExtra("id",data.get(holder.getAdapterPosition()).getId());
            ctx.startActivity(i);


        });
*/



    }

    @Override
    public int getItemCount() {
        Log.i("DEV", "getItemCount: " + String.valueOf(data.size()));
        return data.size();
    }

    class CommentsViewHolder extends RecyclerView.ViewHolder{

        TextView txtOwner;
        TextView txtMsg;
        ConstraintLayout row;

        public CommentsViewHolder(@NonNull View itemView) {
            super(itemView);
            Log.i("DEV", "----------------selammmmm: ");
            txtOwner = itemView.findViewById(R.id.txtOwner);
            txtMsg = itemView.findViewById(R.id.txtMsg);
            row = itemView.findViewById(R.id.row);
        }

    }

}
