package com.ar.BeerChopper.Response;
import java.util.List;

import com.ar.BeerChopper.model.BeerMeasure;
import com.ar.BeerChopper.model.Client;
import com.google.gson.annotations.SerializedName;

public class CheckLoginResponse {

    @SerializedName("client")
    private Client client;

    @SerializedName("beerMeasures")
    private List<BeerMeasure> beerMeasures;

    @SerializedName("serviceResponse")
    private ServiceResponse serviceResponse;

    public CheckLoginResponse(ServiceResponse serviceResponse, List<BeerMeasure> beerMeasures, Client client) {
        this.setServiceResponse(serviceResponse);
        this.setBeerMeasures(beerMeasures);
        this.setClient(client);

    }

    public CheckLoginResponse(ServiceResponse serviceResponse) {
        this.setServiceResponse(serviceResponse);
    }

    public ServiceResponse getServiceResponse() {
        return serviceResponse;
    }

    public void setServiceResponse(ServiceResponse serviceResponse) {
        this.serviceResponse = serviceResponse;
    }


    public List<BeerMeasure> getBeerMeasures() {
        return beerMeasures;
    }

    public void setBeerMeasures(List<BeerMeasure> beerMeasures) {
        this.beerMeasures = beerMeasures;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

}