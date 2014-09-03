package com.ar.BeerChopper.controller;

import android.app.Activity;
import android.content.Intent;

import com.ar.BeerChopper.Response.ServiceResponseType;
import com.ar.BeerChopper.Response.StillConsumingResponse;
import com.ar.BeerChopper.activity.DefaultActivity;
import com.ar.BeerChopper.activity.LastConsumeActivity;
import com.ar.BeerChopper.activity.WaitingActivity;
import com.ar.BeerChopper.http.HTTPManager;
import com.ar.BeerChopper.service.WaitingService;
import com.ar.BeerChopper.task.GetHTTPTask;
import com.google.gson.Gson;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class WaitingController {
	private static WaitingController instance;
	
	private DefaultActivity activity;
	private Intent intent;
	private boolean running = false;
    private int lastPercent = 0;

	public static WaitingController getInstance() {
		if (instance == null){
			instance = new WaitingController();
		}
		return instance;
	}

    public void setLastPercent(int lastPercent) {
        this.lastPercent = lastPercent;
    }

    public int getLastPercent() {
        return lastPercent;
    }
	
	public synchronized boolean isRunning() {
		return running;
	}

	public synchronized void setRunning(boolean running) {
		this.running = running;
	}
	
	public DefaultActivity getActivity() {
		return activity;
	}

	public void setActivity(DefaultActivity activity) {
		this.activity = activity;
	}

	public void startService(Activity activity){
		this.setActivity((DefaultActivity) activity);
		this.setIntent(new Intent(activity, WaitingService.class));
		this.getActivity().startService(this.getIntent());		
	}
	
	public void stopService(){
		this.getActivity().stopService(this.getIntent());		
	}
	
	public void checkIsAvailable(){
		if (!this.isRunning()){
			this.setRunning(true);

            List<NameValuePair> postParams = new ArrayList<NameValuePair>();
            postParams.add(new BasicNameValuePair("cardNumber", String.valueOf(UserControllerBar.getInstance().getClient().getCurrentCard().getNumber())));
            postParams.add(new BasicNameValuePair("chopperNumber", String.valueOf(ChopperController.getInstance().getChopper().getId())));

            GetHTTPTask getHTTPTask = new GetHTTPTask();
			getHTTPTask.setReferer(this);
			getHTTPTask.setHttpMethodURL(HTTPManager.METHOD_CHECK_STATUS_AVAILABLE);
			getHTTPTask.setPostParams(postParams);
			
			getHTTPTask.executeTask("completeCheckForStatus","errorCheckForStatus");
		}
	}
	
	public void errorCheckForStatus(String failedMethod, ArrayList<Object> params){
		this.setRunning(false);
        this.stopService();
        this.getActivity().finish();
	}
	
	public void completeCheckForStatus(JSONObject jsonData, ArrayList<Object> params) {
		this.updateData(jsonData);
	}
	
	public synchronized void updateData(JSONObject jsonData){
		try{
            if (jsonData != null){
                StillConsumingResponse response = new Gson().fromJson(jsonData.toString(), StillConsumingResponse.class);
                if (response.getServiceResponse().getResponseType().equals(ServiceResponseType.CHOPPER_IS_READY)){
                    if (WaitingController.getInstance().isRunning())
                    {
                        setRunning(false);
                        WaitingController.getInstance().stopService();
                        this.getActivity().finish();
                        if (!UserControllerBar.getInstance().isAdmin()){
                            UserControllerBar.getInstance().updateUserAmount(response.getUserAmount(), response.getLastUse());
                            this.getActivity().startActivity(LastConsumeActivity.class, true);
                        }
                        WaitingController.getInstance().reset();
                    }
                }
                else if ((response.getServiceResponse().getResponseType().equals(ServiceResponseType.CHOPPER_IS_WORKING)) && !UserControllerBar.getInstance().isAdmin() && (response.getTransactionPercentage() > 0)){
                    this.setLastPercent(Math.round(response.getTransactionPercentage()));
                    ((WaitingActivity)this.getActivity()).UpdateImage(this.getLastPercent());
                }

            }
		} catch (Exception e){
			e.printStackTrace();
		}
		this.setRunning(false);
	}

	public Intent getIntent() {
		return intent;
	}

	public void setIntent(Intent intent) {
		this.intent = intent;
	}

	public static void reset() {
		instance = null;
	}


}