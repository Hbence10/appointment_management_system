package com.Hbence.appointmentManagementAPI.repository;

import com.Hbence.appointmentManagementAPI.entity.Reservations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.time.LocalDate;
import java.util.List;

@CrossOrigin(origins = {"http://localhost:4200"})
public interface ReservationRepository extends JpaRepository<Reservations, Long> {

    @Procedure(name = "getReservationByUserId", procedureName = "getReservationByUserId")
    List<Reservations> reservations(@Param("userIdIN") Long userId);

    @Procedure(name = "getReservationByDate", procedureName = "getReservationByDate")
    List<Long> getReservationByDate(@Param("dateIN") LocalDate wantedDate);

    @Procedure(name = "getReservationsByEmail", procedureName = "getReservationsByEmail")
    List<Reservations> getReservationsByEmail(@Param("emailIN") String email);

    @Procedure(name = "getAllReservationEmail", procedureName = "getAllReservationEmail")
    List<String> getAllReservationEmail();
}
