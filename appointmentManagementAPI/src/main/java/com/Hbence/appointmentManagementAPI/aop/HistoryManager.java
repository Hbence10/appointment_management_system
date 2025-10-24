package com.Hbence.appointmentManagementAPI.aop;

import com.Hbence.appointmentManagementAPI.repository.HistoryRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class HistoryManager {

    private final HistoryRepository historyRepository;

    /*
    *   functionok amikhez kellene az AOP:
    *   Létrehozás
    *                           - addDeviceCategory
    *                           - addDevice
    *                           - addNewNews
    *                           -
    *                           -
    * */
}
