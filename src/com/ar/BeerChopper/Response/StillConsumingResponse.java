package com.ar.BeerChopper.Response;


public class StillConsumingResponse {

	private float userAmount;
	private float lastUse;
	private float transactionPercentage;
	private ServiceResponse serviceResponse;

	public StillConsumingResponse(ServiceResponse serviceResponse) {
		this.setServiceResponse(serviceResponse);
	}

	public float getTransactionPercentage() {
		return transactionPercentage;
	}

	public void setTransactionPercentage(float percentage) {
		this.transactionPercentage = percentage;
	}

	public float getLastUse() {
		return lastUse;
	}

	public void setLastUse(float cash) {
		this.lastUse = cash;
	}

	public float getUserAmount() {
		return userAmount;
	}

	public void setUserAmount(float cash) {
		this.userAmount = cash;
	}

	public ServiceResponse getServiceResponse() {
		return serviceResponse;
	}

	public void setServiceResponse(ServiceResponse serviceResponse) {
		this.serviceResponse = serviceResponse;
	}
	
}
