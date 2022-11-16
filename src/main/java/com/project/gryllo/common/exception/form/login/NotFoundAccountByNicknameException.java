package com.project.gryllo.common.exception.form.login;


import com.project.gryllo.common.exception.form.CustomFormException;

public class NotFoundAccountByNicknameException extends CustomFormException {
    public NotFoundAccountByNicknameException(String message) {
        super(message);
    }

    public NotFoundAccountByNicknameException(String message, String field) {
        super(message, field);
    }

    public NotFoundAccountByNicknameException(String message, String field, String errorCode) {
        super(message, field, errorCode);
    }
}
