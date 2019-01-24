package com.PickmeUP.project;

import com.PickmeUP.project.model.Journey;
import com.PickmeUP.project.service.JourneyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@Component
public class SchedulerTasks {

    @Autowired
    JourneyService journeyService;

    private static final Logger log = LoggerFactory.getLogger(SchedulerTasks.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Scheduled(cron = "0 */1 * * * *")
    public void reportCurrentTime() throws ParseException {
        log.info("Deactivated ended journeys.", dateFormat.format(new Date()));

        ArrayList<Journey> doneJourneys = journeyService.findAllByActiveAndCanceled(1,0);
        SimpleDateFormat dateTimeformatter = new SimpleDateFormat("dd-M-yyyy hh:mm");
        Date current = new Date();
        for(Journey done : doneJourneys ) {
            Date dateOfJourney = dateTimeformatter.parse(done.getArrivalDate()+ ' '+ done.getArrivalTime());
            if (current.after(dateOfJourney)) {
                done.setActive(0);
                journeyService.updateJourney(done);
            }
        }

    }
}
