/**
 * 
 */
package com.ar.BeerChopper.model;

import com.google.gson.annotations.SerializedName;


/**
 * @author dterrier
 *
 */
public class Measure {

    @SerializedName("id")
    private long id;

    @SerializedName("name")
    private String name;

    @SerializedName("amount")
    private int amount;

    @SerializedName("active")
    private boolean active;

    @SerializedName("defaultMeasure")
	private boolean defaultMeasure;
	
	/**
	 * @param id
	 * @param nombre
	 */
	public Measure(long id, String name, boolean active, boolean defaultMeasure) {
		super();
		this.id = id;
		this.name = name;
		this.active = active;
		this.defaultMeasure = defaultMeasure;
	}

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the active
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * @param active the active to set
	 */
	public void setActive(boolean active) {
		this.active = active;
	}

	/**
	 * @return the defaultMeasure
	 */
	public boolean isDefaultMeasure() {
		return defaultMeasure;
	}

	/**
	 * @param defaultMeasure the defaultMeasure to set
	 */
	public void setDefaultMeasure(boolean defaultMeasure) {
		this.defaultMeasure = defaultMeasure;
	}

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}