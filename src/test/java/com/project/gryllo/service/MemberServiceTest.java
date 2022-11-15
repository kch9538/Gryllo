package com.project.gryllo.service;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;

import com.project.gryllo.dto.auth.AuthMemberInfo;
import com.project.gryllo.dto.member.AuthMember;
import com.project.gryllo.dto.member.EditMember;
import com.project.gryllo.dto.member.LoggedInMember;
import com.project.gryllo.dto.member.MemberDto;
import com.project.gryllo.dto.member.MemberRegister;
import com.project.gryllo.entity.Member;
import com.project.gryllo.repository.MemberRepository;
import com.project.gryllo.service.auth.AuthClient;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class MemberServiceTest {

	private final MemberRegister memberRegister = MemberRegister.builder()
		.email("test@test.com")
		.userName("테스트")
		.nickName("test")
		.password("Test1234!")
		.build();

	private final Member member = Member.builder()
		.email("test@test.com")
		.userName("테스트")
		.nickName("test")
		.password("Test1234!")
		.build();

	private AuthMemberInfo memberInfo = AuthMemberInfo.builder()
		.email(member.getEmail())
		.login(member.getNickName())
		.name(member.getUserName())
		.build();

	private final AuthMember authMember = new AuthMember("test@test.com", "Test1234!");

	private final EditMember editMember = EditMember.builder()
		.userName("test1")
		.file(Optional.empty())
		.bio("tsetes")
		.nickName("test2")
		.password("test2")
		.build();

	private final LoggedInMember loggedInMember = new LoggedInMember(1L, "test@test.com", "test",
		"테스트");

	private final String code = "af70401e9c65f83fe29e";
	private final String sampleToken = "mq4m38cvt3ucw498ub2er";
	private final String email = "test@test.com";


	@InjectMocks
	private MemberService memberService;

	@Mock
	MemberRepository memberRepository;

	@Mock
	private AuthClient authClient;

	@Test
	void resister_Success() {
		given(memberRepository.save(member)).willReturn(member);

		memberService.save(memberRegister);

		verify(memberRepository, times(1)).save(any());
	}

	@Test
	void resister_Fail_Existing_nickName() {
		given(memberRepository.existsByNickName(any())).willReturn(true);

		assertThrows(RuntimeException.class, () -> {
			memberService.save(memberRegister);
		});
	}

	@Test
	void resister_Fail_Existing_email() {
		given(memberRepository.existsByNickName(anyString())).willReturn(false);
		given(memberRepository.existsByEmail(anyString())).willReturn(true);

		assertThrows(RuntimeException.class, () -> memberService.save(memberRegister));
	}

	@Test
	void login_Success() {
		given(memberRepository.findByEmail(any())).willReturn(Optional.of(member));

		memberService.login(authMember);

		verify(memberRepository, times(1)).findByEmail(any());
	}

	@Test
	void login_fail_not_valid_email() {
		given(memberRepository.findByEmail(any())).willReturn(Optional.empty());

		assertThrows(RuntimeException.class, () -> memberService.login(authMember));
	}

	@Test
	void login_fail_not_valid_password() {
		given(memberRepository.findByEmail(any())).willReturn(Optional.of(member));

		assertThrows(RuntimeException.class,
			() -> memberService.login(new AuthMember("test@test.com", "exception")));
	}

	@Test
	void edit_member_Success() {
		given(memberRepository.findById(1L)).willReturn(Optional.of(member));

		MemberDto memberDto = new MemberDto(1L, "test@test.com", "test", "test1234!", "testbio");
		LoggedInMember loggedInMember = new LoggedInMember(1L, "test@test.com", "tes1t", "테스트");
		assertDoesNotThrow(() -> memberService.update(1L, editMember, loggedInMember));
	}

	@Test
	void edit_member_Fail_Not_Valid_Member() {
		given(memberRepository.findById(anyLong())).willReturn(Optional.empty());

		assertThrows(RuntimeException.class,
			() -> memberService.update(anyLong(), editMember, loggedInMember));
	}

	@Test
	void edit_other_member() {
		given(memberRepository.findById(anyLong())).willReturn(Optional.of(member));
		given(memberRepository.existsByNickName(anyString())).willReturn(false);
		assertThrows(RuntimeException.class, () -> memberService.update(anyLong(), editMember,
			new LoggedInMember(1L, "test5@gmail", "test5", "test5")));
	}

	@Test
	void edit_member_Fail_Existing_NickName() {
		given(memberRepository.findById(any())).willReturn(Optional.of(member));
		given(memberRepository.existsByNickName(any())).willReturn(true);

		assertThrows(RuntimeException.class,
			() -> memberService.update(anyLong(), editMember, loggedInMember));
	}

	/**
	 * TO-DO
	 * GitHub 인증 확인
	 */
//	@Test
//	void oauth_firstLogin() {
//		given(authClient.getToken(code)).willReturn(sampleToken);
//		given(authClient.getUserEmail(sampleToken)).willReturn(email);
//
//		given(memberRepository.findByEmail(email)).willReturn(Optional.empty());
//
//		given(memberRepository.save(member)).willReturn(member);
//		given(authClient.getUserInformation(sampleToken)).willReturn(memberInfo);
//
//		LoggedInMember loggedIn = memberService.auth(code);
//
//		verify(memberRepository).save(any());
//		assertThat(loggedIn.getEmail()).isEqualTo(member.getEmail());
//	}

	@Test
	void oauth_login() {
		given(authClient.getToken(code)).willReturn(sampleToken);
		given(authClient.getUserEmail(sampleToken)).willReturn(email);

		given(memberRepository.findByEmail(email)).willReturn(Optional.of(member));

		LoggedInMember loggedIn = memberService.auth(code);

		assertThat(loggedIn.getEmail()).isEqualTo(member.getEmail());
	}
}
