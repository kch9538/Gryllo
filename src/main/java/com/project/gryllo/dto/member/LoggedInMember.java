package com.project.gryllo.dto.member;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@ToString
public class LoggedInMember {
	public static final String SESSION_USER = "sessionUser";

	private long id;
	private String email;
	private String nickName;
	private String userName;

	@Builder
	public LoggedInMember(long id, String email, String nickName, String userName) {
		this.id = id;
		this.email = email;
		this.nickName = nickName;
		this.userName = userName;
	}
}
