package com.Hbence.appointmentManagementAPI.repository;

import com.Hbence.appointmentManagementAPI.entity.Reservations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservations, Long> {

    @Procedure(name = "getReservationByUserId", procedureName = "getReservationByUserId")
    List<Reservations> reservations(@Param("userIdIN") Integer userId);

    @Procedure(name = "getReservationByDate", procedureName = "getReservationByDate")
    List<Reservations> getReservationByDate(@Param("dateIN") LocalDate wantedDate);
}
