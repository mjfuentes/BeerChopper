package com.ar.BeerChopper.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ar.BeerChopper.R;
import com.ar.BeerChopper.controller.UserControllerBar;

public class StyleSelection extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.style_selection);
        findViewById(R.id.rubia).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changePage();
            }
        });
        findViewById(R.id.roja).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changePage();
            }
        });

        ImageButton buttonExit = (ImageButton) findViewById(R.id.button_exit);
        buttonExit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                StyleSelection.this.finish();
            }
        });

        //FONTS
        Typeface fontMuseoSlab300 = Typeface.createFromAsset(getBaseContext()
                .getAssets(), getString(R.string.font_museo_slab_300));
        Typeface fontMuseoSlab700 = Typeface.createFromAsset(getBaseContext()
                .getAssets(), getString(R.string.font_museo_slab_700));

        //HEADER
        TextView textUserName = (TextView) findViewById(R.id.text_user_name);
        TextView textUserAmount = (TextView) findViewById(R.id.text_user_amount);
        TextView textUserWelcome = (TextView) findViewById(R.id.text_user_welcome);
        TextView textUserTextAmount = (TextView) findViewById(R.id.text_user_text_amount);

        //FONTS HEADER
        textUserWelcome.setTypeface(fontMuseoSlab300, Typeface.ITALIC);
        textUserTextAmount.setTypeface(fontMuseoSlab300, Typeface.ITALIC);
        textUserName.setTypeface(fontMuseoSlab700, Typeface.ITALIC);
        textUserAmount.setTypeface(fontMuseoSlab700, Typeface.ITALIC);

        //TEXT HEADER
        textUserWelcome.setText(getString(R.string.hello)+" ");
        textUserName.setText(UserControllerBar.getInstance().getClient().getUserName()+"!");
        textUserTextAmount.setText(getString(R.string.your_amount_is)+" ");
        textUserAmount.setText("$"+ UserControllerBar.getInstance().getClient().getCash());

    }

    public void changePage()
    {

        final Intent mainIntent = new Intent(StyleSelection.this,
                MainActivityBar.class);
        StyleSelection.this.startActivity(mainIntent);
        StyleSelection.this.finish();

    }
}