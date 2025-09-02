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
    List<Reservations> reservations(@Param("userIdIN") Integer userId);

    @Procedure(name = "getReservationByDate", procedureName = "getReservationByDate")
    List<Reservations> getReservationByDate(@Param("dateIN") LocalDate wantedDate);

    @Procedure(name = "makeReservation", procedureName = "makeReservation")
    Long makeReservation(
            @Param("firstNameIN") String firstName,
            @Param("lastNameIN") String lastName,
            @Param("emailIN") String email,
            @Param("phoneNumberIN") String phoneNumber,
            @Param("commentIN") String comment,
            @Param("reservationTypeIN") String reservationType,
            @Param("userIdIN") String userId,
            @Param("paymentMethodIN") String paymentMethodId,
            @Param("statusIN") String statusId
    );
}
