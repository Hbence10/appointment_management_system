package com.Hbence.appointmentManagementAPI.repository;

import com.Hbence.appointmentManagementAPI.entity.Reservations;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservations, Integer> {
}
