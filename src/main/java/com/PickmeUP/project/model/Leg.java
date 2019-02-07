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

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "leg_user",joinColumns = @JoinColumn(name = "leg_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> passengers;

    //      gets id of object.
    //
    //      @return id                 the object's id.
    public int getId(){return this.id;}

    //      sets start_address of object.
    //
    //      @param start_address                  start_address to be set.
    //      @return void
    public void setStart_address(String start_address){this.start_address = start_address;}

    //      gets start_address of object.
    //
    //      @return start_address                 the object's start_address.
    public String getStart_address(){return this.start_address;}

    //      sets end_address of object.
    //
    //      @param end_address                  end_address to be set.
    //      @return void
    public void setEnd_address(String end_address){this.end_address = end_address;}

    //      gets end_address of object.
    //
    //      @return end_address                 the object's end_address.
    public String getEnd_address(){return this.end_address;}

    //      sets seats of object.
    //
    //      @param seats                  seats to be set.
    //      @return void
    public void setSeats(int seats){this.seats = seats;}

    //      gets seats of object.
    //
    //      @return seats                 the object's seats.
    public int getSeats(){return this.seats;}

    //      sets bags of object.
    //
    //      @param bags                  bags to be set.
    //      @return void
    public void setBags(int bags){this.bags = bags;}

    //      gets bags of object.
    //
    //      @return bags                 the object's bags.
    public int getBags(){return this.bags;}

    //      gets position of object.
    //
    //      @return position                 the object's position.
    public int getPosition() {
        return position;
    }

    //      sets position of object.
    //
    //      @param position                  position to be set.
    //      @return void
    public void setPosition(int position) {
        this.position = position;
    }

    //      sets distance of object.
    //
    //      @param distance                  distance to be set.
    //      @return void
    public void setDistance(int distance){this.distance = distance;}

    //      gets distance of object.
    //
    //      @return distance                 the object's distance.
    public int getDistance(){return this.distance;}

    //      sets duration of object.
    //
    //      @param duration                  duration to be set.
    //      @return void
    public void setDuration(int duration){this.duration = duration;}

    //      gets duration of object.
    //
    //      @return duration                 the object's duration.
    public int getDuration(){return this.duration;}

    //      sets journey of object.
    //
    //      @param journey                  journey to be set.
    //      @return void
    public void setJourney(Journey journey){this.journey = journey;}

    //      gets journey of object.
    //
    //      @return journey                 the object's journey.
    public Journey getJourney(){return this.journey;}

    //      sets passengers of object.
    //
    //      @param passengers                  passengers to be set.
    //      @return void
    public void setPassengers(List<User> passengers){this.passengers = passengers;}

    //      gets passengers of object.
    //
    //      @return passengers                 the object's passengers.
    public List <User> getPassengers(){return this.passengers;}

    //      sets checks if seats are available.
    //
    //      @return boolean                    wether there  are or are not seats available
    public Boolean checkSpace(){
        if((this.seats - this.getPassengers().size()) >0 ){
            return true;
        }
        else{
            return false;
        }

    }

    //      correct adresses to be stored.
    //
    //      @param  start                      start adress to be corrected.
    //      @param  end                        end adress to be corrected.
    //      @return boolean                    wether there  are or are not seats available
    public void correctAddresses(String start, String end){

        String[] partsOfStartAdress = start.split(",");
        String[] partsOfEndAdress = end.split(",");

        switch (partsOfStartAdress.length ) {
            case 3:
                this.setStart_address(partsOfStartAdress[1]);
                break;
            case 4:
                this.setStart_address(partsOfStartAdress[2]);
                break;
            case 5:
                this.setStart_address(partsOfStartAdress[3]);
                break;
        }

        switch (partsOfEndAdress.length ) {
            case 3:
                this.setEnd_address(partsOfEndAdress[1]);
                break;
            case 4:
                this.setEnd_address(partsOfEndAdress[2]);
                break;
            case 5:
                this.setEnd_address(partsOfEndAdress[3]);
                break;
        }
    }
}