package com.ar.BeerChopper.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ar.BeerChopper.R;
import com.ar.BeerChopper.Response.ServiceResponse;
import com.ar.BeerChopper.Response.ServiceResponseType;
import com.ar.BeerChopper.controller.ChopperController;
import com.ar.BeerChopper.controller.UserControllerBar;
import com.ar.BeerChopper.http.HTTPManager;
import com.ar.BeerChopper.model.Chopper;
import com.ar.BeerChopper.model.MeasureNames;
import com.ar.BeerChopper.model.MeasureState;
import com.ar.BeerChopper.task.GetHTTPTask;
import com.google.gson.Gson;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class MainActivityBar extends DefaultActivity {

    private int timeOut;
    private Handler handler;
    private Runnable runnable;


    @Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_bar);

		final Activity activity = this;

//      INICIAR SERVICIOS DE ACTUALIZACION
		timeOut = 8000;

		//FONTS
		Typeface fontMuseoSlab300 = Typeface.createFromAsset(getBaseContext()
                .getAssets(), getString(R.string.font_museo_slab_300));
		Typeface fontMuseo700 = Typeface.createFromAsset(getBaseContext()
                .getAssets(), getString(R.string.font_museo_700));
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
		textUserAmount.setText("$"+ String.format("%.2f",UserControllerBar.getInstance().getClient().getCash()));

		//HEADER BEER CONTAINER
		TextView chooseMeassure = (TextView) findViewById(R.id.text_choose_measure);

		//FONT HEADER BEER CONTAINER
		chooseMeassure.setTypeface(fontMuseo700, Typeface.NORMAL);

//		BUTTON LARSEN RC1
		ImageButton info = (ImageButton) findViewById(R.id.larsen_info);

//		BUTTONS BEERS
		final RelativeLayout degustacion = (RelativeLayout) findViewById(R.id.degustacion);
		final RelativeLayout mediaPinta = (RelativeLayout) findViewById(R.id.media_pinta);
		final RelativeLayout chopp = (RelativeLayout) findViewById(R.id.chopp);
		final RelativeLayout pinta = (RelativeLayout) findViewById(R.id.pinta);

//		LAYOUT BEERS
		//layoutBeers = (LinearLayout) findViewById(R.id.layout_beers);

		//FOOTER BEER CONTAINER
		//TextView putGlass = (TextView) findViewById(R.id.text_put_glass);

		//TEXT FOOTER BEER CONTAINER
		//putGlass.setTypeface(fontMuseo700, Typeface.NORMAL);

		//DEFAULT SELECTION
