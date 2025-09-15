package com.Hbence.appointmentManagementAPI.service.exceptions.ExceptionType;

public class InvalidEmailAndPassword extends RuntimeException {
    public InvalidEmailAndPassword(String message) {
        super(message);
    }
}
