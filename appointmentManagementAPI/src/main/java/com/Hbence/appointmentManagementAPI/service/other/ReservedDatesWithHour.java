package com.Hbence.appointmentManagementAPI.service.other;

import com.Hbence.appointmentManagementAPI.entity.ReservedHours;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class ReservedDatesWithHour {
    private Long id;
    private LocalDate date;
    private Boolean isHoliday = false;
    private Boolean isClosed = false;
    private Boolean isFull = false;
    private List<ReservedHours> reservedHours;

    public ReservedDatesWithHour(Long id, LocalDate date, Boolean isHoliday, Boolean isClosed, Boolean isFull, List<ReservedHours> reservedHours) {
        this.id = id;
        this.date = date;
        this.isHoliday = isHoliday;
        this.isClosed = isClosed;
        this.isFull = isFull;
        this.reservedHours = reservedHours;
    }
}
