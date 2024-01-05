package com.modelsis.backendAhamada.utils;

import lombok.Data;
import org.springframework.http.HttpStatus;
@Data
public class ErrorMessage {
    private String message;
    private int value;
    private  HttpStatus httpStatus;

    public ErrorMessage(String message, int value, HttpStatus httpStatus) {
        this.message = message;
        this.value = value;
        this.httpStatus = httpStatus;
    }
}
