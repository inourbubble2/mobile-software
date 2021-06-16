package com.example.mobliesoftware9;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class CommentScrollAdapter extends RecyclerView.Adapter<CommentScrollAdapter.VHolder> {

    Context context;

    public String[] users = {
            "user1",
            "user2",
            "user3"
    };

    public String[] comment = {
            "user1 comment",
            "user2 comment",
            "user3 comment"
    };

    public int[] userProfImg = {
            R.drawable.ic_baseline_account_circle_32,
            R.drawable.ic_baseline_account_circle_32,
            R.drawable.ic_baseline_account_circle_32
    };


    public CommentScrollAdapter(Context context) {
        this.context = context;
    }

    public VHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.scroll_comment_item, parent, false);
        return new VHolder(view);
    }

    @Override
    public void onBindViewHolder(VHolder holder, int position) {
        holder.userProfImage.setImageResource(userProfImg[position]);
        holder.usernameComment.setText(users[position]);
        holder.userComment.setText(comment[position]);
    }


    @Override
    public int getItemCount() {
        return users.length;
    }

    public class VHolder extends RecyclerView.ViewHolder {
        ImageView userProfImage;
        TextView usernameComment;
        TextView userComment;
        public VHolder(View itemView) {
            super(itemView);
            userProfImage = (ImageView) itemView.findViewById(R.id.imgCommentUser);
            usernameComment = (TextView) itemView.findViewById(R.id.usernameComment);
            userComment = (TextView) itemView.findViewById(R.id.userComment);
        };

    }

}
