package com.epam.bankproject.bankproject.service.exception;

public class DuplicateEntityException extends RuntimeException {

    public DuplicateEntityException(String message){
        super(message);
    }
}
