package com.devsu.cuenta.bancaria.web.handlers;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@Setter
public class ApiError {
    private String code;
    private HttpStatus status;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp;

    private String message;
    private String cause;
    private String debugMessage;

    private ApiError() {
        timestamp = LocalDateTime.now();
    }

    ApiError(HttpStatus status) {
        this();
        this.status = status;
        code = String.valueOf(status.value());
    }

    ApiError(HttpStatus status, String message, Throwable ex) {
        this();
        this.status = status;
        code = String.valueOf(status.value());
        this.message = message;
        debugMessage = ex.getLocalizedMessage();
    }

    ApiError(HttpStatus status, Throwable ex) {
        this();
        this.status = status;
        code = String.valueOf(status.value());
        message = "Unexpected error";
        debugMessage = ex.getLocalizedMessage();
    }
}
