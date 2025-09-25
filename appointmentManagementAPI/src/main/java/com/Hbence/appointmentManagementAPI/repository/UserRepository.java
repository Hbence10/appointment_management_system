package com.Hbence.appointmentManagementAPI.repository;


import com.Hbence.appointmentManagementAPI.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    @Procedure(name = "getUserByUsername", procedureName = "getUserByUsername")
    User login(@Param("usernameIN") String username);

    @Procedure(name = "getUserByEmail", procedureName = "getUserByEmail")
    User getUserByEmail(@Param("emailIN") String username);

    @Procedure(name = "getAllEmail", procedureName = "getAllEmail")
    List<String> getAllEmail();
}
