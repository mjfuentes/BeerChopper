package com.ar.BeerChopper.json;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class    JSONParser {
	
	public static final String FIELD_JSON_DESCRIPTION = "description";
	public static final String FIELD_JSON_AMOUNT = "amount";
	public static final String FIELD_JSON_EXTRA = "extra";
	public static final String FIELD_JSON_HASH = "checksum";
	public static final String FIELD_JSON_ID = "id";
	public static final String FIELD_JSON_USER_ID = "user_id";
	public static final String FIELD_JSON_USER = "user";
	public static final String FIELD_JSON_NAME = "name";
	public static final String FIELD_JSON_PRICE = "price";
	public static final String FIELD_JSON_STATUS = "status";
	public static final String FIELD_JSON_USERNAME = "username";
	public static final String FIELD_JSON_VERSION = "version";
	public static final String FIELD_JSON_LAST_USE = "last_use";
	public static final String FIELD_JSON_USER_NAME = "user_name";
	public static final String FIELD_JSON_USER_AMOUNT = "user_amount";
    public static final String FIELD_JSON_TRANSACTION_PERCENT = "transaction_percent";
    public static final String FIELD_JSON_MEASURES = "measures";
    public static final String FIELD_JSON_BEERS = "beers";
    public static final String FIELD_JSON_MEASURE_DEFAULT = "measure_default";
    public static final String FIELD_JSON_ACTIVE = "active";
	
	private final static Object get(JSONObject jSONData, String field){
		try {
			return jSONData.get(field);
		} catch (JSONException e) {
            e.printStackTrace();
			return null;
		}
	}
	
	public final static JSONObject getJSONObject(JSONArray jSONData, int index){
		try {
			return jSONData.getJSONObject(index);
		} catch (JSONException e) {
			return null;
		}
	}
	
	public final static JSONObject getJSONObject(JSONObject jSONData, String field){
		try {
			return jSONData.getJSONObject(field);
		} catch (JSONException e) {
			return null;
		}
		
	}
	
	public final static JSONArray getJSONArray(JSONObject jSONData, String field){
		return (JSONArray) get(jSONData, field);
	}
	
	public final static String getString(JSONObject jSONData, String field){
		String result = get(jSONData, field).toString();
		if (!result.equals("")){
			return result;
		} else {
			return "";
		}
	}
	
	public final static Long getLong(JSONObject jSONData, String field){
		try {
			return jSONData.getLong(field);
		} catch (JSONException e) {
			return (long)0;
		}
	}
	
	public final static Integer getInt(JSONObject jSONData, String field){
		try {
			return jSONData.getInt(field);
		} catch (JSONException e) {
			return (int)0;
		}
	}
	
	public final static Float getFloat(JSONObject jSONData, String field){
		try {
			return Float.parseFloat(jSONData.getString(field));
		} catch (JSONException e) {
			return (float)0;
		}
	}
	
	public final static Boolean getBoolean(JSONObject jSONData, String field){
		try {
			return jSONData.getBoolean(field);
		} catch (JSONException e) {
			return false;
		}
	}
	
	public final static JSONObject put(JSONObject jsonObject, String fieldName, Object element){
		try {
			jsonObject.put(fieldName, element);
		} catch (JSONException e) {
			return jsonObject;
		}
		return jsonObject;
	}
}
