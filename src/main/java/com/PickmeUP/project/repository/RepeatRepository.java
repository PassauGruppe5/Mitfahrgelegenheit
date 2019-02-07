package com.PickmeUP.project.repository;

import com.PickmeUP.project.model.Repeat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepeatRepository extends JpaRepository<Repeat, Integer> {

    //      finds Repeat by id.
    //
    //      @param id                 id to be looked for.
    //      @return Repeat            the Repeat.
    Repeat findById(int id);
}