package com.ar.BeerChopper.http;

import android.preference.PreferenceManager;

import java.io.IOException;
import java.net.URI;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONObject;

import com.ar.BeerChopper.BeerChopper;
import com.ar.BeerChopper.activity.DefaultActivity;

public class HTTPManager {
	private String url; 
	private DefaultHttpClient httpClient;
	private Object referer;
	private JSONObject result;

    public static final String METHOD_SEND_DATA_ORDER = "service/tablet/sendDataOrder";
    public static final String METHOD_CHECK_STATUS_CHOPPER = "service/tablet/checkStatusChopper";
    public static final String METHOD_GET_USER = "service/tablet/getClient";
    public static final String METHOD_VERIFY_LOGIN = "service/tablet/checkLogin";
    public static final String METHOD_CHECK_STATUS_AVAILABLE = "service/tablet/checkIsStillConsuming";
    public static final String METHOD_GET_BEERS_AND_MEAURES = "service/tablet/startup";
    public static String host_name = "";
	
	public static final int CONNECTION_TIMEOUT = 5000;
	public static final int SOCKET_TIMEOUT = 8000;
	
	public static final int STATUS_OK 		= 1;
	public static final int STATUS_TIMEOUT 	= -1;
	public static final int STATUS_ERROR 	= -2;
    private DefaultActivity activity;

    public HTTPManager(Object referer){
		super();
        this.setReferer(referer);
        String host_name = PreferenceManager.getDefaultSharedPreferences(BeerChopper.getContext()).getString("HOST_NAME", "");
		this.url = host_name;

		HttpParams httpParameters = new BasicHttpParams();
		
		HttpConnectionParams.setConnectionTimeout(httpParameters, CONNECTION_TIMEOUT);
		
		HttpConnectionParams.setSoTimeout(httpParameters, SOCKET_TIMEOUT);

		this.httpClient = new DefaultHttpClient(httpParameters);
	}
	
	public HttpClient getHttpClient() {
		return httpClient;
	}
	public void setHttpClient(DefaultHttpClient httpClient) {
		this.httpClient = httpClient;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	public JSONObject getResult() {
		return result;
	}

	public void setResult(JSONObject result) {
		this.result = result;
	}
	
	/**
	 * 
	 * @param method URL DEL METODO A EJECUTAR
	 * @param list 
	 * @return CODIGO DE ESTADO [STATUS_OK|STATUS_TIMEOUT] 
	 */
	public int loadData(String method, List<NameValuePair> list){

		try {
			//HttpPost httppost = new HttpPost(this.getUrl() + method);
            HttpPost httppost = new HttpPost(new URI(this.getUrl() + method));

            ResponseHandler<String> responseHandler = new BasicResponseHandler();
			
			httppost.setEntity(new UrlEncodedFormEntity(list));

			System.out.println("Ejecutando request : "+ this.getUrl() + method + list.toString());
		    
		    String result = this.getHttpClient().execute(httppost,responseHandler);
		    
		    System.out.println("RESULTADO:  " + result);
			
		    this.setResult(new JSONObject(result));
            return STATUS_OK;
        } catch (ClientProtocolException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Error: HTTPManager>>getData - URL: " + this.getUrl() + method+" Error: "+e.getMessage());
			e.printStackTrace();
			return STATUS_ERROR;
		}
			return 0;
	}

	/**
	 * @return the referer
	 */
	public Object getReferer() {
		return referer;
	}

	/**
	 * @param referer the referer to set
	 */
	public void setReferer(Object referer) {
		this.referer = referer;
	}

    public void setActivity(DefaultActivity activity) {
        this.activity = activity;
    }

    public DefaultActivity getActivity()
    {
        return this.activity;
    }
}