package com.application.integration_task.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BeneficiaryUniqueCodeDuplicateException extends RuntimeException {

    public BeneficiaryUniqueCodeDuplicateException(String message) {
        super(message);
    }

}