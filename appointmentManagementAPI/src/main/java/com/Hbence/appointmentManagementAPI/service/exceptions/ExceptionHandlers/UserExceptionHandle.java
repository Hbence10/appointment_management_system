package com.Hbence.appointmentManagementAPI.service.exceptions.ExceptionHandlers;

import com.Hbence.appointmentManagementAPI.service.Response;
import com.Hbence.appointmentManagementAPI.service.exceptions.ExceptionType.UserNotFound;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDateTime;

@ControllerAdvice
public class UserExceptionHandle {

    @ExceptionHandler
    public ResponseEntity<Response> handleException(UserNotFound exc){
        Response error = new Response(
                HttpStatus.NOT_FOUND.value(),
                exc.getMessage(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    //Unique adat megduplázásakor
    @ExceptionHandler
    public ResponseEntity<String> handleDuplicateUniqueData (DataIntegrityViolationException exc){
        System.out.println(exc.getMessage());
        return new ResponseEntity<>(exc.getMessage(), HttpStatus.NOT_FOUND);
    }
}
