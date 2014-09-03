package com.ar.BeerChopper.controller;

import android.content.Intent;

import com.ar.BeerChopper.Response.ServiceResponse;
import com.ar.BeerChopper.Response.ServiceResponseType;
import com.ar.BeerChopper.activity.DefaultActivity;
import com.ar.BeerChopper.http.HTTPManager;
import com.ar.BeerChopper.service.UpdateServiceBar;
import com.ar.BeerChopper.task.GetHTTPTask;
import com.google.gson.Gson;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class UpdaterControllerBar  {
	private static UpdaterControllerBar instance;

//	ACTIVIDAD QUE RECIBE LA NOTIFICACION DE CAMBIO EN EL MOMENTO
	private DefaultActivity activity;
	private Intent intent;

//	INTENT DEL SERVICIO, NECESARIO PARA START/STOP SERVICE
	private Intent updateServiceIntent;

//	HASH DE CAMBIOS
	private String hash = "";

	private boolean running = false;

	public static UpdaterControllerBar getInstance() {
		if (instance == null){
			instance = new UpdaterControllerBar();
		}
		return instance;
	}

	public synchronized boolean isRunning() {
		return running;
	}

	public synchronized void setRunning(boolean running) {
		this.running = running;
	}

	public Intent getUpdateServiceIntent() {
		return updateServiceIntent;
	}

	public void setUpdateServiceIntent(Intent updateServiceIntent) {
		this.updateServiceIntent = updateServiceIntent;
	}

	public DefaultActivity getActivity() {
		return activity;
	}

	public void setActivity(DefaultActivity activity) {
		this.activity = activity;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public void errorCheckStatusChopperTask(String failedMethod, ArrayList<Object> params){
		this.setRunning(false);
	}

	public void completeCheckStatusChopperTask(JSONObject jsonData, ArrayList<Object> params) {
		this.updateData(jsonData);
    }

	public synchronized void updateData(JSONObject jsonData){
		try{
            if (jsonData != null){
                ServiceResponse response = new Gson().fromJson(jsonData.toString(),ServiceResponse.class);
                if (response.getResponseType().equals(ServiceResponseType.CHOPPER_IS_IN_TRANSACTION)){
                    UpdaterControllerBar.getInstance().stopUpdateService();
                    WaitingController.getInstance().startService(this.getActivity());
                }
                else if (response.getResponseType().equals(ServiceResponseType.CHOPPER_IS_READY)){
                    UpdaterControllerBar.getInstance().stopUpdateService();
                    this.getActivity().finish();
                }
            }
		} catch (Exception e){
			e.printStackTrace();
		}
		this.setRunning(false);
	}

	/**
	 * @return the intent
	 */
	public Intent getIntent() {
		return intent;
	}

	/**
	 * @param intent the intent to set
	 */
	public void setIntent(Intent intent) {
		this.intent = intent;
	}


	public static void reset() {
		instance = null;
	}

	public void checkForStatus() {
		if (!this.isRunning()){
			this.setRunning(true);
            List<NameValuePair> postParams = new ArrayList<NameValuePair>();
            postParams.add(new BasicNameValuePair("chopperNumber", Long.toString(ChopperController.getInstance().getChopper().getId())));

            GetHTTPTask getHTTPTask = new GetHTTPTask();
            getHTTPTask.setReferer(this);
            getHTTPTask.setHttpMethodURL(HTTPManager.METHOD_CHECK_STATUS_CHOPPER);
            getHTTPTask.setPostParams(postParams);

            getHTTPTask.executeTask("completeCheckStatusChopperTask","errorCheckStatusChopperTask");
		}
	}

	public void startUpdateService(DefaultActivity activity){
		this.setUpdateServiceIntent(new Intent(activity, UpdateServiceBar.class));
		this.setActivity(activity);

//      INICIAR SERVICIOS DE ACTUALIZACION
        this.getActivity().startService(this.getUpdateServiceIntent());
	}

	public void stopUpdateService(){
		this.getActivity().stopService(this.getUpdateServiceIntent());
	}

}