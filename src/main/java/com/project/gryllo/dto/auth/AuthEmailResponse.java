package com.project.gryllo.dto.auth;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Getter
@ToString
public class AuthEmailResponse {
	private String email;
	private boolean primary;
	private boolean verified;
	private String visibility;
}
