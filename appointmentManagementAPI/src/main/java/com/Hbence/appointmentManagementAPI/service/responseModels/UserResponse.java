package com.Hbence.appointmentManagementAPI.service.responseModels;

import com.Hbence.appointmentManagementAPI.entity.User;

import java.time.LocalDateTime;

public class UserResponse {
    private int status;
    private User result;
    private LocalDateTime localDateTime;

    public UserResponse(int status, User result, LocalDateTime localDateTime) {
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

    public User getResult() {
        return result;
    }

    public void setResult(User result) {
        this.result = result;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }
}
