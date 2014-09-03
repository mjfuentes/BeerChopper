package com.ar.BeerChopper.controller;

import android.content.Context;

import com.ar.BeerChopper.Response.ServiceResponseType;
import com.ar.BeerChopper.Response.StartupResponse;
import com.ar.BeerChopper.activity.LoginActivity;
import com.ar.BeerChopper.http.HTTPManager;
import com.ar.BeerChopper.model.BeerMeasure;
import com.ar.BeerChopper.model.Chopper;
import com.ar.BeerChopper.model.Measure;
import com.ar.BeerChopper.model.MeasureState;
import com.ar.BeerChopper.task.GetHTTPTask;
import com.google.gson.Gson;

import org.apache.http.NameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ChopperController {
	private static ChopperController instance;
	private Long chopperNumber;
	private Chopper chopper;
	private Context mContext;
	private List<BeerMeasure> beerMeasures = new ArrayList<BeerMeasure>();
    private Measure defaultMeasure;

	public static ChopperController getInstance(){
		if (instance == null){
			instance =  new ChopperController();
		} 
		return instance;
	}
	
	public static void reset(){
		instance = null;
	}

	public Chopper getChopper() {
		return chopper;
	}

	public void setChopper(Chopper chopper) {
		this.chopper = chopper;
	}

	public Context getmContext() {
		return mContext;
	}

	public void setmContext(Context mContext) {
		this.mContext = mContext;
	}

	public Long getChopperNumber() {
		return chopperNumber;
	}

	public void setChopperNumber(Long chopperNumber) {
		this.chopperNumber = chopperNumber;
	}

    public void loadMeasuresFromResponse(List<BeerMeasure> beerMeasures){
        Map<String,List<BeerMeasure>> measures = this.getChopper().getMeasures();
        for (BeerMeasure beerMeasure:beerMeasures){
            String measureName = beerMeasure.getMeasure().getName();
            if (!measures.containsKey(measureName)){
                measures.put(measureName,new ArrayList<BeerMeasure>());
                if (beerMeasure.getMeasure().isActive()){
                    this.getChopper().getStates().put(measureName, MeasureState.ACTIVE);
                    if (beerMeasure.getMeasure().isDefaultMeasure()){
                        this.setDefaultMeasure(beerMeasure.getMeasure());
                    }
                }
                else {
                    this.getChopper().getStates().put(measureName, MeasureState.INACTIVE);
                }
            }
            measures.get(measureName).add(beerMeasure);

        }
    }

    public void SetBeerMeasures(List<BeerMeasure> measures){
        this.setBeerMeasures(measures);
    }

    public List<BeerMeasure> getBeerMeasures() {
        return beerMeasures;
    }

    public void setBeerMeasures(List<BeerMeasure> beerMeasures) {
        this.beerMeasures = beerMeasures;
    }

    public void getBeerAndMeasures() {

        List<NameValuePair> postParams = new ArrayList<NameValuePair>();
        GetHTTPTask getHTTPTask = new GetHTTPTask();
        getHTTPTask.setReferer(this);
        getHTTPTask.setHttpMethodURL(HTTPManager.METHOD_GET_BEERS_AND_MEAURES);
        getHTTPTask.setPostParams(postParams);
        getHTTPTask.executeTask("completeGetBeers","errorGettingBeers");
    }

    public void completeGetBeers(JSONObject jsonData, ArrayList<Object> params) {
        try {
            if (jsonData != null){
                StartupResponse response = new Gson().fromJson(jsonData.toString(),StartupResponse.class);
                if (response.getServiceResponse().getResponseType().equals(ServiceResponseType.OK)){
                    this.loadMeasuresFromResponse(response.getBeerMeasures());
                    ((LoginActivity)mContext).SuccesfulLogin();
                }
                else {
                    ((LoginActivity)mContext).failedConnection(response.getServiceResponse().getResponseType().getMessage());
                }
            }
            else
            {
                ((LoginActivity)mContext).failedConnection("La informacion sobre medidas y cervezas esta corrupta.");            }
        } catch(Exception e){
            e.printStackTrace();
            ((LoginActivity)mContext).failedConnection("Ha habido una excepcion: " + e.getMessage());
        }
    }


    public void errorGettingBeers(String failedMethod,
                                  ArrayList<Object> params) {
        System.out.println("Error: " + failedMethod);
        ((LoginActivity)mContext).failedConnection("Ha habido un problema de conexion.");

    }


    public Measure getDefaultMeasure() {
        return defaultMeasure;
    }

    public void setDefaultMeasure(Measure defaultMeasure) {
        this.defaultMeasure = defaultMeasure;
    }
}