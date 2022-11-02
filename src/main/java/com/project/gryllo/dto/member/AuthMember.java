package com.project.gryllo.dto.member;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@ToString
public class AuthMember {
	private String email;
	private String password;

	public AuthMember(String email, String password) {
		this.email = email;
		this.password = password;
	}
}
