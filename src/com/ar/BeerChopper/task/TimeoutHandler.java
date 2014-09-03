package com.ar.BeerChopper.task;

import android.os.Handler;

public class TimeoutHandler extends Handler {
	private static TimeoutHandler instance;
	
	public static TimeoutHandler getInstance(){
		if (instance == null){
			instance = new TimeoutHandler();
		}
		return instance;
	}
}
