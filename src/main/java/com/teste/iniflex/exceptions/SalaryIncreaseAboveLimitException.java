package com.teste.iniflex.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class SalaryIncreaseAboveLimitException extends RuntimeException {
    public SalaryIncreaseAboveLimitException(String message) {
        super(message);
    }
}
