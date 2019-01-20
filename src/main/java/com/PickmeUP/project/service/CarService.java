package com.PickmeUP.project.service;

import com.PickmeUP.project.model.Car;
import com.PickmeUP.project.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CarService {

    @Autowired
    private CarRepository carRepository;

    public Car save(Car car){return carRepository.save(car);}
}
