package com.example.mobliesoftware9;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.mobliesoftware9.DB.CursorWrapper;
import com.example.mobliesoftware9.DB.DatabaseManager;
import com.example.mobliesoftware9.model.Comment;
import com.example.mobliesoftware9.model.Post;
import com.example.mobliesoftware9.model.User;

import java.util.ArrayList;

public class CommentScrollAdapter extends RecyclerView.Adapter<CommentScrollAdapter.VHolder> {

    Context context;

    ArrayList<Comment> dataSet;
    Post post;

    public CommentScrollAdapter(Context context, ArrayList<Comment> dataSet, Post post) {
        this.context = context;
        this.dataSet = dataSet;
        this.post = post;
    }

    public VHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.scroll_comment_item, parent, false);
        return new VHolder(view);
    }

    @Override
    public void onBindViewHolder(VHolder holder, int position) {
        Comment comment = dataSet.get(position);
        CursorWrapper userCursor = DatabaseManager.GetInstance().SelectRows("User",
                null,
                new String[]{"username"},
                new String[]{comment.writerID},
                null,
                null);
        userCursor.mCursor.moveToNext();
        User commentUser = new User();
        commentUser.LoadFromCursor(userCursor);

        if (commentUser.mProfileImage.mBitmap != null) {
            holder.userProfImage.setImageBitmap(commentUser.mProfileImage.mBitmap);
        }

        holder.usernameComment.setText(comment.writerID);
        holder.userComment.setText(comment.mContent);

        String dateStr = Integer.toString(comment.createdAt.getYear()) +'/'+comment.createdAt.getMonthValue() +'/'+comment.createdAt.getDayOfMonth() ;
        holder.commentDate.setText(dateStr);

        holder.commentLike.setOnClickListener(view -> {
            if (comment.OnClickLikeButton()) {
                holder.commentLike.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                Toast.makeText(view.getContext(), "????????? ????????? ???????????????.", Toast.LENGTH_SHORT);
            } else {
                holder.commentLike.setBackgroundTintList(null);
                Toast.makeText(view.getContext(), "???????????? ??????????????????.", Toast.LENGTH_SHORT);

            }
        });
    }


    @Override
    public int getItemCount() {
        return dataSet != null ? dataSet.size() : 0;
    }

    public class VHolder extends RecyclerView.ViewHolder {
        ImageView userProfImage;
        TextView usernameComment;
        TextView userComment;
        TextView commentDate;
        Button commentLike;
        public VHolder(View itemView) {
            super(itemView);
            userProfImage = (ImageView) itemView.findViewById(R.id.imgCommentUser);
            usernameComment = (TextView) itemView.findViewById(R.id.usernameComment);
            userComment = (TextView) itemView.findViewById(R.id.userComment);
            commentDate = (TextView) itemView.findViewById(R.id.commentDate);

            commentLike = (Button) itemView.findViewById(R.id.commentLike);

        };

    }

}
