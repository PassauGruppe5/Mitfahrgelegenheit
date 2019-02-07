package com.PickmeUP.project.service;


import com.PickmeUP.project.model.Repeat;
import com.PickmeUP.project.repository.RepeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RepeatService {

    @Autowired
    private RepeatRepository repeatRepository;

    //      saves Repeat.
    //
    //      @param  repeat                     repeat to be saved.
    //      @return void
    public void saveRepeat(Repeat repeat){repeatRepository.save(repeat);}

    //      finds Repeat by id.
    //
    //      @param  id                     id to be looked for.
    //      @return Repeat                 the Repeat.
    public Repeat findRepeatById(int id){return repeatRepository.findById(id);}

    //      finds Repeat by id.
    //
    //      @param  id                     id to be looked for.
    //      @return Repeat                 the Repeat.
    public Repeat findById(int id){return repeatRepository.findById(id);}

}
