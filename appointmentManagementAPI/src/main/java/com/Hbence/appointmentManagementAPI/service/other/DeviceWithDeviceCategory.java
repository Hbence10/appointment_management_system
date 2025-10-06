package com.Hbence.appointmentManagementAPI.service.other;

import com.Hbence.appointmentManagementAPI.entity.DevicesCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class DeviceWithDeviceCategory {
    private Long id;
    private String name;
    private int amount;
    private boolean isDeleted;
    private Date deletedAt;
    private DevicesCategory categoryId;
}
