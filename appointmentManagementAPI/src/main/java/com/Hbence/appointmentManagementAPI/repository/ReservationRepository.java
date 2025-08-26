package com.Hbence.appointmentManagementAPI.repository;

import com.Hbence.appointmentManagementAPI.entity.Reservations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservations, Long> {

    @Procedure(name = "getReservationByUserId", procedureName = "getReservationByUserId")
    ArrayList<Long> reservations(@Param("userIdIN") Integer userId);
}
