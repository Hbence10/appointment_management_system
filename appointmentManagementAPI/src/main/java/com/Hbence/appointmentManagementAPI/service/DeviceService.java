package com.Hbence.appointmentManagementAPI.service;

import com.Hbence.appointmentManagementAPI.entity.Devices;
import com.Hbence.appointmentManagementAPI.repository.DeviceCategoryRepository;
import com.Hbence.appointmentManagementAPI.repository.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.util.List;

@Transactional
@Service
public class DeviceService {
    private DeviceRepository deviceRepository;
    private DeviceCategoryRepository deviceCategoryRepository;

    @Autowired
    public DeviceService(DeviceRepository deviceRepository, DeviceCategoryRepository deviceCategoryRepository) {
        this.deviceRepository = deviceRepository;
        this.deviceCategoryRepository = deviceCategoryRepository;
    }

    public List<Devices> getAllDevice(){
        return deviceRepository.findAll();
    }
}
