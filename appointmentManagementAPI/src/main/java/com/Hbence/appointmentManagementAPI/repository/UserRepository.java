package com.Hbence.appointmentManagementAPI.repository;


import com.Hbence.appointmentManagementAPI.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {
    @Procedure(name = "getUserByUsername", procedureName = "getUserByUsername")
    User login(@Param("usernameIN") String username);
}
