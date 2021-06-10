package com.example.mobliesoftware9.Image;

import android.util.Log;

import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import java.io.InputStream;
import java.net.HttpURLConnection;


public class ImageLoader extends AsyncTask<String, Void, LoadedImage>
{
    @Override
    protected LoadedImage doInBackground(String[] urls) {
        String urldisplay = urls[0];
        LoadedImage newLoadedImage = new LoadedImage();
        try
        {
            HttpURLConnection con = (HttpURLConnection) new java.net.URL(urldisplay).openConnection();
            con.setInstanceFollowRedirects(true);
            con.connect();

            InputStream in = con.getInputStream();

            newLoadedImage.mImageURL = con.getURL().toString();
            newLoadedImage.mBitmap = BitmapFactory.decodeStream(in);
            newLoadedImage.mSuccessLoad = true;
        }
        catch (Exception e)
        {
            newLoadedImage.mSuccessLoad = false;

            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return newLoadedImage;
    }

    //사용법 :
    //
    // ImageView imageView;
    // LoadedImage image = new ImageLoader().LoadImageFromPicsum(200, 300);
    // imageView.setImageBitmap(image.mBitmap);
    //
    // Image 로드 완료될 때 까지 block 됩니다.
    public LoadedImage LoadRandomImage(int width, int height)
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
            Log.e("Fail to Load Image (LoadImageFromPicsum)", e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    //랜덤이미지가 아닌 특정 URL의 이미지를 원하는 경우
    //이미 로드한 이미지는 DB에 URL만 저장할 예정
    public LoadedImage LoadImageFromURL(String urlStr)
    {
        try
        {
            return this.execute(urlStr).get();
        }
        catch (Exception e)
        {
            Log.e("Fail to Load Image (LoadImageFromURL) : ", e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}
