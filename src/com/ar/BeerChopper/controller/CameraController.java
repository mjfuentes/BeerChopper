package com.ar.BeerChopper.controller;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;

public class CameraController {
    private static CameraController instance;
    private Camera camera;
    private Context mContext;
    public static CameraController getInstance()
    {
        if (instance == null)
        {
            instance = new CameraController();
        }
        return instance;
    }

    public static void reset(){
        instance = null;
    }

    public Camera getCamera() {
        return camera;
    }

    public void setCamera(Camera cam)
    {
        camera = cam;
    }

    public Context getmContext() {
        return mContext;
    }

    /**
     * @param mContext the mContext to set
     */
    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }

    public void takePicture()
    {
        camera.takePicture(myShutterCallback,myPictureCallback_RAW,myPictureCallback_JPG);
    }

    Camera.ShutterCallback myShutterCallback = new Camera.ShutterCallback(){

        @Override
        public void onShutter() {
            // TODO Auto-generated method stub

        }};

    Camera.PictureCallback myPictureCallback_RAW = new Camera.PictureCallback(){

        @Override
        public void onPictureTaken(byte[] arg0, Camera arg1) {
            // TODO Auto-generated method stub

        }};

    Camera.PictureCallback myPictureCallback_JPG = new Camera.PictureCallback(){

        @Override
        public void onPictureTaken(byte[] arg0, Camera arg1) {
            // TODO Auto-generated method stub
            Bitmap bitmapPicture
                    = BitmapFactory.decodeByteArray(arg0, 0, arg0.length);
        }};



}
