package com.ar.BeerChopper.Response;

import com.google.gson.annotations.SerializedName;

public class ServiceResponse {

    @SerializedName("responseType")
    private ServiceResponseType responseType;

    @SerializedName("message")
    private String message;

    @SerializedName("value")
    private long value;
	
	public String toString(){
		return String.format("ServiceResponse \r\n \t Type: %s \r\n \t Message: %s \r\n \t Value: %s", 
				this.getResponseType(), this.getMessage(), this.getValue());
	}
	
	public long getValue(){
		return this.value;
	}
	
	public void setValue(long value) {
		this.value = value;
	}
	
	public ServiceResponse(String message) {
		this.message = message;
	}

	public ServiceResponse(ServiceResponseType responseType){
		this.responseType = responseType;
		this.value = responseType.getValue();
	}
	
	public ServiceResponse(ServiceResponseType responseType, long value) {
		this.responseType = responseType;
		this.value = value;
	}
	
	public ServiceResponse(ServiceResponseType responseType, String message) {
		this.responseType = responseType;
		this.message = message;
	}
	
	public String getMessage() {
		return (message != null) ? message : responseType.getMessage();
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public ServiceResponseType getResponseType() {
		return responseType;
	}

	public void setResponseType(ServiceResponseType responseType) {
		this.responseType = responseType;
	}
	
}
