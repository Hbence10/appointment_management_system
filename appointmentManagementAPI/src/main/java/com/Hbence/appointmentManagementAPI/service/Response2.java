package com.Hbence.appointmentManagementAPI.service;

import java.time.LocalDate;

public class Response2 {
    private String statusLine;
    private LocalDate date;
    private String contentType = "application/json";
    private String message;
    private Object body;

    public Response2(String statusLine, LocalDate date, String contentType, String message, Object body) {
        this.statusLine = statusLine;
        this.date = date;
        this.contentType = contentType;
        this.message = message;
        this.body = body;
    }

    public String getStatusLine() {
        return statusLine;
    }

    public void setStatusLine(String statusLine) {
        this.statusLine = statusLine;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }
}
