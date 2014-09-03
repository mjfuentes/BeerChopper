/**
 * 
 */
package com.ar.BeerChopper.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author dterrier
 *
 */
public class Chopper {

    private Map<String,List<BeerMeasure>> measures = new HashMap<String, List<BeerMeasure>>();
    private Map<String,MeasureState> states = new HashMap<String, MeasureState>();
    private long id;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Chopper(long id, List<Beer> beer) {
		super();
		this.id = id;
	}

    public Map<String, List<BeerMeasure>> getMeasures() {
        return measures;
    }

    public void setMeasures(Map<String, List<BeerMeasure>> measures) {
        this.measures = measures;
    }

    public Map<String, MeasureState> getStates() {
        return states;
    }

    public void setStates(Map<String, MeasureState> states) {
        this.states = states;
    }
}