package com.PickmeUP.project;

import com.PickmeUP.project.model.Journey;
import com.PickmeUP.project.model.Leg;
import com.PickmeUP.project.model.User;
import com.PickmeUP.project.service.GmailService;
import com.PickmeUP.project.service.JourneyService;
import com.PickmeUP.project.service.LegService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class SchedulerTasks {

    @Autowired
    JourneyService journeyService;

    @Autowired
    LegService legService;

    private static final Logger log = LoggerFactory.getLogger(SchedulerTasks.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    //      every 30 minutes journeys with an arrival date and later than the current values
    //      will be set to inactive (active = 0)
    //
    //      @return void
    @Scheduled(cron = "0 */30 * * * *")
    public void setDoneJourneysToInactive(){
        //log the running of the service.
        log.info("Deactivated ended journeys.", dateFormat.format(new Date()));

        //get all done journeys.
        ArrayList<Journey> doneJourneys = journeyService.findAllByActiveAndCanceled(1,0);

        //get current date and time values.
        LocalDateTime current = LocalDateTime.now();

        //mark a jounrey as inactive if arrival date and time is later than the current values.
        for(Journey done : doneJourneys ) {
                if (current.isAfter(LocalDateTime.of(LocalDate.parse(done.getArrivalDate()), LocalTime.parse(done.getArrivalTime())))) {
                done.setActive(0);
                journeyService.updateJourney(done);
            }
        }

    }

    //      every 5 minutes drivers and passengers get a reminder email if their
    //     booked or offered journey will start in one hour from now.
    //
    //      @return void
    @Scheduled(cron = "0 */5 * * * *")
    public void sendReminders(){

        //log running of the service.
        log.info("Reminders sent.", dateFormat.format(new Date()));

        //get all active journeys.
        ArrayList<Journey> journeysToCheck = journeyService.findAllByActiveAndCanceled(1,0);

        //get current values for date and time.
        LocalDateTime current = LocalDateTime.now();

        //check all active journeys for date and time.
        for(Journey done : journeysToCheck ) {

            //if a journey is within 1 hour and 1 hour + 5 mins, a riminder will be sent to driver and
            //passengers
            if ((current.plusHours(1).isBefore(LocalDateTime.of(LocalDate.parse(done.getDepartureDate()), LocalTime.parse(done.getDepartureTime()))) && current.plusHours(1).plusMinutes(5).isAfter(LocalDateTime.of(LocalDate.parse(done.getDepartureDate()), LocalTime.parse(done.getDepartureTime())))) || current.plusHours(1).isEqual(LocalDateTime.of(LocalDate.parse(done.getDepartureDate()), LocalTime.parse(done.getDepartureTime())))) {

                //get all legs of journey.
                ArrayList<Leg> legsOfJourneys = legService.findByJourney(done);

                    //send reminder to driver once.
                    for(Leg leg : legsOfJourneys) {
                    List<User> usersToBeReminded = leg.getPassengers();
                    usersToBeReminded.add(done.getDriver());
                    List<User> remindedUsers = new ArrayList<>();

                    //send reminder to every passenger once.
                    for(User user : usersToBeReminded) {
                        if(remindedUsers.contains(user) != true) {
                            remindedUsers.add(user);
                            try {
                                GmailService.sendJourneyInfoMail(user);
                            } catch (MailException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }
    }
}
