package com.code.managerbackend.exception;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

public class ValidErrors {

    @Getter
    private List<String> errors;

    public ValidErrors(List<String> errors) {
        this.errors = errors;
    }

    public ValidErrors(String message) {
        this.errors = Arrays.asList(message);
    }
}
