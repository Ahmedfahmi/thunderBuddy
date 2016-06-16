package com.ahmedfahmi.challenge.managers;

/**
 * Created by Ahmed Fahmi on 6/16/2016.
 */
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Ahmed Fahmi on 6/9/2016.
 */
public class ImageDownLoader extends AsyncTask<String, Void, Bitmap> {

    private Bitmap bitmap = null;
    private URL url;
    private HttpURLConnection connection;
    private InputStream inputStream;
    public  static ImageDownLoader imageDownLoader;

   private ImageDownLoader() {

    }
    public static ImageDownLoader instance(){
        if (imageDownLoader==null){
            imageDownLoader = new ImageDownLoader();
        }
        return imageDownLoader;
    }




    @Override
    protected Bitmap doInBackground(String... urls) {

        try {
            url = new URL(urls[0]);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            inputStream = (InputStream) connection.getInputStream();
            bitmap = BitmapFactory.decodeStream(inputStream);


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }
}