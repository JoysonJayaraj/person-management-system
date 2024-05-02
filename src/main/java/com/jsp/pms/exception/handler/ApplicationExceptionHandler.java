package com.jsp.pms.exception.handler;

import com.jsp.pms.exception.PersonNotFoundException;
import com.jsp.pms.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ApplicationExceptionHandler {

    @ExceptionHandler(value = {PersonNotFoundException.class})
    public ResponseEntity<ErrorResponse> handler(PersonNotFoundException e) {
        return ResponseEntity.<ErrorResponse>ofNullable(ErrorResponse.builder()
                .error(e.getMessage())
                .status(HttpStatus.NOT_FOUND)
                .timestamp(LocalDateTime.now())
                .build());
    }
}
