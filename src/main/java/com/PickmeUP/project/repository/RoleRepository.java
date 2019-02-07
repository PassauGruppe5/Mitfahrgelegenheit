package com.PickmeUP.project.repository;

import com.PickmeUP.project.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    //      finds Role by role.
    //
    //      @param role               role to be looked for.
    //      @return Role              the Role.
    Role findByRole(String role);

}