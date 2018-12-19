package com.PickmeUP.project.validation;

public class EmailExistsError extends RuntimeException {

    public EmailExistsError(String message) {
        super(message);
    }

}
