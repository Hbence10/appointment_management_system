package com.Hbence.appointmentManagementAPI.service.exceptions.ExceptionType;

public class InvalidEmail extends RuntimeException {
    public InvalidEmail(String message) {
        super(message);
    }
}
