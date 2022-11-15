package com.project.gryllo.dto.member;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@ToString
public class MemberRegister {

	@Pattern(regexp = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$", message = "이메일 양식")
	private String email;

	@Size(min = 2, max = 10, message = "2~10자")
	private String nickName;

	@Size(min = 2, max = 10, message = "2~10자")
	private String userName;

	@Size(min = 4, max = 16, message = "4~16자")
	private String password;

	@Builder
	public MemberRegister(String nickName, String userName, String password, String email) {
		this.nickName = nickName;
		this.userName = userName;
		this.password = password;
		this.email = email;
	}

}
