package com.ar.BeerChopper.controller;

import com.ar.BeerChopper.Response.CheckLoginResponse;
import com.ar.BeerChopper.Response.ServiceResponseType;
import com.ar.BeerChopper.activity.DefaultActivity;
import com.ar.BeerChopper.activity.LoginActivity;
import com.ar.BeerChopper.activity.MainActivityAdmin;
import com.ar.BeerChopper.activity.MainActivityBar;
import com.ar.BeerChopper.http.HTTPManager;
import com.ar.BeerChopper.model.Client;
import com.ar.BeerChopper.task.GetHTTPTask;
import com.google.gson.Gson;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class UserControllerBar {

    //region Private Fields
    private static UserControllerBar instance;
    private Boolean admin = false;
	private String cardToCheck;
	private Client client;
	private DefaultActivity activity;
    //endregion

    //region Public Methods
	public static UserControllerBar getInstance() {
		if (instance == null) {
			instance = new UserControllerBar();
		}
		return instance;
	}

	public static void reset() {
		instance = null;
	}

	public void verifyUserByCardNumber(String cardNumber) {
        this.cardToCheck=cardNumber;

        List<NameValuePair> postParams = new ArrayList<NameValuePair>();
        postParams.add(new BasicNameValuePair("chopperNumber", Integer.toString(1)));
        postParams.add(new BasicNameValuePair("cardNumber", cardNumber));
        GetHTTPTask getHTTPTask = new GetHTTPTask();
        getHTTPTask.setReferer(this);
        getHTTPTask.setHttpMethodURL(HTTPManager.METHOD_VERIFY_LOGIN);
        getHTTPTask.setPostParams(postParams);

        getHTTPTask.executeTask("completeVerifyUserByCardNumberTask","errorVerifyUserByCardNumberTask");
	}

    public void completeVerifyUserByCardNumberTask(JSONObject jsonData, ArrayList<Object> params) {
        if (jsonData != null){
            CheckLoginResponse response = new Gson().fromJson(jsonData.toString(),CheckLoginResponse.class);
            if (response.getServiceResponse().getResponseType().equals(ServiceResponseType.NEW_CUSTOMER_TRANSACTION_READY)){
                if (this.updateData(response)){
                    this.getActivity().startActivity(MainActivityBar.class, true);
                    ((LoginActivity)this.getActivity()).SuccesfulLogin();
                }
                else {
                    ((LoginActivity)this.getActivity()).FailedLogin("Null data");
                }
            }
            else if (response.getServiceResponse().getResponseType().equals(ServiceResponseType.NEW_ADMIN_TRANSACTION_READY)){
                if (this.updateData(response)){
                    this.getActivity().startActivity(MainActivityAdmin.class, true);
                    ((LoginActivity)this.getActivity()).SuccesfulLogin();
                }
            }
            else {
                ((LoginActivity)this.getActivity()).FailedLogin(response.getServiceResponse().getResponseType().getMessage());
            }
        }
        else
        {
            ((LoginActivity)this.getActivity()).FailedLogin("CONNECTION PROBLEM");
        }
    }

    public void errorVerifyUserByCardNumberTask(String failedMethod, ArrayList<Object> params) {
        ((LoginActivity)this.getActivity()).FailedLogin(failedMethod);
    }

	public synchronized Boolean updateData(CheckLoginResponse response) {
		try {
                if (response.getClient() != null){
                    this.setClient(response.getClient());
                    this.setAdmin(false);
                    return true;
                }
                else if (response.getServiceResponse().getResponseType().equals(ServiceResponseType.NEW_ADMIN_TRANSACTION_READY)){
                    this.setAdmin(true);
                    return true;
                }

                else return false;

        }
        catch (Exception e) {
			e.printStackTrace();
            return false;
		}
    }

	public void updateUserAmount(Float amount, Float lastUse) {
        this.getClient().setCash(amount);
		this.getClient().setLastConsume(lastUse);
	}

	/**
	 * @return the activity
	 */
	public DefaultActivity getActivity() {
		return activity;
	}

	/**
	 * @param activity
	 *            the activity to set
	 */
	public void setActivity(DefaultActivity activity) {
		this.activity = activity;
	}

	/**
	 * @return the cardToCheck
	 */
	public String getCardToCheck() {
		return cardToCheck;
	}

	/**
	 * @param cardToCheck the cardToCheck to set
	 */
	public void setCardToCheck(String cardToCheck) {
		this.cardToCheck = cardToCheck;
	}

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Boolean isAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }
    //endregion
}