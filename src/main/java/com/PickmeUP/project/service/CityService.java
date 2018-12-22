package com.PickmeUP.project.service;

import com.PickmeUP.project.model.City;
import com.PickmeUP.project.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CityService {

    @Autowired
    private CityRepository cityRepository;
    private City city = new City();
}
