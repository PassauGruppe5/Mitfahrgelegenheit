package com.PickmeUP.project.service;

import com.PickmeUP.project.model.Bonus;
import com.PickmeUP.project.repository.BonusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class BonusService {

    @Autowired
    private BonusRepository bonusRepository;

    //      finds Bonus by id.
    //
    //      @param  id                  id to be looked for.
    //      @return Bonus               the Bonus.
    public Bonus findBonusById(int id){return bonusRepository.findById(id);}
}
