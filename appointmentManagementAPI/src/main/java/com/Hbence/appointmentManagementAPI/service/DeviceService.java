package com.Hbence.appointmentManagementAPI.service;

import com.Hbence.appointmentManagementAPI.entity.Devices;
import com.Hbence.appointmentManagementAPI.entity.DevicesCategory;
import com.Hbence.appointmentManagementAPI.repository.DeviceCategoryRepository;
import com.Hbence.appointmentManagementAPI.repository.DeviceRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Transactional
@Service
public class DeviceService {
    private final DeviceRepository deviceRepository;
    private final DeviceCategoryRepository deviceCategoryRepository;

    @Autowired
    public DeviceService(DeviceRepository deviceRepository, DeviceCategoryRepository deviceCategoryRepository) {
        this.deviceRepository = deviceRepository;
        this.deviceCategoryRepository = deviceCategoryRepository;
    }

    //Eszkoz_kategoria
    public ResponseEntity<List<DevicesCategory>> getAllDevicesByCategory(){
        List<DevicesCategory> devicesCategoryList = deviceCategoryRepository.findAll();
        return ResponseEntity.ok(devicesCategoryList);
    }

    public ResponseEntity<DevicesCategory> addDeviceCategory(DevicesCategory newDevicesCategory){
        return null;
    }

    public ResponseEntity<String> deleteDevicesCategory(Long id){
        return null;
    }

    public ResponseEntity<DevicesCategory> updateDevicesCategory (DevicesCategory updatedDevicesCategory){
        return null;
    }

    //Maga_az_eszkoz
    public ResponseEntity<Devices> updateDevice (Devices updatedDevice){
        return null;
    }

    public ResponseEntity<Devices> addDevice(Devices newDevice){
        return null;
    }

    public ResponseEntity<String> deleteDevice (Long id){
        return null;
    }
}
