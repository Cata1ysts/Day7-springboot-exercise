package com.oocl.training.advice;

import com.oocl.training.Util.DataBaseQueryException;
import com.oocl.training.Util.EmployeeInfoException;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Map;

@RestControllerAdvice
public class GolbalExceptionHandler {

    @ExceptionHandler(EmployeeInfoException.class)
    public ResponseEntity<?> handleEmployeeException(EmployeeInfoException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                Map.of(
                        "message","Illegal argument error",
                        "error", ex.getMessage(),
                        "timestamp", LocalDateTime.now()
                )
        );

    }

    @ExceptionHandler(DataBaseQueryException.class)
    public ResponseEntity<?> handleDataBaseQueryException(DataBaseQueryException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                Map.of(
                        "message","Data not found",
                        "error", ex.getMessage(),
                        "timestamp", LocalDateTime.now()
                )
        );

    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleOtherException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                Map.of(
                        "message","Internal Server Error",
                        "error", "未知错误",
                        "timestamp", LocalDateTime.now()
                )
        );

    }
}
