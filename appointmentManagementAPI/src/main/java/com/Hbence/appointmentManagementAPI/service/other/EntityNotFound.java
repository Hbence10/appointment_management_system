package com.Hbence.appointmentManagementAPI.service.other;

public class EntityNotFound extends RuntimeException {
    public EntityNotFound(String message) {
        super(message);
    }
}
