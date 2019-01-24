package com.PickmeUP.project.model;

public class TopFiveDestination {

    private int id;

    private int count;

    private String name;

    public TopFiveDestination(int counted, String named){
        this.count = counted;
        this.name = named;
    }

    public void setName(String named) {
        name = named;
    }

    public void setCount(int counted) {
        this.count = counted;
    }

    public String getName() {
        return name;
    }

    public int getCount() {
        return count;
    }
}
