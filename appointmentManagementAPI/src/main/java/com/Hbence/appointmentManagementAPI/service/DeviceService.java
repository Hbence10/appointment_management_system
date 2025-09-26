package com.Hbence.appointmentManagementAPI.service;

import com.Hbence.appointmentManagementAPI.entity.Devices;
import com.Hbence.appointmentManagementAPI.entity.DevicesCategory;
import com.Hbence.appointmentManagementAPI.repository.DeviceCategoryRepository;
import com.Hbence.appointmentManagementAPI.repository.DeviceRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class DeviceService {
    private final DeviceRepository deviceRepository;
    private final DeviceCategoryRepository deviceCategoryRepository;

    //Eszkoz_kategoria
    public ResponseEntity<List<DevicesCategory>> getAllDevicesByCategory() {
        List<DevicesCategory> devicesCategoryList = deviceCategoryRepository.findAll();
        return ResponseEntity.ok(devicesCategoryList);
    }

    public ResponseEntity<Object> addDeviceCategory(DevicesCategory newDevicesCategory) {
        newDevicesCategory.setName(newDevicesCategory.getName().trim());
        return ResponseEntity.ok(deviceCategoryRepository.save(newDevicesCategory));
    }

    public ResponseEntity<String> deleteDevicesCategory(Long id) {
        return null;
    }

    public ResponseEntity<DevicesCategory> updateDevicesCategory(DevicesCategory updatedDevicesCategory) {
        if(updatedDevicesCategory.getId() == null){
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(deviceCategoryRepository.save(updatedDevicesCategory));
        }
    }

    //Maga_az_eszkoz
    public ResponseEntity<Devices> updateDevice(Devices updatedDevice) {
        if(updatedDevice.getId() == null){
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(deviceRepository.save(updatedDevice));
        }
    }

    public ResponseEntity<Object> addDevice(Devices newDevice) {
        List<DevicesCategory> devicesCategoryList = deviceCategoryRepository.findAll();
        if(!devicesCategoryList.contains(newDevice.getCategoryId())){
            return ResponseEntity.status(409).body("invalidDeviceCategory");
        } else if (newDevice.getId() != null){
            return ResponseEntity.status(422).body("invalidInput");
        } else {
            return ResponseEntity.ok(deviceRepository.save(newDevice));
        }
    }

    public ResponseEntity<String> deleteDevice(Long id) {
        return null;
    }
}
