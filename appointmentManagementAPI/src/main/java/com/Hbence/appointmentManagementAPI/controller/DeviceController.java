package com.Hbence.appointmentManagementAPI.controller;

import com.Hbence.appointmentManagementAPI.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/devices")
public class DeviceController {

    private DeviceService deviceService;

    @Autowired
    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
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