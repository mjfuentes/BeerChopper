package com.ar.BeerChopper.task;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONObject;

import android.os.AsyncTask;

import com.ar.BeerChopper.activity.DefaultActivity;
import com.ar.BeerChopper.http.HTTPManager;

public class GetHTTPTask extends AsyncTask<Object, Object, ArrayList<Object>>{
	
	//private Activity activity;
	private Object referer;
	private String httpMethodURL;
	private String httpParams;
	private List<NameValuePair> postParams;
    private DefaultActivity defaultActivity;

    public List<NameValuePair> getPostParams() {
		return postParams;
	}

	public void setPostParams(List<NameValuePair> postParams) {
		this.postParams = postParams;
	}

	private Method method;
	private ArrayList<Object> extraParams = new ArrayList<Object>();
	private ArrayList<Object> returnParams = new ArrayList<Object>();
	private Object HTTPResult; 
	private int poolCount = 1;
	private int httpStatus = -1;
	private String errorCallback;
	private int poolCountLimit = 3;
	
	public int getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(int httpStatus) {
		this.httpStatus = httpStatus;
	}

	private int getPoolCount() {
		return poolCount;
	}

	public Object getHTTPResult() {
		return HTTPResult;
	}

	public void setHTTPResult(Object hTTPResult) {
		HTTPResult = hTTPResult;
	}

	public String getHttpMethodURL() {
		return httpMethodURL;
	}

	public void setHttpMethodURL(String httpMethodURL) {
		this.httpMethodURL = httpMethodURL;
	}

	public Method getMethod() {
		return method;
	}

	public void setMethod(Method method) {
		this.method = method;
	}

	public ArrayList<Object> getExtraParams() {
		return extraParams;
	}

	public void setExtraParams(ArrayList<Object> extraParams) {
		this.extraParams = extraParams;
	}

	public ArrayList<Object> getReturnParams() {
		return returnParams;
	}

	public void setReturnParams(ArrayList<Object> returnParams) {
		this.returnParams = returnParams;
	}
	
	public String getErrorCallback() {
		return errorCallback;
	}

	public void setErrorCallback(String errorCallback) {
		this.errorCallback = errorCallback;
	}
	
	public Object getReferer() {
		return referer;
	}

	public void setReferer(Object referer) {
		this.referer = referer;
	}

    public void setActivity(DefaultActivity activity)
    {
        this.defaultActivity = activity;
    }

    public DefaultActivity getActivity()
    {
        return this.defaultActivity;
    }



	public int getPoolCountLimit() {
		return poolCountLimit;
	}

	public void setPoolCountLimit(int poolCountLimit) {
		this.poolCountLimit = poolCountLimit;
	}

	@Override
	protected ArrayList<Object> doInBackground(Object... params) {
		//PARAMETROS
		/*List<NameValuePair> postParams = new ArrayList<NameValuePair>(1);

    	postParams.add(new BasicNameValuePair("params", this.getHttpParams()));
    	this.setPostParams(postParams);*/
		
		this.executeRequest();
		
		while (this.getHttpStatus() < 0 && this.getPoolCount() < this.getPoolCountLimit()){
			
		}
		
		return new ArrayList<Object>();

	}
	
	private void executeRequest(){
		final HTTPManager httpManager = new HTTPManager(this.getReferer());
        httpManager.setActivity(this.getActivity());
        this.setHttpStatus(httpManager.loadData(this.getHttpMethodURL(), this.getPostParams()));
		
		//SI HUBO ERROR y TODAVIA SE PUEDE SEGUIR INTENTANDO
		if (this.getHttpStatus() < 0) {
			if (this.getPoolCount() < this.getPoolCountLimit()){
				this.incrementPoolCount();
				TimeoutHandler.getInstance().postDelayed(new Runnable() { 
			         public void run() {
			        	 GetHTTPTask.this.executeRequest();
			         } 
			    }, 2000);
			} else {
//				AndroidLogger.logger.error(R.string.intentos_conexion_agotados+" ("+this.getPoolCountLimit()+")");
			}
		} else {
			//OK, SETEAR RESULTADO
			this.setHTTPResult(httpManager.getResult());
		}
	}
	
	private void incrementPoolCount() {
		this.poolCount++;
	}

	protected void onPostExecute(ArrayList<Object> result) {
		super.onPostExecute(result);
		try {
			if (this.getHttpStatus() > 0){
				(this.getMethod()).invoke(this.getReferer(), this.getHTTPResult(), this.getExtraParams());
			} else if (this.getErrorCallback() != null){
				Method errorMethod = this.getReferer().getClass().getMethod(this.getErrorCallback(), String.class, ArrayList.class);
				errorMethod.invoke(this.getReferer(), this.getHttpMethodURL(), this.getExtraParams());
			}
		} catch (Exception e) {
            e.printStackTrace();

			//AndroidLogger.logger.error("Error en invocación de método de callback: "+ this.getMethod());
		}
	}
	
	public void executeTask(String methodCallback, String errorMethodCallback){
		try {
			this.setMethod(this.getReferer().getClass().getMethod(methodCallback, JSONObject.class,
							ArrayList.class));
			this.setErrorCallback(errorMethodCallback);	
			this.execute(this.getReferer(), this.getHttpMethodURL(), this.getMethod() ,this.getExtraParams());
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
	}

	public String getHttpParams() {
		return httpParams;
	}

	public void setHttpParams(String params) {
		this.httpParams = params;
	}


}