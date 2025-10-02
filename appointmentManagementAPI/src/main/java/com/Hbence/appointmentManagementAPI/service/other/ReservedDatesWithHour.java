package com.Hbence.appointmentManagementAPI.service.other;

import com.Hbence.appointmentManagementAPI.entity.ReservedHours;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ReservedDatesWithHour {
    private Long id;
    private LocalDate date;
    private Boolean isHoliday = false;
    private Boolean isClosed = false;
    private Boolean isFull = false;
    private List<ReservedHours> reservedHours;

}
