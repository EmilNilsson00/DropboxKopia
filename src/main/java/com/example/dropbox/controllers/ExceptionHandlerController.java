package com.example.dropbox.controllers;


import Exceptions.InvalidTokenException;
import Exceptions.NotfoundException;
import Exceptions.UnauthorizedException;
import com.example.dropbox.dtos.ExceptionDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<ExceptionDto> InvalidTokenExceptionResponse(InvalidTokenException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ExceptionDto.builder()
                        .message(e.getMessage())
                        .build());
    }

    @ExceptionHandler(NotfoundException.class)
    public ResponseEntity<ExceptionDto> NotfoundExceptionResponse(NotfoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                ExceptionDto.builder()
                        .message(e.getMessage())
                        .build());
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ExceptionDto> UnauthorizedExceptionResponse(UnauthorizedException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                ExceptionDto.builder()
                        .message(e.getMessage())
                        .build());
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionDto> handleException(Exception e) {
        System.out.println(e.getMessage() + " Exception.class");
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                ExceptionDto.builder()
                        .message("Internal Server Error")
                        .build());
    }
}
