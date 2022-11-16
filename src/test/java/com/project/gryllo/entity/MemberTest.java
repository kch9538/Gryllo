package com.project.gryllo.entity;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Java6Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class MemberTest {

	public static final Member member = Member.builder()
		.email("test@test.com")
		.userName("테스트")
		.nickName("test")
		.password("Test1234!")
		.build();

	@Test
	void edit_Member() {
		String sessionEmail = "test@test.com";
		Member update = Member.builder()
			.email("test@test.com")
			.userName("테스트")
			.nickName("test")
			.password("Test1234!")
			.build();
		member.update(update, sessionEmail);
		assertThat(member).isEqualTo(update);
	}

	@Test
	void edit_Member_Fail() {
		String sessionEmail = "test@test.com";
		Member updatedUser = Member.builder()
			.email("test@test.com")
			.userName("테트리스")
			.nickName("test")
			.password("Test1234!")
			.build();
		assertThatThrownBy(() -> member.update(updatedUser, sessionEmail)).isInstanceOf(
			RuntimeException.class);
	}

	@Test
	void change_to_Oauth_Member() {
		member.changeToOAuthUser();
		assertThat(member.isOauthUser()).isEqualTo(true);
	}

	@Test
	void password_check_Fail() {
		String password = member.getPassword() + "fail";
		assertThatThrownBy(() -> member.checkPassword(password)).isInstanceOf(
			RuntimeException.class);
	}

	@Test
	void password_check() {
		String password = member.getPassword();
		assertThatCode(() -> member.checkPassword(password)).doesNotThrowAnyException();
	}

	@Test
	void email_check_Fail() {
		String email = member.getEmail() + "fail";
		assertThatThrownBy(() -> member.checkEmail(email)).isInstanceOf(RuntimeException.class);
	}

	@Test
	void email_check() {
		String email = member.getEmail();
		assertThatCode(() -> member.checkEmail(email)).doesNotThrowAnyException();
	}
}