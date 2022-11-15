package com.project.gryllo.dto.member;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@ToString
public class MemberDto {
	@NotNull
	private long id;

	@Size(min = 2, max = 10, message = "닉네임은 2~10자")
	private String nickName;

	@Size(min = 2, max = 10, message = "이름은 2~10자")
	private String userName;

	@Size(min = 4, max = 16, message = "비밀번호는 4~16자")
	private String password;

	private String bio;
	@Builder
	public MemberDto(long id, String userName, String nickName, String password, String bio) {
		this.id = id;
		this.userName = userName;
		this.nickName = nickName;
		this.password = password;
		this.bio = bio;
	}
}
