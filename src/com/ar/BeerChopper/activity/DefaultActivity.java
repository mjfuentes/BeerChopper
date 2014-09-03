package com.ar.BeerChopper.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;

import com.ar.BeerChopper.R;

public abstract class DefaultActivity extends Activity {

    //region Private Fields
	private ProgressDialog progressDialog = null;
    private AlertDialog.Builder builder;

    //region Public Methods
	public ProgressDialog getProgressDialog() {
		return progressDialog;
	}

	public void setProgressDialog(ProgressDialog progressDialog) {
		this.progressDialog = progressDialog;
	}

	@SuppressWarnings("rawtypes")
	public void startActivity(Class activityClass, boolean reset,
			Bundle extraParams, int requestCode) {
		if (activityClass == this.getClass()) {
			return;
		}

		Intent intent = new Intent(DefaultActivity.this, activityClass);
		if (extraParams != null) {
			intent.putExtras(extraParams);
		}

		if (!reset) {
			intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		}

		if (requestCode > 0) {
			startActivityForResult(intent, requestCode);
		} else {
			startActivity(intent);
		}
	}

	@SuppressWarnings("rawtypes")
	public void startActivity(Class activityClass, boolean reset) {
		this.startActivity(activityClass, reset, null, -1);
	}

	@SuppressWarnings("rawtypes")
	public void startActivity(Class activityClass, boolean reset,
			Bundle extraParams) {
		this.startActivity(activityClass, reset, extraParams, -1);
	}


    public void onBackPressed() {
        return;
    }


	public void showProgressDialog(String msg) {
		if (!(this.getProgressDialog() != null && this.getProgressDialog()
				.isShowing())) {
			ProgressDialog pd = new ProgressDialog(this);
			pd.setCancelable(false);
			this.setProgressDialog(pd);
		}

		this.getProgressDialog().setMessage(msg);
		this.getProgressDialog().show();
	}

	public void hideProgressDialog() {
		if (this.getProgressDialog() != null
				&& this.getProgressDialog().isShowing()) {
			this.getProgressDialog().cancel();
		}
	}

	public boolean isDialog() {
		return false;
	}

	/**
	 * Muestra un error generico
	 */
	public void showDialogError(String mensaje, String title) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(title)
				.setMessage(mensaje)
				.setPositiveButton(R.string.aceptar,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dialog.cancel();
							}
						});
		AlertDialog alert = builder.create();
		alert.show();
		this.hideProgressDialog();
	}


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!this.isDialog()) {
            this.requestWindowFeature(Window.FEATURE_NO_TITLE);
            this.getWindow().setFlags(
                    WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }

        builder = new AlertDialog.Builder(this);
        String[] array = new String[] {"Configuracion","Creditos","Salir"};
        builder.setItems(array, new DialogInterface.OnClickListener()
        {

            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                switch (arg1)
                {
                    case 0:
                    {
                        Intent newIntent = new Intent(DefaultActivity.this,SettingsMenu.class);
                        startActivityForResult(newIntent,1);
                        break;
                    }
                    case 1:
                    {
                        break;
                    }

                    case 2:
                    {
                        finish();
                        break;
                    }
                }
            }

        });
    }

    public boolean onKeyDown(int keycode, KeyEvent e) {
        switch(keycode) {
            case KeyEvent.KEYCODE_MENU:
                AlertDialog dialog =  builder.create();
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                WindowManager.LayoutParams wmlp = dialog.getWindow().getAttributes();
                wmlp.gravity = Gravity.BOTTOM | Gravity.LEFT;
                dialog.show();
                return true;
        }

        return super.onKeyDown(keycode, e);
    }

}