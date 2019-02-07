package com.PickmeUP.project.repository;


import com.PickmeUP.project.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    //      finds User by email.
    //
    //      @param email                    email to be looked for.
    //      @return User                    the User.
    User findByEmail(String email);

    //      finds User by role.
    //
    //      @param id                       id to be looked for.
    //      @return User                    the User.
    User findById(int id);

}
