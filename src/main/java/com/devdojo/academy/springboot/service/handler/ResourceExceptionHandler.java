package com.devdojo.academy.springboot.service.handler;

import com.devdojo.academy.springboot.service.exception.BadRequestException;
import com.devdojo.academy.springboot.service.exception.ErrorDetails;
import com.devdojo.academy.springboot.service.exception.FieldsValidationException;
import com.devdojo.academy.springboot.service.exception.ObjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<ErrorDetails> objectNotFound(ObjectNotFoundException e, HttpServletRequest request) {
        int status = HttpStatus.NOT_FOUND.value();
        ErrorDetails error = ErrorDetails.builder()
                .message("Anime not found." + e.getMessage())
                .timestamp(Instant.now())
                .status(status)
                .path(request.getRequestURI())
                .build();
        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<FieldsValidationException>
    badRequestException(MethodArgumentNotValidException e, HttpServletRequest request) {

        int status = HttpStatus.BAD_REQUEST.value();

        List<FieldError> fieldsList = e.getBindingResult().getFieldErrors();
        String fields = fieldsList.stream().map(FieldError::getField).collect(Collectors.joining(", "));
        String fieldsMessage = fieldsList.stream().map(FieldError::getDefaultMessage).collect(Collectors.joining(", "));

        FieldsValidationException error = FieldsValidationException.builder()
                .message("Bad request, invalid fields.")
                .timestamp(Instant.now())
                .status(status)
                .path(request.getRequestURI())
                .fields(fields)
                .fieldMessage(fieldsMessage)
                .build();
        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorDetails> badRequestException(BadRequestException e, HttpServletRequest request) {
        int status = HttpStatus.BAD_REQUEST.value();
        ErrorDetails error = ErrorDetails.builder()
                .message("We were unable to fulfill your request, please check the information.")
                .timestamp(Instant.now())
                .status(status)
                .path(request.getRequestURI())
                .build();
        return ResponseEntity.status(status).body(error);
    }
}

