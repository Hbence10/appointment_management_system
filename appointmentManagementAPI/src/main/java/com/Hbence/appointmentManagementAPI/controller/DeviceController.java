package com.Hbence.appointmentManagementAPI.controller;

import com.Hbence.appointmentManagementAPI.entity.Devices;
import com.Hbence.appointmentManagementAPI.entity.DevicesCategory;
import com.Hbence.appointmentManagementAPI.service.DeviceService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/devices")
public class DeviceController {

    private final DeviceService deviceService;

    //Eszkoz_kategoria
    @GetMapping("/getAllCategory")
    public ResponseEntity<List<DevicesCategory>> getAllDevicesByCategory() {
        return deviceService.getAllDevicesByCategory();
    }

    @PostMapping("/addCategory")
    public ResponseEntity<Object> addDeviceCategory(@RequestBody DevicesCategory newDevicesCategory) {
        System.out.println(newDevicesCategory);
        return deviceService.addDeviceCategory(newDevicesCategory);
    }

    @DeleteMapping("/deleteCategory/{id}")
    public ResponseEntity<Boolean> deleteDeviceCategory(@PathVariable("id") Long id) {
        return deviceService.deleteDevicesCategory(id);
    }

    @PutMapping("/updateCategory")
    public ResponseEntity<DevicesCategory> updateDeviceCategory(@RequestBody DevicesCategory updatedDevicesCategory) {
        return deviceService.updateDevicesCategory(updatedDevicesCategory);
    }

    //Maga_az_eszkoz
    @PutMapping("/update")
    public ResponseEntity<Devices> updateDevice(@RequestBody Devices updatedDevice) {
        return deviceService.updateDevice(updatedDevice);
    }

    @PostMapping("addDevice")
    public ResponseEntity<Object> addDevice(@RequestBody Devices newDevice) {
        return deviceService.addDevice(newDevice);

    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> deleteDevice(@PathVariable("id") Long id) {
        return deviceService.deleteDevice(id);
    }

    //Error Handling
    @ExceptionHandler
    public ResponseEntity<String> handleUniqueError(DataIntegrityViolationException e) {
        return ResponseEntity.status(409).body("duplicateCategory");
    }
}