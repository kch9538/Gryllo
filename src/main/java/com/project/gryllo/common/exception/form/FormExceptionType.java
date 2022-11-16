package com.project.gryllo.common.exception.form;

import com.project.gryllo.common.exception.CustomException;
import com.project.gryllo.common.exception.form.login.IncorrectPasswordException;
import com.project.gryllo.common.exception.form.login.NotFoundAccountByEmailException;
import com.project.gryllo.common.exception.form.login.NotFoundAccountByNicknameException;
import com.project.gryllo.common.exception.form.register.DuplicateEmailException;
import com.project.gryllo.common.exception.form.register.DuplicateNicknameException;
import com.project.gryllo.common.exception.form.register.InvalidEmailException;
import com.project.gryllo.common.exception.form.register.InvalidNicknameByNumberException;
import com.project.gryllo.common.exception.form.register.InvalidNicknameByStringException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FormExceptionType {
    //로그인
    NOT_FOUND_ACCOUNT_BY_NICKNAME(new NotFoundAccountByNicknameException(
            "입력한 사용자 이름을 사용하는 계정을 찾을 수 없습니다. 사용자 이름을 확인하고 다시 시도하세요.",
            "signInId", "incorrect")),
    NOT_FOUND_ACCOUNT_BY_Email(new NotFoundAccountByEmailException(
            "입력한 이메일을 사용하는 계정을 찾을 수 없습니다. 사용자의 이메일을 확인하고 다시 시도하세요.",
            "signInId", "incorrect")),
    INCORRECT_PASSWORD(new IncorrectPasswordException(
            "잘못된 비밀번호입니다. 다시 확인하세요.", "password", "incorrect")),

    // 회원가입
    DUPLICATE_NICKNAME(new DuplicateNicknameException(
            "다른 계정에서 이미 사용중인 사용자 이름입니다.", "nickname", "duplicate")),
    DUPLICATE_EMAIL(new DuplicateEmailException(
            "다른 계정에서 이미 사용중인 이메일입니다.", "email", "duplicate")),
    INVALID_NICKNAME_BY_NUMBER(new InvalidNicknameByNumberException(
            "사용자의 이름에 숫자만 포함할 수는 없습니다.", "nickname", "invalid")),
    INVALID_NICKNAME_BY_STRING(new InvalidNicknameByStringException(
            "사용자 이름에는 문자, 숫자, 밑줄 및 마침표만 사용할 수 있습니다.", "nickname", "invalid")),
    INVALID_EMAIL(new InvalidEmailException(
            "유효한 이메일 주소를 입력하세요.", "email", "invalid"));


    private final CustomException exception;
}
