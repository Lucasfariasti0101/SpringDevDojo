package com.devdojo.academy.springboot.service.exception;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class FieldsValidationException extends ExceptionDetail{
    private final String fields;
    private final String fieldMessage;
}
