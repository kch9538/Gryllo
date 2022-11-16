package com.project.gryllo.common.exception.form;

import com.project.gryllo.common.exception.CustomException;
import lombok.Getter;

@Getter
public class CustomFormException extends CustomException {
    private String field;
    private String errorCode;

    public CustomFormException(String message) {
        super(message);
    }

    public CustomFormException(String message, String field) {
        super(message);
        this.field = field;
    }

    public CustomFormException(String message, String field, String errorCode) {
        super(message);
        this.field = field;
        this.errorCode = errorCode;
    }
}
