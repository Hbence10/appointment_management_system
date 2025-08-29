package com.Hbence.appointmentManagementAPI.repository;

import com.Hbence.appointmentManagementAPI.entity.ReservationType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationTypeRepository extends JpaRepository <ReservationType, Long> {
}
