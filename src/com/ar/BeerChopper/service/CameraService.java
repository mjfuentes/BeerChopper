package com.ar.BeerChopper.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.ar.BeerChopper.controller.CameraController;

import java.util.Timer;
import java.util.TimerTask;


public class CameraService extends Service {
    public static final long UPDATE_INTERVAL = 5000;
    public static final long DELAY_INTERVAL = 0;
    private Timer timer = new Timer();


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        takePictures();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        _shutdownService();
    }

    public IBinder onBind(Intent intent) {
        return null;
    }

    private void _shutdownService() {
        if (timer != null) {
            timer.cancel();
        }
    }

    private void takePictures()
    {
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                CameraController.getInstance().takePicture();
            }
        }, 0, UPDATE_INTERVAL);
    }
}
