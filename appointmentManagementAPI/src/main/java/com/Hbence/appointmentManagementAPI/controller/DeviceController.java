package com.Hbence.appointmentManagementAPI.controller;

import com.Hbence.appointmentManagementAPI.entity.Devices;
import com.Hbence.appointmentManagementAPI.entity.DevicesCategory;
import com.Hbence.appointmentManagementAPI.service.DeviceService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/devices")
public class DeviceController {

    private final DeviceService deviceService;

    //Eszkoz_kategoria
    @GetMapping("/category")
    public ResponseEntity<List<DevicesCategory>> getAllDevicesByCategory(){
        return deviceService.getAllDevicesByCategory();
    }

    @PostMapping("/category")
    public ResponseEntity<Object> addDeviceCategory(@RequestBody DevicesCategory newDevicesCategory){
        return deviceService.addDeviceCategory(newDevicesCategory);
    }

    @DeleteMapping("/category/{id}")
    public ResponseEntity<String> deleteDeviceCategory(@PathVariable("id") Long id){
        return deviceService.deleteDevicesCategory(id);
    }

    @PutMapping("/category")
    public ResponseEntity<DevicesCategory> updateDeviceCategory(@RequestBody DevicesCategory updatedDevicesCategory){
        return deviceService.updateDevicesCategory(updatedDevicesCategory);
    }

    //Maga_az_eszkoz
    @PutMapping("")
    public ResponseEntity<Devices> updateDevice(@RequestBody Devices updatedDevice){
        return deviceService.updateDevice(updatedDevice);
    }

    @PostMapping("")
    public ResponseEntity<Devices> addDevice(@RequestBody Devices newDevice){
        return deviceService.addDevice(newDevice);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDevice(@PathVariable("id") Long id){
        return deviceService.deleteDevice(id);
    }

    //
    @ExceptionHandler
    public ResponseEntity<String> handleUniqueError(DataIntegrityViolationException e) {
        return ResponseEntity.status(409).body("duplicateCategory");
    }
}