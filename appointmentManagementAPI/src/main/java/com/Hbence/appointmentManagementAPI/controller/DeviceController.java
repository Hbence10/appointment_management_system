package com.Hbence.appointmentManagementAPI.controller;

import com.Hbence.appointmentManagementAPI.entity.Devices;
import com.Hbence.appointmentManagementAPI.entity.DevicesCategory;
import com.Hbence.appointmentManagementAPI.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/devices")
public class DeviceController {

    private DeviceService deviceService;

    @Autowired
    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @GetMapping("/")
    public List<Devices> getAllDevice(){
        return deviceService.getAllDevice();
    }

    //Kategoria szerint visszadobja az osszes ezkozt
    @GetMapping("/{categoryName}")
    public List<Devices> getAllDeviceByCategory(@PathVariable String categoryName){
        return null;
    }

    @GetMapping("/categories")
    public List<DevicesCategory> getAllDevicesCategory(){
        return null;
    }
}
    /*
    * Endpointok:
    *       - eszkoz kategoria lekerdezese
    *       - eszkoz kategoria torlese
    *       - eszkoz kategoria letrehozasa
    *       - eszkoz kategoria szerkesztese
    *       - eszkoz lekerdezese
            - eszkoz torlese
            - eszkoz letrehozasa
            - eszkoz szerkesztese
    *
    */