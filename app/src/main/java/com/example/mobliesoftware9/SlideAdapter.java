package com.example.mobliesoftware9;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout)object);
    }
}
