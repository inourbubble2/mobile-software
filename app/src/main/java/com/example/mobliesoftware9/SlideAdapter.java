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

import org.w3c.dom.Text;

//readpost 내에서 유저들의 post slider
public class SlideAdapter extends PagerAdapter {
    Context context;
    LayoutInflater inflater;

    //list of test images
    public int[] list_imgs = {
            R.drawable.slide_test1,
            R.drawable.slide_test2,
            R.drawable.slide_test3
    };

    //list of posts
    public String[] posts = {
            "user1 post",
            "user2 post",
            "user3 post"
    };

    public String[] postUsername = {
            "user1",
            "user2",
            "user3"
    };

    public SlideAdapter(Context context) {
        this.context = context;
    }

    public int getCount() {
        return list_imgs.length;
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
        imgSlide.setImageResource(list_imgs[position]);
        postSlide.setText(posts[position]);
        postUser.setText(postUsername[position]);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout)object);
    }
}
