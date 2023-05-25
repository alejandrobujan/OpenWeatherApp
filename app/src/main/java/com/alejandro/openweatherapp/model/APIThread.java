package com.alejandro.openweatherapp.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.alejandro.openweatherapp.model.APIRequest;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class APIThread extends Thread{
    private String query;
    private APIRequest request;
    private Bitmap imageBitmap;

    public APIThread(String query) {
        this.query = query;
    }

    @Override
    public void run() {
        super.run();
        this.request = new APIRequest(query);
        this.imageBitmap = getImageBitmap("https://openweathermap.org/img/wn/" + request.getIcon() + ".png");
    }

    private Bitmap getImageBitmap(String url) {
        Bitmap bm = null;
        try {
            URL aURL = new URL(url);
            URLConnection conn = aURL.openConnection();
            conn.connect();
            InputStream is = conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);
            bm = BitmapFactory.decodeStream(bis);
            bis.close();
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bm;
    }

    public APIRequest getRequest() {
        return request;
    }

    public Bitmap getImageBitmap() {
        return imageBitmap;
    }
}