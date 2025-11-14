package com.Hbence.appointmentManagementAPI.repository;

import com.Hbence.appointmentManagementAPI.entity.CloseReason;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CloseReasonRepository extends JpaRepository<CloseReason, Long> {
}
