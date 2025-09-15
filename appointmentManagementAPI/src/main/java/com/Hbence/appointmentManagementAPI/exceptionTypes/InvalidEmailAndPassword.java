package com.Hbence.appointmentManagementAPI.exceptionTypes;

public class InvalidEmailAndPassword extends RuntimeException {
    public InvalidEmailAndPassword(String message) {
        super(message);
    }
}
