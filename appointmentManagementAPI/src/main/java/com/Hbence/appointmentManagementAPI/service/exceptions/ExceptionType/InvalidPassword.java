package com.Hbence.appointmentManagementAPI.service.exceptions.ExceptionType;

public class InvalidPassword extends RuntimeException {
    public InvalidPassword(String message) {
        super(message);
    }
}
