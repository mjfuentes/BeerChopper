package com.ar.BeerChopper.task;

import android.content.Context;
import android.os.AsyncTask;

import java.util.Timer;

/**
 * Created by Matias_2 on 13/06/13.
 */
public class GlassUpdater extends AsyncTask<Context, Boolean,Object> {
    Context ctx;
    private Timer timer = new Timer();
    private boolean cont = true;

    @Override
    protected void onProgressUpdate(Boolean... values) {
        /*((WaitingActivity)ctx).UpdateImage();
        if (((WaitingActivity) ctx).getStatus())
        {
            cont = false;
        }*/
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
    }

    @Override
    protected Object doInBackground(Context... params) {
        /*ctx = params[0];
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                publishProgress(true);
                if (!cont) timer.cancel();
            }
        }, 0, 20);*/
        return null;
    }
}

