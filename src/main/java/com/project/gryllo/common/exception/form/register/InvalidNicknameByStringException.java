package com.project.gryllo.common.exception.form.register;


import com.project.gryllo.common.exception.form.CustomFormException;

public class InvalidNicknameByStringException extends CustomFormException {
    public InvalidNicknameByStringException(String message) {
        super(message);
    }

    public InvalidNicknameByStringException(String message, String field) {
        super(message, field);
    }

    public InvalidNicknameByStringException(String message, String field, String errorCode) {
        super(message, field, errorCode);
    }
}
