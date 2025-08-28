package com.Hbence.appointmentManagementAPI.repository;

import com.Hbence.appointmentManagementAPI.entity.ReservedDates;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface ReservedDateRepository extends JpaRepository<ReservedDates, Long> {

    @Procedure(name = "getReservedDateByMonth", procedureName = "getReservedDateByMonth")
    List<Long> reservedDatesByDate(@Param("dateIN") LocalDate wantedDate);
}


