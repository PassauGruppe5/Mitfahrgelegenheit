package com.PickmeUP.project.service;

import com.PickmeUP.project.model.Stop;
import com.PickmeUP.project.repository.StopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
class StopService {

    @Autowired
    private StopRepository stopRepository;
    private Stop stop = new Stop();
}
