package com.project.gryllo.member.controller.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberSaveForm {

    @NotBlank(message = "이메일을 입력해주세요")
    private String email;

    @NotBlank(message = "성명을 입력해주세요")
    @Size(max = 29, message = "성명을 30자 미만으로 입력하세요.")
    private String name;

    @NotBlank(message = "사용자 이름을 입력해주세요")
    @Size(max = 29, message = "사용자 이름을 30자 미만으로 입력하세요.")
    private String nickname;

    @NotBlank(message = "비밀번호를 입력해주세요")
    @Size(min = 4, message = "비밀번호는 4글자 이상 입력해주세요.")
    private String password;
}
