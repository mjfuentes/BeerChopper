package com.ar.BeerChopper.task;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import com.ar.BeerChopper.activity.LoginActivity;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Matias_2 on 17/06/13.
 */
public class ImageLoaderTask extends AsyncTask<String,Object,Bitmap> {

    LoginActivity activity;
    public ImageLoaderTask(LoginActivity act)
    {
        activity = act;
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        try{
            URL url = new URL(params[0]);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inTempStorage = new byte[32 * 1024];
            // Disable Dithering mode
            options.inDither = false;
            // Tell to gc that whether it needs free memory, the Bitmap can
            // be cleared
            options.inPurgeable = true;
            // Which kind of reference will be used to recover the Bitmap
            // data after being clear, when it will be used in the future
            options.inInputShareable = true;
            return BitmapFactory.decodeStream((InputStream)new URL(params[0]).getContent(),null,options);
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Bitmap o) {
        if (o != null)
        {
            activity.addImage(o);
        }
    }
}
