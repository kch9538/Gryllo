package com.project.gryllo.member.model;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class MemberInput {
	private String userId;
	private String nickName;
	private String password;

}
