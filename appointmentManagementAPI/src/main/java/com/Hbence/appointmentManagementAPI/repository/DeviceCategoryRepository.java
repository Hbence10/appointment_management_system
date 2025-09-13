package com.Hbence.appointmentManagementAPI.repository;

import com.Hbence.appointmentManagementAPI.entity.DevicesCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceCategoryRepository extends JpaRepository<DevicesCategory, Long> {
}
