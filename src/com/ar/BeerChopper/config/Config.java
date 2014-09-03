package com.ar.BeerChopper.config;


import android.content.Context;
import android.content.SharedPreferences;

public class Config {

	private static Config instance = null;
	private SharedPreferences settings;
	private final static String _PREFS_FILE_NAME = "choperaDigital.properties";
	private Context context;
	
	public final static String PROPERTY_OPEN_DIALOG_PAYMENT = "OPEN_DIALOG_PAYMENT";
	public final static String PROPERTY_DEFAULT_CATEGORY = "DEFAULT_CATEGORY";
	public final static String PROPERTY_HOST = "HOST";

	public static Config getInstance() {
		return instance;
	}

	public static void init(Context aContext){
		if (instance == null) {
			instance = new Config(aContext);
		}
	}

	private Config(Context aContext){
		super();
		this.context = aContext;
		this.readProperties();
	}

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}
	
	public SharedPreferences getSettings() {
		return settings;
	}

	public void setSettings(SharedPreferences settings) {
		this.settings = settings;
	}

	public String getStringProperty(String property) {
		return this.getSettings().getString(property, "");
	}

	public Long getLongProperty(String property) {
		return this.getSettings().getLong(property, -1);
	}
	
	public Boolean getBooleanProperty(String property) {
		return this.getSettings().getBoolean(property, false);
	}

	private void readProperties() {
		this.setSettings(this.getContext().getSharedPreferences(_PREFS_FILE_NAME, Context.MODE_PRIVATE));
	}

	public void configure(String key, String value){
		SharedPreferences.Editor editor = this.getSettings().edit();
	    editor.putString(key, value);
	    editor.commit();
	}
	
	public void configure(String key, boolean value){
		SharedPreferences.Editor editor = this.getSettings().edit();
	    editor.putBoolean(key, value);
	    editor.commit();
	}

	public void configure(String key, long value) {
		SharedPreferences.Editor editor = this.getSettings().edit();
	    editor.putLong(key, value);
	    editor.commit();
	}
}
