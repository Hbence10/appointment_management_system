package com.Hbence.appointmentManagementAPI.service.exceptions;

import java.sql.Timestamp;

public class ExceptionSatusModel {
    private int Status;
    private String message;
    private Timestamp timestamp;

    public ExceptionSatusModel(int status, String message, Timestamp timestamp) {
        Status = status;
        this.message = message;
        this.timestamp = timestamp;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
