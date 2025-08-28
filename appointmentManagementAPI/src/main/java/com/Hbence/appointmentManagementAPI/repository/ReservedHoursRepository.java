package com.Hbence.appointmentManagementAPI.repository;

import com.Hbence.appointmentManagementAPI.entity.ReservedHours;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ReservedHoursRepository extends JpaRepository<ReservedHours, Long> {

    @Procedure(name = "getReservedHoursByDate", procedureName = "getReservedHoursByDate")
    List<Long> getReservationByMonth(@Param("dateIN") LocalDate dateIN);
}
