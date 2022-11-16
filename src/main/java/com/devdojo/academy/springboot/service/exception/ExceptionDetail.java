package com.devdojo.academy.springboot.service.exception;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.time.Instant;

@Data
@SuperBuilder
public class ExceptionDetail {
    protected Instant timestamp;
    protected int status;
    protected String message;
    protected String path;

}
