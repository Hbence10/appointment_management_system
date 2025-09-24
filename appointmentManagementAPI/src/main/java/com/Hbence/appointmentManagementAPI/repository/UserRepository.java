package com.Hbence.appointmentManagementAPI.repository;


import com.Hbence.appointmentManagementAPI.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<Users, Long> {
    @Procedure(name = "getUserByUsername", procedureName = "getUserByUsername")
    Users login(@Param("usernameIN") String username);
}
