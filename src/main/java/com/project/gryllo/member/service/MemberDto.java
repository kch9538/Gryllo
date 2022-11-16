package com.project.gryllo.member.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MemberDto {

    private String email;
    private String name;
    private String nickname;
    private String password;
}
