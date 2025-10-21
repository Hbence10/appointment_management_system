package com.Hbence.appointmentManagementAPI.repository;

import com.Hbence.appointmentManagementAPI.entity.ReservedDates;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface ReservedDateRepository extends JpaRepository<ReservedDates, Long> {

    @Procedure(name = "getReservedDatesOfPeriod", procedureName = "getReservedDatesOfPeriod")
    List<ReservedDates> reservedDatesByDate(@Param("startDateIN") LocalDate startDate, @Param("endDateIN") LocalDate endDate);

    @Procedure(name = "getReservedDateByDate", procedureName = "getReservedDateByDate")
    ReservedDates getReservedDateByDate(@Param("dateIN") LocalDate date);

    @Procedure(name = "getReservedDateBetweenTwoDateByDate", procedureName = "getReservedDateBetweenTwoDateByDate")
    ReservedDates getReservedDateBetweenTwoDateByDate(@Param("startDateIN") LocalDate startDate, @Param("endDateIN") LocalDate endDate, @Param("dateIN") LocalDate dateIN);
}


