package com.ar.BeerChopper.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.ViewFlipper;
import com.ar.BeerChopper.R;
import com.ar.BeerChopper.controller.ChopperController;
import com.ar.BeerChopper.controller.UserControllerBar;
import com.ar.BeerChopper.controller.UserControllerFeria;
import com.ar.BeerChopper.http.HTTPManager;
import com.ar.BeerChopper.task.AlbumLoader;
import com.nostra13.universalimageloader.core.ImageLoader;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;

public class LoginActivity extends DefaultActivity {
	private ViewFlipper imageFrame;
    private int cant = 0;
	private EditText cardNumber;
    private float init_x;
    private String[] images;
    private TextWatcher textWacher;
    public AlertDialog dialog;
    private String mode;
    private ProgressDialog loadingDialog;

    @Override
    protected void onResume() {
        super.onResume();
        this.startReading();
        this.checkMeasures();
        imageFrame.startFlipping();
    }

    public void setImages(String json)
    {
        ArrayList<String> list = new ArrayList<String>();
        if (!json.equals(""))
        {
            try {
                JSONObject jObj = new JSONObject(json);
                JSONArray jArray = jObj.getJSONObject("data").getJSONArray("images");
                for (int i = 0; i<jArray.length();i++)
                {
                    JSONObject jImage = jArray.getJSONObject(i);
                    list.add(jImage.getString("link"));
                }
                images = list.toArray(new String[list.size()]);
                addFlipperImages();

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        else
        {
            LoadLocalImages();
        }
    }

    private void hideLoading() {
        (findViewById(R.id.progressBar)).setVisibility(View.INVISIBLE);
    }

    @Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        HTTPManager.host_name = PreferenceManager.getDefaultSharedPreferences(this).getString("HOST_NAME","");
        mode = PreferenceManager.getDefaultSharedPreferences(this).getString("APP_MODE","Ferias");
        String albumId = PreferenceManager.getDefaultSharedPreferences(this).getString("ALBUM_ID","");
        setContentView(R.layout.login);
		IntentFilter filter = new IntentFilter();
		filter.addAction(UsbManager.ACTION_USB_DEVICE_ATTACHED);
		filter.addAction(UsbManager.ACTION_USB_DEVICE_DETACHED);
        imageFrame = (ViewFlipper) findViewById(R.id.imageFrames);
        imageFrame.setDisplayedChild(2);
        loadingDialog = new ProgressDialog(LoginActivity.this);
        loadingDialog.setCancelable(false);
        loadingDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        loadingDialog.setMessage("Autenticando..");
        if (!albumId.equals(""))
        {
            AlbumLoader loader = new AlbumLoader(this);
            loader.execute();
        }
        else
        {
            LoadLocalImages();
        }
        imageFrame.setFlipInterval(7000);
        imageFrame.setInAnimation(inFromRightAnimation());
        imageFrame.setOutAnimation(outToLeftAnimation());
        imageFrame.startFlipping();
        imageFrame.setOnTouchListener(new OnTouchListener(){
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: //Cuando el usuario toca la pantalla por primera vez
                        init_x=event.getX();
                        imageFrame.stopFlipping();
                        return true;
                    case MotionEvent.ACTION_UP: //Cuando el usuario deja de presionar
                        float distance =init_x-event.getX();
                        if(distance>0)
                        {
                            imageFrame.showNext();
                            imageFrame.startFlipping();
                        }
                        if(distance<0)
                        {
                            imageFrame.setInAnimation(inFromLeftAnimation());
                            imageFrame.setOutAnimation(outToRightAnimation());
                            imageFrame.showPrevious();
                            imageFrame.startFlipping();
                            imageFrame.setInAnimation(inFromRightAnimation());
                            imageFrame.setOutAnimation(outToLeftAnimation());
                        }
                    default:
                        break;
                }
                return false;
            }

        });

