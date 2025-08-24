package com.Hbence.appointmentManagementAPI.service;

import java.time.LocalDateTime;

public class Response {
    private int status;
    private Object result;
    private LocalDateTime localDateTime;

    public Response(int status, Object result, LocalDateTime localDateTime) {
        this.status = status;
        this.result = result;
        this.localDateTime = localDateTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }
}
