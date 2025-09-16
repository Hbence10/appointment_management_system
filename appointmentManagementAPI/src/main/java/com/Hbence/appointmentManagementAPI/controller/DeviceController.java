package com.Hbence.appointmentManagementAPI.controller;

import com.Hbence.appointmentManagementAPI.entity.DevicesCategory;
import com.Hbence.appointmentManagementAPI.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/devices")
public class DeviceController {

    private final DeviceService deviceService;

    @Autowired
    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    //Eszkoz_kategoria
    @GetMapping("/category")
    public ResponseEntity<List<DevicesCategory>> getAllDevicesByCategory(){
        return deviceService.getAllDevicesByCategory();
    }

    @PostMapping("/category")
    public ResponseEntity<String> addDeviceCategory(DevicesCategory newDevicesCategory){
        return ResponseEntity.ok("");
    }

    @DeleteMapping("/category/{id}")
    public ResponseEntity<String> deleteDeviceCategory(@PathVariable("id") long id){
        return ResponseEntity.ok("");
    }

    @PutMapping("/category")
    public ResponseEntity<String> updateDeviceCategory(@RequestBody DevicesCategory updatedDevicesCategory){
        return ResponseEntity.ok("");
    }

    //Maga_az_eszkoz

}