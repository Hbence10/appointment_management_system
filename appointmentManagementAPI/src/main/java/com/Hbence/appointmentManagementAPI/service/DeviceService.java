package com.Hbence.appointmentManagementAPI.service;

import com.Hbence.appointmentManagementAPI.entity.Devices;
import com.Hbence.appointmentManagementAPI.entity.DevicesCategory;
import com.Hbence.appointmentManagementAPI.repository.DeviceCategoryRepository;
import com.Hbence.appointmentManagementAPI.repository.DeviceRepository;
import com.Hbence.appointmentManagementAPI.service.other.DeviceWithDeviceCategory;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class DeviceService {
    private final DeviceRepository deviceRepository;
    private final DeviceCategoryRepository deviceCategoryRepository;

    //Eszkoz_kategoria
    public ResponseEntity<List<DevicesCategory>> getAllDevicesByCategory() {
        try {
            List<DevicesCategory> devicesCategoryList = deviceCategoryRepository.findAll().stream().filter(devicesCategory -> !devicesCategory.getIsDeleted()).toList();
            for (DevicesCategory i : devicesCategoryList) {
                i.setDevicesList(i.getDevicesList().stream().filter(device -> !device.getIsDeleted()).toList());
            }
            return ResponseEntity.ok(devicesCategoryList);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @PreAuthorize("hasAnyRole('admin', 'superAdmin')")
    public ResponseEntity<Object> addDeviceCategory(DevicesCategory newDevicesCategory) {
        try {
            if (newDevicesCategory == null) {
                return ResponseEntity.status(422).build();
            }

            if (newDevicesCategory.getId() != null) {
                return ResponseEntity.status(422).body("invalidInput");
            } else {
                System.out.println(newDevicesCategory);
                newDevicesCategory.setName(newDevicesCategory.getName().trim());
                return ResponseEntity.ok(deviceCategoryRepository.save(newDevicesCategory));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @PreAuthorize("hasAnyRole('admin', 'superAdmin')")
    public ResponseEntity<Boolean> deleteDevicesCategory(Long id) {
        try {
            if (id == null) {
                return ResponseEntity.status(422).build();
            }

            DevicesCategory searchedDeviceCategory = deviceCategoryRepository.findById(id).orElse(new DevicesCategory(null));

            if (searchedDeviceCategory.getId() == null || searchedDeviceCategory.getIsDeleted()) {
                return ResponseEntity.notFound().build();
            } else {
                searchedDeviceCategory.setIsDeleted(true);
                searchedDeviceCategory.setDeletedAt(LocalDateTime.now());
                return ResponseEntity.ok().build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @PreAuthorize("hasAnyRole('admin', 'superAdmin')")
    public ResponseEntity<DevicesCategory> updateDevicesCategory(DevicesCategory updatedDevicesCategory) {
        try {
            if (updatedDevicesCategory == null) {
                return ResponseEntity.status(422).build();
            }

            if (updatedDevicesCategory.getId() == null) {
                return ResponseEntity.notFound().build();
            } else {
                return ResponseEntity.ok(deviceCategoryRepository.save(updatedDevicesCategory));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    //Maga_az_eszkoz
    @PreAuthorize("hasAnyRole('admin', 'superAdmin')")
    public ResponseEntity<Object> updateDevice(DeviceWithDeviceCategory updatedDevice) {
        try {
            if (updatedDevice == null) {
                return ResponseEntity.status(422).build();
            }

            DevicesCategory searched = deviceCategoryRepository.findById(updatedDevice.getCategoryId().getId()).get();

            if (searched == null) {
                return ResponseEntity.status(409).body("invalidDeviceCategory");
            } else if (updatedDevice.getId() == null) {
                return ResponseEntity.notFound().build();
            } else {
                Devices updatedD = deviceRepository.findById(updatedDevice.getId()).get();
                updatedD.setName(updatedDevice.getName().trim());
                updatedD.setAmount(updatedDevice.getAmount());
                updatedD.setCategoryId(updatedDevice.getCategoryId());
                return ResponseEntity.ok(deviceRepository.save(updatedD));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @PreAuthorize("hasAnyRole('admin', 'superAdmin')")
    public ResponseEntity<Object> addDevice(DeviceWithDeviceCategory newDevice) {
        try {
            if (newDevice == null) {
                return ResponseEntity.status(422).build();
            }

            if (newDevice.getId() != null) {
                return ResponseEntity.status(422).body("invalidInput");
            } else {
                newDevice.setName(newDevice.getName().trim());
                Devices newD = new Devices(newDevice.getName(), newDevice.getAmount(), newDevice.getCategoryId());
                return ResponseEntity.ok(deviceRepository.save(newD));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @PreAuthorize("hasAnyRole('admin', 'superAdmin')")
    public ResponseEntity<String> deleteDevice(Long id) {
        try {
            if (id == null) {
                return ResponseEntity.status(422).build();
            }

            Devices searchedDevice = deviceRepository.findById(id).get();
            if (searchedDevice == null || searchedDevice.getIsDeleted()) {
                return ResponseEntity.notFound().build();
            } else {
                searchedDevice.setIsDeleted(true);
                searchedDevice.setDeletedAt(new Date());
                return ResponseEntity.ok().build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
}
