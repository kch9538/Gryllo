package com.project.gryllo.member.entity;

import static com.project.gryllo.common.exception.form.FormExceptionType.INVALID_EMAIL;

import javax.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class Privacy {

	private String password;

	private String email;

	public static Privacy create(String password, String email) {
		Privacy privacy = new Privacy();
		privacy.setPassword(password);
		privacy.setEmail(email);

		return privacy;
	}

	private void setPassword(String password) {
		this.password = password;
	}

	private void setEmail(String email) {

		validateEmail(email);
		this.email = email;
	}


	private void validateEmail(String email) {
		if (!isEmail(email)) {
			throw INVALID_EMAIL.getException();
		}
	}

	public static boolean isEmail(String email) {
		return email.matches("^[A-Za-z\\d_.-]+@(.+)$");
	}
}
