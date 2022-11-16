package com.project.gryllo.member.entity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.project.gryllo.common.exception.form.register.InvalidEmailException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class PrivacyTest {


    @Nested
    @DisplayName("이메일 확인")
    class isEmailTest {

        @DisplayName("성공")
        @ParameterizedTest
        @ValueSource(strings = {"1@1", "JUN_young.c-123@naver.com"})
        void success(String email) {
            //given
            //when
            boolean isEmail = Privacy.isEmail(email);

            //then
            assertThat(isEmail).isTrue();
        }

        @DisplayName("실패")
        @ParameterizedTest
        @EmptySource
        @ValueSource(strings = {"@1", "1@", " @1", " ", "@", "한글@1", "$@1", "+@1", "@@1", ")@1"})
        void fail(String email) {
            //given
            //when
            boolean isEmail = Privacy.isEmail(email);

            //then
            assertThat(isEmail).isFalse();
        }
    }

    @Nested
    @DisplayName("정적 팩토리 메소드 검증")
    class createTest {
        @DisplayName("성공")
        @ParameterizedTest
        @CsvSource({
                "JUN_young.c-123@naver.com, 1234",
        })
        void success(String email, String password) {
            //given
            //when
            //then
            assertThatNoException().isThrownBy(() -> Privacy.create(password, email));
        }


        @DisplayName("실패 - 유효하지 않은 이메일")
        @ParameterizedTest
        @CsvSource({
                "1@, 1234",
                "한글@naver.com, 1234",
        })
        void failByEmail(String email, String password) {
            //given
            //when
            //then
            assertThatThrownBy(() -> Privacy.create(password, email))
                    .isExactlyInstanceOf(InvalidEmailException.class);
        }
    }
}