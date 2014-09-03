package com.ar.BeerChopper.model;

public enum MeasureNames implements IMeasureNames {

    JAR(IMeasureNames.JAR, 1000),
    PINT(IMeasureNames.PINT, 500),
    HALFPINT(IMeasureNames.HALFPINT, 250),
    CHOPP(IMeasureNames.CHOPP, 233),
    TASTING(IMeasureNames.TASTING, 100);

    private String name;
    private int measure;

    MeasureNames (String aName, int aMeasure){
        name = aName;
        measure = aMeasure;
    }

    public String getName(){
        return name;
    }

    public int getMeasure(){
        return measure;
    }

}