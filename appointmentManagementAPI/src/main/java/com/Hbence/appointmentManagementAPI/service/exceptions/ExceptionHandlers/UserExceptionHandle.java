package com.Hbence.appointmentManagementAPI.service.exceptions.ExceptionHandlers;

import com.Hbence.appointmentManagementAPI.service.exceptions.ExceptionSatusModel;
import com.Hbence.appointmentManagementAPI.service.exceptions.ExceptionType.UserNotFound;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@ControllerAdvice
public class UserExceptionHandle {
    /*
    * User - Mire dobhat hibát/Mit kell elintezni?
    *
    *
    * */

    @ExceptionHandler
    public ResponseEntity<ExceptionSatusModel> handleException(UserNotFound exc){
        ExceptionSatusModel error = new ExceptionSatusModel(
                HttpStatus.NOT_FOUND.value(),
                exc.getMessage(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
}
