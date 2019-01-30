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

    @Scheduled(cron = "0 */30 * * * *")
    public void setDoneJourneysToInactive(){
        log.info("Deactivated ended journeys.", dateFormat.format(new Date()));
        ArrayList<Journey> doneJourneys = journeyService.findAllByActiveAndCanceled(1,0);
        LocalDateTime current = LocalDateTime.now();

        for(Journey done : doneJourneys ) {
                if (current.isAfter(LocalDateTime.of(LocalDate.parse(done.getArrivalDate()), LocalTime.parse(done.getArrivalTime())))) {
                done.setActive(0);
                journeyService.updateJourney(done);
            }
        }

    }

    @Scheduled(cron = "0 */5 * * * *")
    public void sendReminders(){
        log.info("Reminders sent.", dateFormat.format(new Date()));
        ArrayList<Journey> jounreysToCheck = journeyService.findAllByActiveAndCanceled(1,0);
        LocalDateTime current = LocalDateTime.now();

        for(Journey done : jounreysToCheck ) {
            if ((current.plusHours(1).isBefore(LocalDateTime.of(LocalDate.parse(done.getDepartureDate()), LocalTime.parse(done.getDepartureTime()))) && current.plusHours(1).plusMinutes(5).isAfter(LocalDateTime.of(LocalDate.parse(done.getDepartureDate()), LocalTime.parse(done.getDepartureTime())))) || current.plusHours(1).isEqual(LocalDateTime.of(LocalDate.parse(done.getDepartureDate()), LocalTime.parse(done.getDepartureTime())))) {

                ArrayList<Leg> legsOfJourneys = legService.findByJourney(done);

                    for(Leg leg : legsOfJourneys) {
                    List<User> usersToBeReminded = leg.getPassengers();
                    usersToBeReminded.add(done.getDriver());
                    List<User> remindedUsers = new ArrayList<>();

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
