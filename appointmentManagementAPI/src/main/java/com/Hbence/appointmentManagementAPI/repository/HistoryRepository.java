package com.Hbence.appointmentManagementAPI.repository;

import com.Hbence.appointmentManagementAPI.entity.History;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoryRepository extends JpaRepository<History, Long> {
}
