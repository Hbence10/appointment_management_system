package com.Hbence.appointmentManagementAPI.exceptionTypes;

public class InvalidEmail extends RuntimeException {
    public InvalidEmail(String message) {
        super(message);
    }
}
