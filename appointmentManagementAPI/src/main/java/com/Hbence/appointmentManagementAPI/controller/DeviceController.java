package com.Hbence.appointmentManagementAPI.controller;

import com.Hbence.appointmentManagementAPI.entity.Devices;
import com.Hbence.appointmentManagementAPI.entity.DevicesCategory;
import com.Hbence.appointmentManagementAPI.service.DeviceService;
import com.Hbence.appointmentManagementAPI.service.other.DeviceWithDeviceCategory;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/devices")
public class DeviceController {

    private final DeviceService deviceService;

    //Eszkoz_kategoria
    @Operation(summary = "Eszköz kategóriák lekérdezése", description = "Eszköz kategóriák lekérdezése")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sikeres lekérdezés", content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = DevicesCategory.class))
            )),
            @ApiResponse(responseCode = "500", description = "A server okozta hiba.", content = @Content)
    })
    @GetMapping("/getAllCategory")
    public ResponseEntity<List<DevicesCategory>> getAllDevicesByCategory() {
        return deviceService.getAllDevicesByCategory();
    }

    @Operation(summary = "Eszköz kategória létrehozása", description = "Eszköz kategória létrehozása")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "", required = true, content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = DevicesCategory.class, description = "")
    ))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sikeres létrehozás", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = DevicesCategory.class, description = "")
            )),
            @ApiResponse(responseCode = "409", description = "Duplikált kategória név", content = @Content),
            @ApiResponse(responseCode = "415", description = "invalidObject, Az adott object id-ja nem egyenlő null-lal", content = @Content),
            @ApiResponse(responseCode = "422", description = "Az endpoint meghívása requestBody nélkül", content = @Content),
            @ApiResponse(responseCode = "500", description = "A server okozta hiba.", content = @Content),
    })
    @PostMapping("/addCategory")
    public ResponseEntity<Object> addDeviceCategory(@RequestBody DevicesCategory newDevicesCategory) {
        return deviceService.addDeviceCategory(newDevicesCategory);
    }

    @Operation(summary = "Eszköz kategória törlése", description = "Eszköz kategória törlése id alapján")
    @Parameter(name = "id", description = "Az adott eszköz kategóriához tartozó id.", required = true, in = ParameterIn.PATH)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sikeres törlés", content = @Content),
            @ApiResponse(responseCode = "404", description = "Nem létező kategória törlése", content = @Content),
            @ApiResponse(responseCode = "422", description = "Az endpoint meghívása parameter nélkül.", content = @Content),
            @ApiResponse(responseCode = "500", description = "A server okozta hiba.", content = @Content)
    })
    @DeleteMapping("/deleteCategory/{id}")
    public ResponseEntity<Boolean> deleteDeviceCategory(@PathVariable("id") Long id) {
        return deviceService.deleteDevicesCategory(id);
    }

    @Operation(summary = "Eszköz kategória frissitése", description = "Eszköz kategória frissitése")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "", required = true, content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = DevicesCategory.class, description = "")
    ))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sikeres frissités", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = DevicesCategory.class, description = "")
            )),
            @ApiResponse(responseCode = "404", description = "Az adatbázisban nem létező eszköz kategória frissitése", content = @Content),
            @ApiResponse(responseCode = "409", description = "Duplikált eszköz kategória név", content = @Content),
            @ApiResponse(responseCode = "422", description = "Az endpoint meghivása requestBodz nélkül", content = @Content),
            @ApiResponse(responseCode = "500", description = "A server okozta hiba.", content = @Content)
    })
    @PutMapping("/updateCategory")
    public ResponseEntity<Object> updateDeviceCategory(@RequestBody DevicesCategory updatedDevicesCategory) {
        return deviceService.updateDevicesCategory(updatedDevicesCategory);
    }

    //Maga_az_eszkoz
    @Operation(summary = "Eszköz frissitése", description = "Eszköz frissitáse")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "", required = true, content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = Devices.class, description = "")
    ))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sikeres frissités", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Devices.class, description = "")
            )),
            @ApiResponse(responseCode = "404", description = "Nem létező eszköz frissitése vagy nem létező kategóriára frissités", content = @Content),
            @ApiResponse(responseCode = "409", description = "Duplikált eszköznév megadása", content = @Content),
            @ApiResponse(responseCode = "422", description = "Az endpoint meghívása requestBody nélkül", content = @Content),
            @ApiResponse(responseCode = "500", description = "A server által okozott hiba", content = @Content)
    })
    @PutMapping("/update")
    public ResponseEntity<Object> updateDevice(@RequestBody Devices updatedDevice) {
        return deviceService.updateDevice(updatedDevice);
    }

    @Operation(summary = "Eszköz hozzáadása", description = "Eszköz hozzáadása")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "", required = true, content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = Devices.class, description = "")
    ))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sikeres hozzáadás", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Devices.class, description = "")
            )),
            @ApiResponse(responseCode = "404", description = "Nem létező eszközkategória megadása", content = @Content),
            @ApiResponse(responseCode = "409", description = "Duplikált eszköznév megadása", content = @Content),
            @ApiResponse(responseCode = "415", description = "invalidObject, a megadott object id-ja nem egyenlő null-lal", content = @Content),
            @ApiResponse(responseCode = "422", description = "Az endpoint meghívása requestBody nélkül", content = @Content),
            @ApiResponse(responseCode = "500", description = "A server okozta hiba.", content = @Content)
    })
    @PostMapping("addDevice")
    public ResponseEntity<Object> addDevice(@RequestBody DeviceWithDeviceCategory newDevice) {
        return deviceService.addDevice(newDevice);
    }

    @Operation(summary = "Eszköz törlése", description = "Eszköz törlése id alapján")
    @Parameter(name = "id", description = "Az eszközhöz tartozó id.", required = true, in = ParameterIn.PATH)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sikeres törlés", content = @Content),
            @ApiResponse(responseCode = "404", description = "Nem létező eszköz törlés", content = @Content),
            @ApiResponse(responseCode = "422", description = "Az endpoint meghívása parameter nélkül", content = @Content),
            @ApiResponse(responseCode = "500", description = "A server okozta hiba.", content = @Content)
    })
    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> deleteDevice(@PathVariable("id") Long id) {
        return deviceService.deleteDevice(id);
    }
}