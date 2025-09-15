package com.Hbence.appointmentManagementAPI.exceptionTypes;

public class InvalidPassword extends RuntimeException {
    public InvalidPassword(String message) {
        super(message);
    }
}
