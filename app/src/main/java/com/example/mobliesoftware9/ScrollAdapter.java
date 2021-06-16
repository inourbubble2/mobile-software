package com.example.mobliesoftware9;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobliesoftware9.model.Post;

import org.w3c.dom.Text;

import java.util.List;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

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
        Post post = dataSet[position];
        holder.postDate.setText(post.createdAt.toString().substring(0,10));
        holder.postImage.setImageBitmap(post.attachedImg.mBitmap);
        holder.userPost.setText(post.title);

        holder.editPost.setOnClickListener(view -> {
            Intent intent = new Intent(context, CreatePostActivity.class);
            intent.putExtra("mPrimaryKey", post.mPrimaryKey);
            intent.putExtra("imgUrl", post.attachedImg.mImageURL);
            intent.putExtra("title", post.title);
            intent.putExtra("content", post.content);
            view.getContext().startActivity(intent.addFlags(FLAG_ACTIVITY_NEW_TASK));
        });

        // 삭제 기능 구현
        holder.deletePost.setOnClickListener(view -> {
            dataSet[position].DeleteFromDBWithPrimaryKey();
            Toast.makeText(context,"삭제가 완료되었습니다.", Toast.LENGTH_SHORT);
            Log.d("ScrollAdapter",dataSet[position].mPrimaryKey + " 삭제 완료");
        });
    }


    @Override
    public int getItemCount() {
        return dataSet != null ? dataSet.length : 0;
    }

    public class VHolder extends RecyclerView.ViewHolder {
        ImageView postImage;
        TextView postDate;
        TextView userPost;
        Button editPost;
        Button deletePost;
        public VHolder(View itemView) {
            super(itemView);
            postImage = (ImageView) itemView.findViewById(R.id.slidePostImg);
            postDate = (TextView) itemView.findViewById(R.id.postDate);
            userPost = (TextView) itemView.findViewById(R.id.userPost);
            editPost = (Button) itemView.findViewById(R.id.editPost);
            deletePost = (Button) itemView.findViewById(R.id.deletePost);
        };

    }

}

