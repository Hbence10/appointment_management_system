package com.Hbence.appointmentManagementAPI.controller;

import com.Hbence.appointmentManagementAPI.entity.Devices;
import com.Hbence.appointmentManagementAPI.entity.DevicesCategory;
import com.Hbence.appointmentManagementAPI.service.DeviceService;
import com.Hbence.appointmentManagementAPI.service.other.DeviceWithDeviceCategory;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Operation(summary = "", description = "")
    @ApiResponses({

    })
    @GetMapping("/getAllCategory")
    public ResponseEntity<List<DevicesCategory>> getAllDevicesByCategory() {
        return deviceService.getAllDevicesByCategory();
    }

    @Operation(summary = "", description = "")
    @ApiResponses({

    })
    @PostMapping("/addCategory")
    public ResponseEntity<Object> addDeviceCategory(@RequestBody DevicesCategory newDevicesCategory) {
        System.out.println(newDevicesCategory);
        return deviceService.addDeviceCategory(newDevicesCategory);
    }

    @Operation(summary = "", description = "")
    @Parameter(name = "", description = "", required = true, in = ParameterIn.PATH)
    @ApiResponses({

    })
    @DeleteMapping("/deleteCategory/{id}")
    public ResponseEntity<Boolean> deleteDeviceCategory(@PathVariable("id") Long id) {
        return deviceService.deleteDevicesCategory(id);
    }

    @Operation(summary = "", description = "")
    @ApiResponses({

    })
    @PutMapping("/updateCategory")
    public ResponseEntity<DevicesCategory> updateDeviceCategory(@RequestBody DevicesCategory updatedDevicesCategory) {
        return deviceService.updateDevicesCategory(updatedDevicesCategory);
    }

    //Maga_az_eszkoz
    @Operation(summary = "", description = "")
    @ApiResponses({

    })
    @PutMapping("/update")
    public ResponseEntity<Object> updateDevice(@RequestBody Devices updatedDevice) {
        return deviceService.updateDevice(updatedDevice);
    }

    @Operation(summary = "", description = "")
    @ApiResponses({

    })
    @PostMapping("addDevice")
    public ResponseEntity<Object> addDevice(@RequestBody DeviceWithDeviceCategory newDevice) {
        return deviceService.addDevice(newDevice);
    }

    @Operation(summary = "", description = "")
    @Parameter(name = "", description = "", required = true, in = ParameterIn.PATH)
    @ApiResponses({

    })
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