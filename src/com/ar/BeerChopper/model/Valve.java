/**
 * 
 */
package com.ar.BeerChopper.model;


/**
 * @author dterrier
 *
 */
public class Valve {

	private long id;
	private Beer beer;
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
	 * @return the beer
	 */
	public Beer getBeer() {
		return beer;
	}
	/**
	 * @param beer the beer to set
	 */
	public void setBeer(Beer beer) {
		this.beer = beer;
	}
	/**
	 * @param id
	 * @param beer
	 */
	public Valve(long id, Beer beer) {
		super();
		this.id = id;
		this.beer = beer;
	}
	
}