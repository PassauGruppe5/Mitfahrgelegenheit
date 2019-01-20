package com.PickmeUP.project.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Leg")
public class Leg {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "start_address")
    private String start_address;

    @Column(name = "end_address")
    private String end_address;

    @Column(name = "seats")
    private int seats;

    @Column(name = "bags")
    private int bags;

    @Column(name = "distance" )
    private int distance;

    @Column(name = "duration")
    private int duration;

    @Column(name = "position")
    private int position;

    @ManyToOne
    @JoinColumn(name = "journey")
    private Journey journey;

    @ManyToMany
    @JoinColumn(name = "passengers")
    private List<User> passengers;

    public int getId(){return this.id;}

    public void setStart_address(String start_address){this.start_address = start_address;}
    public String getStart_address(){return this.start_address;}

    public void setEnd_address(String end_address){this.end_address = end_address;}
    public String getEnd_address(){return this.end_address;}

    public void setSeats(int seats){this.seats = seats;}
    public int getSeats(){return this.seats;}

    public void setBags(int bags){this.bags = bags;}
    public int getBags(){return this.bags;}

    public int getPosition() {
        return position;
    }
    public void setPosition(int position) {
        this.position = position;
    }

    public void setDistance(int distance){this.distance = distance;}
    public int getDistance(){return this.distance;}

    public void setDuration(int duration){this.duration = duration;}
    public int getDuration(){return this.duration;}

    public void setJourney(Journey journey){this.journey = journey;}
    public Journey getJourney(){return this.journey;}

    public void setPassengers(List<User> passengers){this.passengers = passengers;}
    public List <User> getPassengers(){return this.passengers;}

    public Boolean checkSpace(){
        if((this.seats - this.getPassengers().size()) >0 ){
            return true;
        }
        else{
            return false;
        }

    }
}