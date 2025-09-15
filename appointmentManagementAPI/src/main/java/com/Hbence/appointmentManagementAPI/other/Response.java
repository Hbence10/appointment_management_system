package com.Hbence.appointmentManagementAPI.other;

import java.time.LocalDate;

public class Response {
    private Integer statusCode;
    private LocalDate date;
    private Object body;
    private String contentType = "application/json";

    public Response(Integer statusCode, Object body, LocalDate date) {
        this.statusCode = statusCode;
        this.date = date;
        this.body = body;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
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


    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }
}
