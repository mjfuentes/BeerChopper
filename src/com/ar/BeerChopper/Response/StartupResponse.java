package com.ar.BeerChopper.Response;

import com.ar.BeerChopper.model.BeerMeasure;

import java.util.List;

/**
 * Created by matias on 3/22/14.
 */
public class StartupResponse {

    private List<BeerMeasure> beerMeasures;
    private ServiceResponse serviceResponse;

    public StartupResponse(ServiceResponse serviceResponse, List<BeerMeasure> beerMeasures) {
        this.setServiceResponse(serviceResponse);
        this.setBeerMeasures(beerMeasures);
    }

    public List<BeerMeasure> getBeerMeasures() {
        return beerMeasures;
    }

    public void setBeerMeasures(List<BeerMeasure> beerMeasures) {
        this.beerMeasures = beerMeasures;
    }

    public ServiceResponse getServiceResponse() {
        return serviceResponse;
    }

    public void setServiceResponse(ServiceResponse serviceResponse) {
        this.serviceResponse = serviceResponse;
    }
}
