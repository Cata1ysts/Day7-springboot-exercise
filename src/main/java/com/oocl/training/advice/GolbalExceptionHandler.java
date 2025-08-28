package com.oocl.training.advice;

import com.oocl.training.Util.EmployeeException;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Map;

@RestControllerAdvice
public class GolbalExceptionHandler {

    @ExceptionHandler(EmployeeException.class)
    public ResponseEntity<?> handleEmployeeException(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                Map.of(
                        "message","Illegal argument error",
                        "error", ex.getMessage(),
                        "timestamp", LocalDateTime.now()
                )
        );

    }
}
