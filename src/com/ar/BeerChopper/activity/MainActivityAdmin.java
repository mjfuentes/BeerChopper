package com.ar.BeerChopper.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.ar.BeerChopper.R;
import com.ar.BeerChopper.controller.UpdaterControllerBar;

public class MainActivityAdmin extends DefaultActivity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_main);
			
        ImageButton buttonExit = (ImageButton) findViewById(R.id.button_exit);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE);
        buttonExit.setOnClickListener(new View.OnClickListener() {
				public void onClick(View arg0) {
					MainActivityAdmin.this.logout(arg0);
				}
			});

        UpdaterControllerBar.getInstance().startUpdateService(this);
	}
    
    public void logout(View v) {
        UpdaterControllerBar.getInstance().stopUpdateService();
		MainActivityAdmin.this.finish();
	}
    
}