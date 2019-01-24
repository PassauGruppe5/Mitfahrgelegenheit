package com.PickmeUP.project.service;

import com.PickmeUP.project.model.Bonus;
import com.PickmeUP.project.repository.BonusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class BonusService {

    @Autowired
    private BonusRepository bonusRepository;

    public Bonus findBonusById(int id){return bonusRepository.findById(id);}
}
