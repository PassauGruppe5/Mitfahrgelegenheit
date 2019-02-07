package com.PickmeUP.project.service;

import com.PickmeUP.project.model.Car;
import com.PickmeUP.project.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CarService {

    @Autowired
    private CarRepository carRepository;

    //      saves Car by id.
    //
    //      @param  car             car to be saved.
    //      @return Car             the Car.
    public Car save(Car car){return carRepository.save(car);}
}
