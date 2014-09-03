package com.ar.BeerChopper.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.ar.BeerChopper.controller.WaitingController;
import com.ar.BeerChopper.http.HTTPManager;
import com.ar.BeerChopper.task.GetHTTPTask;

import java.util.Timer;
import java.util.TimerTask;

public class WaitingService extends Service {
	public static final long UPDATE_INTERVAL = 500;
	public static final long DELAY_INTERVAL = 0;

	private Timer timer = new Timer();

	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		GetHTTPTask getHTTPTask = new GetHTTPTask();
		getHTTPTask.setReferer(this);
		getHTTPTask.setHttpMethodURL(HTTPManager.METHOD_CHECK_STATUS_AVAILABLE);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		_shutdownService();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		doServiceWork();
		return START_STICKY;
	}

	private void doServiceWork() {
		timer.scheduleAtFixedRate(new TimerTask() {
			public void run() {
				WaitingController.getInstance().checkIsAvailable();
			}
		}, 0, UPDATE_INTERVAL);

	}

	private void _shutdownService() {
		if (timer != null) {
			timer.cancel();
		}
	}

}