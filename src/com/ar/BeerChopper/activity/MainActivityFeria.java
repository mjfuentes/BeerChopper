package com.ar.BeerChopper.activity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ar.BeerChopper.R;
import com.ar.BeerChopper.controller.UserControllerFeria;

public class MainActivityFeria extends DefaultActivity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feria_saldo);
        
        	//FONTS
      		Typeface fontMuseoSlab300 = Typeface.createFromAsset(getBaseContext()
      				.getAssets(), getString(R.string.font_museo_slab_300));
      		Typeface fontMuseoSlab700 = Typeface.createFromAsset(getBaseContext()
      				.getAssets(), getString(R.string.font_museo_slab_700));
      		Typeface fontMuseo700 = Typeface.createFromAsset(getBaseContext()
    				.getAssets(), getString(R.string.font_museo_700));
      		Typeface fontDsDigib = Typeface.createFromAsset(getBaseContext()
    				.getAssets(), getString(R.string.font_ds_digib));

      		//HEADER
      		TextView textUserThankYou = (TextView) findViewById(R.id.text_user_thank_you);

      		final TextView textGoesHere = (TextView) findViewById(R.id.textGoesHere);
      		textGoesHere.setTypeface(fontDsDigib);
			
      		CountDownTimer countDown = new CountDownTimer(9000, 1000) {
      			public void onTick(long millisUntilFinished) {
      				textGoesHere.setText("0"+(millisUntilFinished/1000));
      			}

				@Override
				public void onFinish() {
					cancel();
				}

  			}; 
  			countDown.start();
      		
    		//FONTS HEADER
    		textUserThankYou.setTypeface(fontMuseoSlab300, Typeface.ITALIC);

      		//Last Consume
      		TextView textLastConsume = (TextView) findViewById(R.id.your_last_use);
      		TextView textLastConsumeAmount = (TextView) findViewById(R.id.last_consume);
      		TextView textYourAmount = (TextView) findViewById(R.id.customer_balance);
      		TextView currentAmount = (TextView) findViewById(R.id.customer_balance_money);
      		//Fonts Last Consume
      		textLastConsume.setTypeface(fontMuseoSlab700, Typeface.ITALIC);
      		textLastConsumeAmount.setTypeface(fontMuseoSlab700, Typeface.ITALIC);
      		textYourAmount.setTypeface(fontMuseoSlab700, Typeface.ITALIC);
      		currentAmount.setTypeface(fontMuseoSlab700, Typeface.ITALIC);
            //userName.setTypeface(fontMuseoSlab700, Typeface.ITALIC);
      		//Text Last Consume
      		currentAmount.setText(" $ "+String.format("%.2f",UserControllerFeria.getInstance().getClient().getCash()));
      		textLastConsumeAmount.setText(" $ "+String.format("%.2f",UserControllerFeria.getInstance().getClient().getLastConsume()));
			new Handler().postDelayed(new Runnable() {
				public void run() {
					MainActivityFeria.this.finish();
				}
			}, 8000);
			
			ImageButton buttonExit = (ImageButton) findViewById(R.id.button_exit);

        buttonExit.setOnClickListener(new View.OnClickListener() {
				public void onClick(View arg0) {
					MainActivityFeria.this.logout(arg0);
				}
			});
	}
    
    public void logout(View v) {
		MainActivityFeria.this.finish();
	}
    
}