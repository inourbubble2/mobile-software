package com.example.mobliesoftware9;

import android.content.ClipData;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobliesoftware9.model.Post;

import java.util.List;

public class ScrollAdapter extends RecyclerView.Adapter<ScrollAdapter.VHolder> {

    Context context;
    Post[] dataSet;

    public ScrollAdapter(Context context, Post[] dataSet) {
        this.context = context;
        this.dataSet = dataSet;
    }

    public VHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.scroll_post_item, parent, false);
        return new VHolder(view);
    }

    @Override
    public void onBindViewHolder(VHolder holder, int position) {
        holder.postDate.setText(dataSet[position].createdAt.toString().substring(0,10));
        holder.postImage.setImageBitmap(dataSet[position].attachedImg.mBitmap);
        holder.userPost.setText(dataSet[position].title);
    }


    @Override
    public int getItemCount() {
        return dataSet != null ? dataSet.length : 0;
    }

    public class VHolder extends RecyclerView.ViewHolder {
        ImageView postImage;
        TextView postDate;
        TextView userPost;
        public VHolder(View itemView) {
            super(itemView);
            postImage = (ImageView) itemView.findViewById(R.id.slidePostImg);
            postDate = (TextView) itemView.findViewById(R.id.postDate);
            userPost = (TextView) itemView.findViewById(R.id.userPost);
        };

    }

}

