package com.project.gryllo.dto.auth;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Getter
@ToString
public class AuthRequest {
	private String code;
	private String client_id;
	private String client_secret;

	public AuthRequest(String code, String client_id, String client_secret) {
		this.code = code;
		this.client_id = client_id;
		this.client_secret = client_secret;
	}
}