        cardNumber = (EditText) findViewById(R.id.card_reader);
        textWacher = new TextWatcher() {
            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (cardNumber.getText().toString().length() == 11) {
                    if (mode.equals("Bares"))
                    {
                        stopReading();
                        UserControllerBar.getInstance().setActivity(LoginActivity.this);
                        UserControllerBar.getInstance().verifyUserByCardNumber(cardNumber.getText().toString());

                    }
                    else if (mode.equals("Ferias"))
                    {
                        stopReading();
                        UserControllerFeria.getInstance().setActivity(LoginActivity.this);
                        UserControllerFeria.getInstance().verifyUserByCardNumber(cardNumber.getText().toString());
                    }

                    else {
                        Toast.makeText(LoginActivity.this, "Application not ready.",Toast.LENGTH_SHORT).show();
                    }
                }
            }

        };
	}

    @Override
    protected void onPause() {
        super.onPause();
        if (dialog != null){
            dialog.hide();
        }
    }

    public void startReading()
    {
        cardNumber.setText("");
        cardNumber.addTextChangedListener(textWacher);
        loadingDialog.hide();
    }

    public void stopReading()
    {
        cardNumber.removeTextChangedListener(textWacher);
        loadingDialog.show();
        loadingDialog.getWindow().setLayout(250,80);
    }

    public void FailedLogin(String error)
    {
        this.startReading();
        Toast.makeText(LoginActivity.this, error,Toast.LENGTH_SHORT).show();
    }

    public void SuccesfulLogin()
    {
        imageFrame.stopFlipping();
        //startReading();
    }

    private void checkMeasures(){
        if ((ChopperController.getInstance().getBeerMeasures().size() == 0) && mode.equals("Bares")){
            ChopperController.getInstance().setmContext(this);
            ChopperController.getInstance().getBeerAndMeasures();
        }
    }

    public void failedConnection(String error){
        AlertDialog.Builder errorDialogBuilder = new AlertDialog.Builder(LoginActivity.this);
        errorDialogBuilder.setTitle("Error");
        errorDialogBuilder.setCancelable(false);
        errorDialogBuilder.setMessage(error);
        errorDialogBuilder.setNegativeButton("Configurar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent newIntent = new Intent(LoginActivity.this,SettingsMenu.class);
                startActivity(newIntent);
                dialogInterface.dismiss();
            }
        });
        errorDialogBuilder.setPositiveButton("Reintentar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (mode.equals("Bares")) {
                    ChopperController.getInstance().setmContext(LoginActivity.this);
                    ChopperController.getInstance().getBeerAndMeasures();
                }
                dialogInterface.dismiss();
                LoginActivity.this.startReading();
            }
        });
        errorDialogBuilder.create().show();
        cardNumber.removeTextChangedListener(textWacher);
    }


    private Animation inFromRightAnimation() {
        Animation inFromRight = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT,  +1.0f, Animation.RELATIVE_TO_PARENT,  0.0f,
                Animation.RELATIVE_TO_PARENT,  0.0f, Animation.RELATIVE_TO_PARENT,   0.0f );

        inFromRight.setDuration(300);
        inFromRight.setInterpolator(new AccelerateInterpolator());
        return inFromRight;
    }

    private Animation outToLeftAnimation() {
        Animation outtoLeft = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, -1.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f);
        outtoLeft.setDuration(300);
        outtoLeft.setInterpolator(new AccelerateInterpolator());
        return outtoLeft;
    }

    private Animation inFromLeftAnimation() {
        Animation inFromLeft = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, -1.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f);
        inFromLeft.setDuration(300);
        inFromLeft.setInterpolator(new AccelerateInterpolator());
        return inFromLeft;
    }

    private Animation outToRightAnimation() {
        Animation outtoRight = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, +1.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f);
        outtoRight.setDuration(300);
        outtoRight.setInterpolator(new AccelerateInterpolator());
        return outtoRight;
    }

    /*public void onActivityResult(int requestCode, int resultCode, Intent intent) {

        if (resultCode == RESULT_OK) {

            String contents = intent.getStringExtra("SCAN_RESULT");
            if (contents.length() == 10){
                cardNumber.setText(contents + "0");
            }
            else Toast.makeText(this, "Usuario invalido", Toast.LENGTH_LONG).show();
        }
        else if (resultCode == RESULT_CANCELED) {
            // Handle cancel
            Toast.makeText(this,"QR invalido",Toast.LENGTH_LONG).show();
        }
    }*/

    protected void addFlipperImages() {
        if (images.length == 0)
        {
            LoadLocalImages();
        }
        else
        {
            for (int i=0; i<images.length;i++)
            {

                ViewGroup.LayoutParams p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                ImageView iv = new ImageView(this);
                iv.setLayoutParams(p);
                ImageLoader.getInstance().displayImage(images[i], iv);
                imageFrame.addView(iv);
                imageFrame.setDisplayedChild(cant + 1);
                hideLoading();
            }
        }
    }

    protected void LoadLocalImages()
    {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT);
        for (int i = 2; i < 5; i++) {
            ImageView imageView = new ImageView(this);
            try {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inTempStorage = new byte[32 * 1024];
                options.inDither = false;
                options.inPurgeable = true;
                options.inInputShareable = true;

                Bitmap bitmap = BitmapFactory.decodeStream(
                        this.getAssets().open(this.getAssets().list("")[i]),
                        null, options);
                this.getAssets().open(this.getAssets().list("")[i]).close();
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
            imageView.setLayoutParams(params);
            imageFrame.addView(imageView);
            hideLoading();
        }
    }

    public void addImage(Bitmap bmp)
    {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT);
        ImageView imageView = new ImageView(this);
        imageView.setImageBitmap(bmp);
        imageView.setLayoutParams(params);
        imageFrame.addView(imageView);
        imageFrame.setDisplayedChild(cant+1);
    }

}