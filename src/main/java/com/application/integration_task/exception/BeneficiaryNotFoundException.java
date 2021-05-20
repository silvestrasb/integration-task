package com.application.integration_task.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class BeneficiaryNotFoundException extends RuntimeException {

    public BeneficiaryNotFoundException(String message) {
        super(message);
    }

}