package com.Hbence.appointmentManagementAPI.repository;

import com.Hbence.appointmentManagementAPI.entity.Devices;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceRepository extends JpaRepository<Devices, Long> {
}
