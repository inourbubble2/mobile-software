package com.example.mobliesoftware9;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

import com.example.mobliesoftware9.DB.CursorWrapper;
import com.example.mobliesoftware9.DB.DatabaseManager;
import com.example.mobliesoftware9.model.Post;
import com.example.mobliesoftware9.model.User;

//readpost 내에서 유저들의 post slider
public class SlideAdapter extends PagerAdapter {
    Context context;
    LayoutInflater inflater;

    Post[] dataSet;

    public SlideAdapter(Context context, Post[] dataSet) {
        this.context = context;
        this.dataSet = dataSet;
    }

    public int getCount() {
        return dataSet != null ? dataSet.length : 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == (RelativeLayout)object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Post post = dataSet[position];

        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.slide, container, false);
        RelativeLayout layoutSlide = (RelativeLayout) view.findViewById(R.id.relativeLayoutSlide);
        ImageView imgSlide = (ImageView) view.findViewById(R.id.slideImg);
        ImageView imgPostUser = (ImageView) view.findViewById(R.id.imgPostUser);
        TextView postSlide = (TextView) view.findViewById(R.id.userPostTitle);
        TextView postContent = (TextView) view.findViewById(R.id.userPost);
        TextView postUser = (TextView) view.findViewById(R.id.postUsername);
        TextView likedCount = (TextView) view.findViewById(R.id.noOfLike);


        imgSlide.setImageBitmap(post.attachedImg.mBitmap);
        postSlide.setText(post.title);
        postContent.setText(post.content);
        postUser.setText(post.writerID);
        likedCount.setText(Integer.toString(post.likedCount));

        container.addView(view);

        Button btnLike = (Button) view.findViewById(R.id.btnLike);
        UpdateLikeButtonState(btnLike, post);

        Button btnComment = (Button) view.findViewById(R.id.btnComment);
        Button btnShare = (Button) view.findViewById(R.id.btnShare);


        // 작성자 프로필 사진 뜨게 하기
        CursorWrapper userCursor = DatabaseManager.GetInstance().SelectRows("User",
                null,
                new String[]{"username"},
                new String[]{post.writerID},
                null,
                null);
        userCursor.mCursor.moveToNext();
        User poster = new User();
        poster.LoadFromCursor(userCursor);

        if (poster.mProfileImage.mBitmap != null) {
            imgPostUser.setImageBitmap(poster.mProfileImage.mBitmap);
        }

        btnLike.setOnClickListener(v -> {
            if (post.OnClickLikeButton()) {
                likedCount.setText(Integer.toString(post.likedCount));
                Toast.makeText(context, "좋아요 버튼을 눌렀습니다.", Toast.LENGTH_SHORT).show();
            } else {
                likedCount.setText(Integer.toString(post.likedCount));
                Toast.makeText(context, "좋아요를 해제했습니다.", Toast.LENGTH_SHORT).show();
            }
            UpdateLikeButtonState(btnLike, post);
        });

        btnComment.setOnClickListener(v -> {
            Intent intent = new Intent(context, CommentActivity.class);
            intent.putExtra("mPrimaryKey", post.mPrimaryKey);
            v.getContext().startActivity(intent);
        });

        btnShare.setOnClickListener(v -> {
            post.SharePost(context);
        });

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout)object);
    }

    private void UpdateLikeButtonState(Button likeButton, Post post)
    {
        if(post.GetLocalUserLiked() == true)
        {
            likeButton.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
        }
        else
        {
            likeButton.setBackgroundTintList(ColorStateList.valueOf(Color.GRAY));
        }
    }

}
