package com.ar.BeerChopper;
import org.acra.ACRA;
import org.acra.ReportingInteractionMode;
import org.acra.annotation.ReportsCrashes;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.graphics.Bitmap;

import com.ar.BeerChopper.config.Config;
import com.ar.BeerChopper.controller.ChopperController;
import com.ar.BeerChopper.model.Beer;
import com.ar.BeerChopper.model.Chopper;
import com.ar.BeerChopper.task.TimeoutHandler;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;

@ReportsCrashes(formKey = "dHVsbWt5TGMtVnpsaEl6TGJKM0lfWHc6MQ", mode=ReportingInteractionMode.SILENT)
public class BeerChopper extends Application {

    private static Context context;

    @Override
	public void onCreate() {
		// The following line triggers the initialization of ACRA
		if (!((getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE) > 0))
			ACRA.init(this);
		
		super.onCreate();

        context = this.getApplicationContext();

		// PREFERENCIAS
		Config.init(getApplicationContext());

		// POOL WI-FI
		TimeoutHandler.getInstance();


        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .defaultDisplayImageOptions(defaultOptions)
                .threadPoolSize(3)

                .build();

        ImageLoader.getInstance().init(config);
        Chopper chopper;
        if(Config.getInstance().getStringProperty("CHOPPER_NUMBER").equals("")){
            chopper = new Chopper(new Long(getResources().getString(R.string.config_default_chopper_number)), new ArrayList<Beer>());
        } else {
            chopper = new Chopper(new Long(Config.getInstance().getStringProperty("CHOPPER_NUMBER")), new ArrayList<Beer>());
        }

        ChopperController.getInstance().setChopper(chopper);

	}

    public static Context getContext()
    {
        return context;
    }

	/**
	 * RESETEA LA APP AL ESTADO INICIAL, INCLUYENDO LOS CONTROLADORES
	 */

}