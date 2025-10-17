package com.Hbence.appointmentManagementAPI.repository;


import com.Hbence.appointmentManagementAPI.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Long> {
    @Procedure(name = "getUserByUsername", procedureName = "getUserByUsername")
    Users login(@Param("usernameIN") String username);

    @Procedure(name = "getUserByEmail", procedureName = "getUserByEmail")
    Users getUserByEmail(@Param("emailIN") String username);

    @Procedure(name = "getAllEmail", procedureName = "getAllEmail")
    List<String> getAllEmail();

    @Procedure(name = "getAllAdmin", procedureName = "getAllAdmin")
    List<Users> getAllAdmin();

    Optional<Users> findByUsername(String email);
}
