package com.project.gryllo.common.exception.form.login;


import com.project.gryllo.common.exception.form.CustomFormException;

public class IncorrectPasswordException extends CustomFormException {
    public IncorrectPasswordException(String message) {
        super(message);
    }

    public IncorrectPasswordException(String message, String field) {
        super(message, field);
    }

    public IncorrectPasswordException(String message, String field, String errorCode) {
        super(message, field, errorCode);
    }
}
