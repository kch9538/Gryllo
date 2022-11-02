package com.project.gryllo.dto.auth;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Getter
@ToString
public class AuthTokenResponse {
	private String access_token;
	private String scope;
}
