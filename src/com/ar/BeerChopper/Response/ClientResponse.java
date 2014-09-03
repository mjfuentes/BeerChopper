package com.ar.BeerChopper.Response;

import com.ar.BeerChopper.model.Client;

public class ClientResponse {

    private Client client;
    private float lastUse;
    private float lastConsumption;
    private ServiceResponse serviceResponse;

    public ClientResponse() {}

    public ClientResponse(Client client, ServiceResponse serviceResponse){
        this.setClient(client);
        this.setServiceResponse(serviceResponse);
    }

    /**
     * @return the client
     */
    public Client getClient() {
        return client;
    }

    /**
     * @param client the client to set
     */
    public void setClient(Client client) {
        this.client = client;
    }

    /**
     * @return the serviceResponse
     */
    public ServiceResponse getServiceResponse() {
        return serviceResponse;
    }

    /**
     * @param serviceResponse the serviceResponse to set
     */
    public void setServiceResponse(ServiceResponse serviceResponse) {
        this.serviceResponse = serviceResponse;
    }

    public float getLastUse() {
        return lastUse;
    }

    public void setLastUse(float cash) {
        this.lastUse = cash;
    }

    /**
     * @return the lastConsumption
     */
    public float getLastConsumption() {
        return lastConsumption;
    }

    /**
     * @param lastConsumption the lastConsumption to set
     */
    protected void setLastConsumption(float lastConsumption) {
        this.lastConsumption = lastConsumption;
    }

}