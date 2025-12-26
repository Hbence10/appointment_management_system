package com.Hbence.appointmentManagementAPI.service;

import com.Hbence.appointmentManagementAPI.entity.Devices;
import com.Hbence.appointmentManagementAPI.entity.DevicesCategory;
import com.Hbence.appointmentManagementAPI.repository.DeviceCategoryRepository;
import com.Hbence.appointmentManagementAPI.repository.DeviceRepository;
import com.Hbence.appointmentManagementAPI.service.other.DeviceWithDeviceCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.validation.ConstraintViolationException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Date;
import java.util.List;

@Transactional(noRollbackFor = {DataIntegrityViolationException.class, ConstraintViolationException.class, SQLIntegrityConstraintViolationException.class, SQLException.class})
@Service
@RequiredArgsConstructor
public class DeviceService {
    private final DeviceRepository deviceRepository;
    private final DeviceCategoryRepository deviceCategoryRepository;

    //Eszkoz kategoria
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
                return ResponseEntity.status(415).body("invalidObject");
            } else {
                newDevicesCategory.setName(newDevicesCategory.getName().trim());
                return ResponseEntity.ok(deviceCategoryRepository.save(newDevicesCategory));
            }
        } catch (DataIntegrityViolationException e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResponseEntity.status(409).body("duplicateDeviceCategoryName");
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

            DevicesCategory searchedDeviceCategory = deviceCategoryRepository.findById(id).orElse(null);

            if (searchedDeviceCategory == null || searchedDeviceCategory.getIsDeleted()) {
                return ResponseEntity.notFound().build();
            } else {
                searchedDeviceCategory.setIsDeleted(true);
                searchedDeviceCategory.setDeletedAt(new Date());
                return ResponseEntity.ok().build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @PreAuthorize("hasAnyRole('admin', 'superAdmin')")
    public ResponseEntity<Object> updateDevicesCategory(DevicesCategory updatedDevicesCategory) {
        try {
            if (updatedDevicesCategory == null) {
                return ResponseEntity.status(422).build();
            }

            if (updatedDevicesCategory.getId() == null) {
                return ResponseEntity.notFound().build();
            } else {
                updatedDevicesCategory.setName(updatedDevicesCategory.getName().trim());
                return ResponseEntity.ok(deviceCategoryRepository.save(updatedDevicesCategory));
            }
        } catch (DataIntegrityViolationException e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResponseEntity.status(409).body("duplicateDeviceCategoryName");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    //Maga_az_eszkoz
    @PreAuthorize("hasAnyRole('admin', 'superAdmin')")
    public ResponseEntity<Object> updateDevice(Devices updatedDevice) {
        try {
            if (updatedDevice == null) {
                return ResponseEntity.status(422).build();
            }

            DevicesCategory searchedDeviceCategory = deviceCategoryRepository.findById(updatedDevice.getCategoryId().getId()).orElse(null);

            if (searchedDeviceCategory == null) {
                return ResponseEntity.status(404).body("deviceCategoryDoesntExist");
            } else if (updatedDevice.getId() == null) {
                return ResponseEntity.status(404).body("deviceDoesntExist");
            } else {
                updatedDevice.setName(updatedDevice.getName().trim());
                return ResponseEntity.ok(deviceRepository.save(updatedDevice));
            }
        } catch (DataIntegrityViolationException e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResponseEntity.status(409).body("duplicateDeviceName");
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
                return ResponseEntity.status(415).body("invalidObject");
            } else {
                DevicesCategory searchedCategory = deviceCategoryRepository.findById(newDevice.getCategoryId().getId()).orElse(null);
                if (searchedCategory == null || searchedCategory.getIsDeleted()) {
                    return ResponseEntity.notFound().build();
                }
                newDevice.setName(newDevice.getName().trim());
                Devices newD = new Devices(newDevice.getName(), newDevice.getAmount(), newDevice.getCategoryId());
                return ResponseEntity.ok(deviceRepository.save(newD));
            }
        } catch (DataIntegrityViolationException e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            if (e.getMessage().contains("Duplicate entry")) {
                return ResponseEntity.status(409).body("duplicateDeviceName");
            }
            return ResponseEntity.internalServerError().build();
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

            Devices searchedDevice = deviceRepository.findById(id).orElse(null);
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

/*
 * HTTP STATUS KODOK:
 *   - 200: Sikeres muvelet
 *   - 404: Not Found
 *   - 409: Mar foglalt nev
 *   - 415: Unsupported Media Type --> Ha az adott adat invalid
 *   - 422: Hianyzo parameter/response body
 *   - 500: Internal Server Error
 * */