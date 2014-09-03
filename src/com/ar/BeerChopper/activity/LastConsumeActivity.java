package com.ar.BeerChopper.activity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.ar.BeerChopper.R;
import com.ar.BeerChopper.controller.UserControllerBar;

public class LastConsumeActivity extends DefaultActivity {
    @Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.last_consume);

                //FONTS
		Typeface fontMuseoSlab300 = Typeface.createFromAsset(getBaseContext()
                .getAssets(), getString(R.string.font_museo_slab_300));
		Typeface fontMuseoSlab700 = Typeface.createFromAsset(getBaseContext()
                .getAssets(), getString(R.string.font_museo_slab_700));
		
		//HEADER
		TextView textUserName = (TextView) findViewById(R.id.text_user_name);
		TextView textUserThankYou = (TextView) findViewById(R.id.text_user_thank_you);
		
		textUserName.setText(UserControllerBar.getInstance().getClient().getUserName()+"!");
		
		//FONTS HEADER
		textUserThankYou.setTypeface(fontMuseoSlab300, Typeface.ITALIC);
		textUserName.setTypeface(fontMuseoSlab700, Typeface.ITALIC);
		
		//Last Consume
		TextView textLastConsume = (TextView) findViewById(R.id.your_last_consume);
		TextView textLastConsumeAmount = (TextView) findViewById(R.id.your_last_consume_amount);
		TextView textYourAmount = (TextView) findViewById(R.id.your_balance);
		TextView currentAmount = (TextView) findViewById(R.id.current_amount);
		TextView inYourAccount = (TextView) findViewById(R.id.in_your_account);
		
		//Text Last Consume
		textLastConsume.setText(getString(R.string.your_last_consume_was)+" ");
		textLastConsumeAmount.setText(" $ "+ String.valueOf(UserControllerBar.getInstance().getClient().getLastConsume()));
		textYourAmount.setText(getString(R.string.there_are)+" ");
		currentAmount.setText(" $ "+ String.format("%.2f",UserControllerBar.getInstance().getClient().getCash())+" ");
		inYourAccount.setText(getString(R.string.your_account)+" ");
		
		//Fonts Last Consume
		textLastConsume.setTypeface(fontMuseoSlab300, Typeface.ITALIC);
		textLastConsumeAmount.setTypeface(fontMuseoSlab700, Typeface.ITALIC);
		textYourAmount.setTypeface(fontMuseoSlab300, Typeface.ITALIC);
		currentAmount.setTypeface(fontMuseoSlab700, Typeface.ITALIC);
		inYourAccount.setTypeface(fontMuseoSlab300, Typeface.ITALIC);
		
		new Handler().postDelayed(new Runnable() {
            public void run() {
                LastConsumeActivity.this.finish();
            }
        }, 5000);
	}    

}