//        Measure defaultMeasure = this.getDefaultMeasure();
//        if (defaultMeasure != null){
//            selectedMeasure = defaultMeasure.getName();
//        }
//        else {
//            selectedMeasure = "Pinta";
//        }

        info.setOnClickListener(new View.OnClickListener() {

			public void onClick(View arg0) {
                handler.removeCallbacks(runnable);
				final Dialog infoPopup = new Dialog(activity);
				infoPopup.setContentView(R.layout.info_popup);

				infoPopup.getWindow().setLayout((int)(MainActivityBar.this.getWindow().peekDecorView().getWidth()*0.98),(int) (MainActivityBar.this.getWindow().peekDecorView().getHeight()*0.98));

				ImageView imageView = (ImageView) infoPopup.findViewById(R.id.background_info_popup);
				imageView.setImageResource(R.drawable.popuplarsenrcone);
				imageView.setBackgroundResource(R.color.transparent);
				ImageButton imageButton = (ImageButton) infoPopup.findViewById(R.id.close_info_popup);
				imageButton.setOnClickListener(new View.OnClickListener() {

					public void onClick(View arg0) {
						infoPopup.cancel();
					}
				});
				infoPopup.show();
				new Handler().postDelayed(new Runnable() {
					public void run() {
						infoPopup.cancel();
					}
				}, 4000);

			}
		});

		//METHODS BEER BUTTONS
        if (this.getCurrentChopper().getMeasures().containsKey(MeasureNames.TASTING.getName()) &&
                this.getCurrentChopper().getStates().get(MeasureNames.TASTING.getName()).equals(MeasureState.ACTIVE)){
            ((TextView)degustacion.findViewById(R.id.degustacion_precio)).setText("$" + String.valueOf(Math.round(this.getCurrentChopper().getMeasures().get(MeasureNames.TASTING.getName()).get(0).getPrice())));
            degustacion.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					timeOut += 8000;
                    findViewById(R.id.seleccion_degustacion).setVisibility(View.VISIBLE);
					MainActivityBar.this.sendPresentationSelected(MeasureNames.TASTING.getName());
				}

			});
		}else{
            ((ImageView)degustacion.findViewById(R.id.degustacion_image)).setImageResource(R.drawable.degustacionoff);
            degustacion.findViewById(R.id.degustacion_precio).setVisibility(View.INVISIBLE);
		}

        if (this.getCurrentChopper().getMeasures().containsKey(MeasureNames.HALFPINT.getName()) &&
                this.getCurrentChopper().getStates().get(MeasureNames.HALFPINT.getName()).equals(MeasureState.ACTIVE)){
            ((TextView)mediaPinta.findViewById(R.id.media_pinta_precio)).setText("$" +String.valueOf(Math.round(this.getCurrentChopper().getMeasures().get(MeasureNames.HALFPINT.getName()).get(0).getPrice())));
            mediaPinta.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					timeOut += 8000;
                    findViewById(R.id.seleccion_mediapinta).setVisibility(View.VISIBLE);
					MainActivityBar.this.sendPresentationSelected(MeasureNames.HALFPINT.getName());
                }

			});
		} else{
            ((ImageView)mediaPinta.findViewById(R.id.media_pinta_image)).setImageResource(R.drawable.mediapintaoff);
            mediaPinta.findViewById(R.id.media_pinta_precio).setVisibility(View.INVISIBLE);
        }

        if (this.getCurrentChopper().getMeasures().containsKey(MeasureNames.JAR.getName()) &&
                this.getCurrentChopper().getStates().get(MeasureNames.JAR.getName()).equals(MeasureState.ACTIVE)){
            ((TextView)chopp.findViewById(R.id.chopp_precio)).setText("$" +String.valueOf(Math.round(this.getCurrentChopper().getMeasures().get(MeasureNames.JAR.getName()).get(0).getPrice())));
            chopp.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					timeOut += 8000;
                    findViewById(R.id.seleccion_chopp).setVisibility(View.VISIBLE);
					MainActivityBar.this.sendPresentationSelected(MeasureNames.JAR.getName());
                }

			});
		} else {
            ((ImageView)chopp.findViewById(R.id.chopp_image)).setImageResource(R.drawable.choppoff);
            chopp.findViewById(R.id.chopp_precio).setVisibility(View.INVISIBLE);

        }

        if (this.getCurrentChopper().getMeasures().containsKey(MeasureNames.PINT.getName()) &&
                this.getCurrentChopper().getStates().get(MeasureNames.PINT.getName()).equals(MeasureState.ACTIVE)){
            ((TextView)pinta.findViewById(R.id.pinta_precio)).setText("$" +String.valueOf(Math.round(this.getCurrentChopper().getMeasures().get(MeasureNames.PINT.getName()).get(0).getPrice())));
            pinta.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					timeOut += 8000;
                    findViewById(R.id.pinta).setVisibility(View.VISIBLE);
					MainActivityBar.this.sendPresentationSelected(MeasureNames.PINT.getName());
                }

			});
		}else {
            ((ImageView)pinta.findViewById(R.id.pinta_image)).setImageResource(R.drawable.pintaoff);
            pinta.findViewById(R.id.pinta_precio).setVisibility(View.INVISIBLE);

        }

		ImageButton buttonExit = (ImageButton) findViewById(R.id.button_exit);

		buttonExit.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				MainActivityBar.this.logout(arg0);
			}
		});
		setTimeOutScreen();


	}

	/**
	 *
	 */
	public void setTimeOutScreen() {
        runnable = new Runnable() {
            public void run() {
                MainActivityBar.this.finish();
            }
        };
        handler = new Handler();
		handler.postDelayed(runnable,timeOut);
	}

    private Chopper getCurrentChopper(){
        return ChopperController.getInstance().getChopper();
    }

	private void sendPresentationSelected(String selectedMeasure) {
        handler.removeCallbacks(runnable);

        List<NameValuePair> postParams = new ArrayList<NameValuePair>();
        postParams.add(new BasicNameValuePair("chopperNumber", String.valueOf(ChopperController.getInstance().getChopper().getId())));
        postParams.add(new BasicNameValuePair("selectedMeasureName",selectedMeasure));

		GetHTTPTask getHTTPTask = new GetHTTPTask();
		getHTTPTask.setReferer(this);
		getHTTPTask.setHttpMethodURL(HTTPManager.METHOD_SEND_DATA_ORDER);
		getHTTPTask.setPostParams(postParams);

		getHTTPTask.executeTask("completeSendPresentationSelectedTask","errorSendPresentationSelectedTask");
	}

	public void completeSendPresentationSelectedTask(JSONObject jsonData, ArrayList<Object> params) {
        if (jsonData!=null){
            ServiceResponse response = new Gson().fromJson(jsonData.toString(),ServiceResponse.class);
            if (response.getResponseType().equals(ServiceResponseType.MEASURE_SELECTED)){
                Intent intent = new Intent(this, WaitingActivity.class);
                startActivity(intent);
                this.finish();
            } else {
                Toast.makeText(this,response.getResponseType().getMessage(),Toast.LENGTH_SHORT).show();
                this.finish();
            }
		}
	}

	public void errorSendPresentationSelectedTask(String failedMethod,
			ArrayList<Object> params) {
            Toast.makeText(this,"error: " + failedMethod,5).show();

    }

	public void logout(View v) {
    	UserControllerBar.getInstance().setCardToCheck(null);
    	UserControllerBar.reset();
		MainActivityBar.this.finish();
	}


}