package com.Hbence.appointmentManagementAPI.service;

import com.Hbence.appointmentManagementAPI.entity.Devices;
import com.Hbence.appointmentManagementAPI.entity.DevicesCategory;
import com.Hbence.appointmentManagementAPI.repository.DeviceCategoryRepository;
import com.Hbence.appointmentManagementAPI.repository.DeviceRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class DeviceService {
    private final DeviceRepository deviceRepository;
    private final DeviceCategoryRepository deviceCategoryRepository;

    //Eszkoz_kategoria
    public ResponseEntity<List<DevicesCategory>> getAllDevicesByCategory() {
        List<DevicesCategory> devicesCategoryList = deviceCategoryRepository.findAll().stream().filter(devicesCategory -> !devicesCategory.getIsDeleted()).toList();
        return ResponseEntity.ok(devicesCategoryList);
    }

    @PreAuthorize("hasAnyRole('admin', 'superAdmin')")
    public ResponseEntity<Object> addDeviceCategory(DevicesCategory newDevicesCategory) {
        if (newDevicesCategory.getId() != null) {
            return ResponseEntity.status(422).body("invalidInput");
        } else {
            System.out.println(newDevicesCategory);
            newDevicesCategory.setName(newDevicesCategory.getName().trim());
            return ResponseEntity.ok(deviceCategoryRepository.save(newDevicesCategory));
        }
    }

    @PreAuthorize("hasAnyRole('admin', 'superAdmin')")
    public ResponseEntity<Boolean> deleteDevicesCategory(Long id) {
        DevicesCategory searchedDeviceCategory = deviceCategoryRepository.findById(id).orElse(new DevicesCategory(null));

        if (searchedDeviceCategory.getId() == null || searchedDeviceCategory.getIsDeleted()) {
            return ResponseEntity.notFound().build();
        } else {
            searchedDeviceCategory.setIsDeleted(true);
            searchedDeviceCategory.setDeletedAt(LocalDateTime.now());
            return ResponseEntity.ok(true);
        }
    }

    @PreAuthorize("hasAnyRole('admin', 'superAdmin')")
    public ResponseEntity<DevicesCategory> updateDevicesCategory(DevicesCategory updatedDevicesCategory) {
        if (updatedDevicesCategory.getId() == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(deviceCategoryRepository.save(updatedDevicesCategory));
        }
    }

    //Maga_az_eszkoz
    @PreAuthorize("hasAnyRole('admin', 'superAdmin')")
    public ResponseEntity<Devices> updateDevice(Devices updatedDevice) {
        if (updatedDevice.getId() == null) {
            return ResponseEntity.notFound().build();
        } else {
            updatedDevice.setName(updatedDevice.getName().trim());
            return ResponseEntity.ok(deviceRepository.save(updatedDevice));
        }
    }

    @PreAuthorize("hasAnyRole('admin', 'superAdmin')")
    public ResponseEntity<Object> addDevice(Devices newDevice) {
        List<DevicesCategory> devicesCategoryList = deviceCategoryRepository.findAll();
        if (!devicesCategoryList.contains(newDevice.getCategoryId())) {
            return ResponseEntity.status(409).body("invalidDeviceCategory");
        } else if (newDevice.getId() != null) {
            return ResponseEntity.status(422).body("invalidInput");
        } else {
            newDevice.setName(newDevice.getName().trim());
            return ResponseEntity.ok(deviceRepository.save(newDevice));
        }
    }

    @PreAuthorize("hasAnyRole('admin', 'superAdmin')")
    public ResponseEntity<String> deleteDevice(Long id) {
        return null;
    }
}
