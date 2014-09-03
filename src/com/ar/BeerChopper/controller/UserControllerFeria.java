package com.ar.BeerChopper.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import com.ar.BeerChopper.Response.ClientResponse;
import com.ar.BeerChopper.Response.ServiceResponseType;
import com.ar.BeerChopper.activity.DefaultActivity;
import com.ar.BeerChopper.activity.LoginActivity;
import com.ar.BeerChopper.activity.MainActivityFeria;
import com.ar.BeerChopper.http.HTTPManager;
import com.ar.BeerChopper.model.Client;
import com.ar.BeerChopper.task.GetHTTPTask;
import com.google.gson.Gson;

public class UserControllerFeria {
	private static UserControllerFeria instance;
	private DefaultActivity activity;
    private Client client;

	public static UserControllerFeria getInstance(){
		if (instance == null){
			instance =  new UserControllerFeria();
		} 
		return instance;
	}
	
	public static void reset(){
		instance = null;
	}
	
	public void verifyUserByCardNumber(String cardNumber) {
        List<NameValuePair> postParams = new ArrayList<NameValuePair>();
        postParams.add(new BasicNameValuePair("chopperNumber", Integer.toString(1)));
        postParams.add(new BasicNameValuePair("cardNumber", cardNumber));

        GetHTTPTask getHTTPTask = new GetHTTPTask();
        getHTTPTask.setReferer(this);
        getHTTPTask.setActivity(activity);
        getHTTPTask.setHttpMethodURL(HTTPManager.METHOD_GET_USER);
        getHTTPTask.setPostParams(postParams);

        getHTTPTask.executeTask("completeVerifyUserByCardNumberTask","errorVerifyUserByCardNumberTask");
	}

    public synchronized Boolean updateData(ClientResponse response) {
        try {
            if (response.getClient() != null){
                this.setClient(response.getClient());
                this.getClient().setLastConsume(response.getLastUse());
                UserControllerBar.getInstance().setAdmin(false);
                return true;
            }
            else if (response.getServiceResponse().getResponseType().equals(ServiceResponseType.NEW_ADMIN_TRANSACTION_READY)){
                UserControllerBar.getInstance().setAdmin(true);
                return true;
            }

            else return false;

        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void completeVerifyUserByCardNumberTask(JSONObject jsonData, ArrayList<Object> params) {
        try {
            if (jsonData != null){
                ClientResponse response = new Gson().fromJson(jsonData.toString(),ClientResponse.class);
                if (response.getServiceResponse().getResponseType().equals(ServiceResponseType.OK)){
                    if (this.updateData(response)){
                        this.getActivity().startActivity(MainActivityFeria.class, true);
                        ((LoginActivity)this.getActivity()).SuccesfulLogin();
                    }
                }
                else {
                    ((LoginActivity)this.getActivity()).failedConnection(response.getServiceResponse().getResponseType().getMessage());
                }
            }
            else
            {
                ((LoginActivity)this.getActivity()).failedConnection("La informacion recibida del servidor no es valida.");
            }
        } catch(Exception e){
            e.printStackTrace();
            ((LoginActivity)this.getActivity()).FailedLogin("Ha habido una excepcion: " + e.getMessage());
        }
    }


	public void errorVerifyUserByCardNumberTask(String failedMethod,
			ArrayList<Object> params) {
		System.out.println("Error: " + failedMethod);
        ((LoginActivity)this.getActivity()).failedConnection("Ha habido un problema de conexion.");

    }

	/**
	 * @return the activity
	 */
	public DefaultActivity getActivity() {
		return activity;
	}

	/**
	 * @param activity the activity to set
	 */
	public void setActivity(DefaultActivity activity) {
		this.activity = activity;
	}


    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

}