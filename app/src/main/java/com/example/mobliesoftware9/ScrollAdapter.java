package com.example.mobliesoftware9;

import android.content.ClipData;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ScrollAdapter extends RecyclerView.Adapter<ScrollAdapter.VHolder> {

    Context context;

    public int[] list_imgs = {
            R.drawable.slide_test1,
            R.drawable.slide_test2,
            R.drawable.slide_test3
    };

    //list of posts
    public String[] posts = {
            "post1",
            "post2",
            "post3"
    };

    public String[] date = {
            "2021/6/13",
            "2021/6/12",
            "2021/6/11"
    };

    public ScrollAdapter(Context context) {
        this.context = context;
    }

    public VHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.scroll_post_item, parent, false);
        return new VHolder(view);
    }

    @Override
    public void onBindViewHolder(VHolder holder, int position) {
        holder.postDate.setText(date[position]);
        holder.postImage.setImageResource(list_imgs[position]);
        holder.userPost.setText(posts[position]);
    }


    @Override
    public int getItemCount() {
        return posts.length;
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

