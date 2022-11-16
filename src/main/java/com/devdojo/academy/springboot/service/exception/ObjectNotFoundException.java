package com.devdojo.academy.springboot.service.exception;

public class ObjectNotFoundException extends RuntimeException{
    public ObjectNotFoundException(String msg) {
        super(msg);
    }
}
