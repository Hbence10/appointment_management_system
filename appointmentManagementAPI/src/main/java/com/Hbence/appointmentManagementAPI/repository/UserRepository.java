package com.Hbence.appointmentManagementAPI.repository;


import com.Hbence.appointmentManagementAPI.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Integer> {

    @Procedure(name = "login", procedureName = "name")
    Integer asd(@Param("usernameIN") String username, @Param("passwordIN") String password);
}
