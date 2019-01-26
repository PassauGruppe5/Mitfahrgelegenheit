package com.PickmeUP.project.model;


public class TopFiveDestinationObject {

    private String named;
    private String counted;

    public TopFiveDestinationObject(Object[] object){
        this.counted = object[0].toString();
        this.named = (String) object[1];
    }

    public String getCounted() {
        return counted;
    }

    public String getNamed() {
        return named;
    }

    public void setCounted(String counted) {
        this.counted = counted;
    }

    public void setNamed(String named) {
        this.named = named;
    }
}
