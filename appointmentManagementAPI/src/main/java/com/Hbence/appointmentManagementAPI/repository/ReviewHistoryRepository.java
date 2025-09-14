package com.Hbence.appointmentManagementAPI.repository;

import com.Hbence.appointmentManagementAPI.entity.ReviewLikeHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;

public interface ReviewHistoryRepository extends JpaRepository<ReviewLikeHistory, Long> {
}
