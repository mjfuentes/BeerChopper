package com.ar.BeerChopper.model;

import com.google.gson.annotations.SerializedName;

public class BeerMeasure {

    @SerializedName("beerMeasureId")
    private Long beerMeasureId;

    @SerializedName("measure")
    private Measure measure;

    @SerializedName("beer")
    private Beer beer;

    @SerializedName("price")
    private float price;
	
	public BeerMeasure(){
		
	}
	
	public BeerMeasure(Beer aBeer, Measure aMeasure) {
		this.setBeer(aBeer);
		this.setMeasure(aMeasure);
		this.setPrice(0);
	}
	
	public BeerMeasure(Beer aBeer, Measure aMeasure, float aPrice) {
		this.setBeer(aBeer);
		this.setMeasure(aMeasure);
		this.setPrice(aPrice);
	}
	
	@Override
	public String toString(){
		String print = String.format("BeerMeasure for beer: %s and measure: %s $%s", 
					   this.getBeer(), this.getMeasure(), this.getPrice());
		return print;
	}
	
	public Long getBeerMeasureId() {
		return beerMeasureId;
	}
	public void setBeerMeasureId(Long beerMeasureId) {
		this.beerMeasureId = beerMeasureId;
	}
	public Measure getMeasure() {
		return measure;
	}
	public void setMeasure(Measure measure) {
		this.measure = measure;
	}
	public Beer getBeer() {
		return beer;
	}
	public void setBeer(Beer beer) {
		this.beer = beer;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	
}
