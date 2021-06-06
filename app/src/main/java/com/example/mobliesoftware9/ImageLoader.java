package com.example.mobliesoftware9;

import android.util.Log;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import java.io.InputStream;



public class ImageLoader extends AsyncTask<String, Void, Bitmap>
{
    @Override
    protected Bitmap doInBackground(String[] urls) {
        String urldisplay = urls[0];
        Bitmap imageBitmap = null;
        try {
            InputStream in = new java.net.URL(urldisplay).openStream();
            imageBitmap = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return imageBitmap;
    }

    //사용법 :
    // ImageView imageView;
    // Bitmap newImageBitmap = new ImageLoader().LoadImageFromPicsum(width, height);
    // imageView.setImageBitmap(newImageBitmap);
    // Image 로드 완료될 때 까지 block 됩니다.
    public Bitmap LoadImageFromPicsum(int width, int height)
    {
        String picsumUrl = "https://picsum.photos/";
        picsumUrl += width;
        picsumUrl += "/";
        picsumUrl += height;

        try
        {
            return this.execute(picsumUrl).get();
        }
        catch (Exception e)
        {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}
