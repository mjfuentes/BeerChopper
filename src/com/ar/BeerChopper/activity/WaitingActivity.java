package com.ar.BeerChopper.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ar.BeerChopper.R;
import com.ar.BeerChopper.controller.UpdaterControllerBar;
import com.ar.BeerChopper.controller.UserControllerBar;
import com.ar.BeerChopper.controller.UserControllerFeria;
import com.ar.BeerChopper.task.GlassUpdater;

public class WaitingActivity extends DefaultActivity {
    private ImageView glass;
    private GlassUpdater task;
    private Boolean done = false;
    private Bitmap glassImage;
    private int lastPercent = 0;
    private int lastImageIndex = 0;
    private int[] glassImages = new int[5];
    //private ImageView glass_content;

    @Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.waiting_new);
		
		//FONTS
		Typeface fontMuseoSlab300 = Typeface.createFromAsset(getBaseContext()
                .getAssets(), getString(R.string.font_museo_slab_300));
		Typeface fontMuseoSlab700 = Typeface.createFromAsset(getBaseContext()
                .getAssets(), getString(R.string.font_museo_slab_700));

        //glass_content = (ImageView)findViewById(R.id.beer);
		
		//HEADER
		TextView textUserName = (TextView) findViewById(R.id.text_user_name);
		TextView textUserThankYou = (TextView) findViewById(R.id.text_user_thank_you);
        TextView textUserHelp = (TextView) findViewById(R.id.text_choose_measure);

        if (PreferenceManager.getDefaultSharedPreferences(WaitingActivity.this).getString("APP_MODE","").equals("Bares"))
        {
            textUserName.setText(UserControllerBar.getInstance().getClient().getUserName()+"!");
        }
        else
        {
            textUserName.setText(UserControllerFeria.getInstance().getClient().getUserName()+"!");
        }

		//FONTS HEADER
		textUserThankYou.setTypeface(fontMuseoSlab300, Typeface.ITALIC);
		textUserName.setTypeface(fontMuseoSlab700, Typeface.ITALIC);
        textUserHelp.setTypeface(fontMuseoSlab300, Typeface.ITALIC);



//      INICIAR SERVICIOS DE ACTUALIZACION
        UpdaterControllerBar.getInstance().startUpdateService(this);

        loadImages();
        glass = (ImageView)findViewById(R.id.vaso);
        glassImage = BitmapFactory.decodeResource(getResources(),glassImages[0]);
        glass.setImageBitmap(glassImage);
        //glass.setImageResource(R.drawable.vaso_vacio);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE);

        //task = new GlassUpdater();
        //task.execute(this);
	}    

    public void UpdateImage(int percent)
    {
        if ((percent>lastPercent) && (percent>0) && (percent<100)){
            int index = (percent / 30);
            lastPercent = percent;
            if (index > lastImageIndex)
            {
                lastImageIndex= index;
                glassImage.recycle();
                glassImage = BitmapFactory.decodeResource(getResources(),glassImages[index]);
                glass.setImageBitmap(glassImage);
            }
        }
    }

    public boolean getStatus()
    {
        return done;
    }

    private void loadImages()
    {
        glassImages = new int[]{R.drawable.uno,R.drawable.dos,R.drawable.tres,R.drawable.cuatro};
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        UpdaterControllerBar.getInstance().stopUpdateService();
    }





}