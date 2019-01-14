package com.PickmeUP.project.service;


import com.PickmeUP.project.model.Repeat;
import com.PickmeUP.project.repository.RepeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RepeatService {

    @Autowired
    private RepeatRepository repeatRepository;

    public void saveRepeat(Repeat repeat){repeatRepository.save(repeat);}

    public Repeat findRepeatById(int id){return repeatRepository.findById(id);}

}
