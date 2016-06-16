package com.ahmedfahmi.challenge.managers;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.util.concurrent.ExecutionException;

/**
 * Created by Ahmed Fahmi on 6/16/2016.
 */
public class ImageManager {

    private ImageDownLoader imageDownLoader;

    public static ImageManager imageManager;

    private ImageManager() {
        imageDownLoader = ImageDownLoader.instance();
    }

    public static ImageManager instance() {
        if (imageManager == null) {
            imageManager = new ImageManager();
        }
        return imageManager;
    }


    public String convertBitmapToString(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] bytes = stream.toByteArray();
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }

    public Bitmap toBitmap(String string) {

        byte[] imageAsBytes = Base64.decode(string.getBytes(), Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);

        return bitmap;
    }

    public Bitmap getBitmap(String url){
        Bitmap image = null;
        try {
           image = imageDownLoader.execute(url).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return image;
    }

}
