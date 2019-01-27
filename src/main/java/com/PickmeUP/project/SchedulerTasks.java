package com.PickmeUP.project;

import com.PickmeUP.project.model.Journey;
import com.PickmeUP.project.service.JourneyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;

@Component
public class SchedulerTasks {

    @Autowired
    JourneyService journeyService;

    private static final Logger log = LoggerFactory.getLogger(SchedulerTasks.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Scheduled(cron = "0 */1 * * * *")
    public void setDoneJourneysToInactive(){
        log.info("Deactivated ended journeys.", dateFormat.format(new Date()));
        ArrayList<Journey> doneJourneys = journeyService.findAllByActiveAndCanceled(1,0);
        LocalDateTime current = LocalDateTime.now();

        for(Journey done : doneJourneys ) {
            LocalDateTime testest   =  LocalDateTime.of(LocalDate.parse(done.getArrivalDate()), LocalTime.parse(done.getArrivalTime()));
            System.out.println(testest);
            if (current.isAfter(LocalDateTime.of(LocalDate.parse(done.getArrivalDate()), LocalTime.parse(done.getArrivalTime())))) {
                done.setActive(0);
                journeyService.updateJourney(done);
            }
            System.out.println(testest);
        }

    }
}
