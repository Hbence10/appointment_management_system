package com.Hbence.appointmentManagementAPI.service.exceptions;

import java.time.LocalDateTime;

public class ExceptionSatusModel {
    private String Status;
    private int StatusCode;
    private LocalDateTime timestamp;

    public ExceptionSatusModel(int status, String message, LocalDateTime timestamp) {
        StatusCode = status;
        this.Status = message;
        this.timestamp = timestamp;
    }

    public int getStatusCode() {
        return StatusCode;
    }

    public void setStatusCode(int statusCode) {
        StatusCode = statusCode;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        this.Status = status;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
