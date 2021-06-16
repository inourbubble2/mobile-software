package com.example.mobliesoftware9;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.mobliesoftware9.R;
import com.example.mobliesoftware9.model.Post;

import org.w3c.dom.Text;

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
        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.slide, container, false);
        RelativeLayout layoutSlide = (RelativeLayout) view.findViewById(R.id.relativeLayoutSlide);
        ImageView imgSlide = (ImageView) view.findViewById(R.id.slideImg);
        TextView postSlide = (TextView) view.findViewById(R.id.userPost);
        TextView postUser = (TextView) view.findViewById(R.id.postUsername);

        Post post = dataSet[position];
        imgSlide.setImageBitmap(post.attachedImg.mBitmap);
        postSlide.setText(post.title);
        postUser.setText(post.writerID);
        container.addView(view);

        Button btnLike = (Button) view.findViewById(R.id.btnLike);
        Button btnComment = (Button) view.findViewById(R.id.btnComment);
        Button btnShare = (Button) view.findViewById(R.id.btnShare);

        btnLike.setOnClickListener(v -> {
            if (post.OnClickLikeButton()) {
                btnLike.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                Toast.makeText(v.getContext(), "좋아요 버튼을 눌렀습니다.", Toast.LENGTH_SHORT);
            } else {
                btnLike.setBackgroundTintList(ColorStateList.valueOf(Color.GRAY));
                Toast.makeText(v.getContext(), "좋아요를 해제했습니다.", Toast.LENGTH_SHORT);
            }

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
}
