package com.example.mobliesoftware9.Image;

import android.graphics.Bitmap;

import java.net.URL;

public class LoadedImage
{
    public Bitmap mBitmap = null;
    //랜덤 이미지가 아닌 mBitmap의 url
    public String mImageURL;
    public boolean mSuccessLoad;

    public void LoadBitmapWithThisImageURL()
    {
        LoadedImage loadedImage = new ImageLoader().LoadImageFromURL(this.mImageURL);
        this.mBitmap = loadedImage.mBitmap;
        this.mSuccessLoad = loadedImage.mSuccessLoad;
    }

    public Bitmap GetBitmap()
    {
        if(mBitmap == null)
        {
            this.LoadBitmapWithThisImageURL();
        }
        return this.mBitmap;
    }


}
