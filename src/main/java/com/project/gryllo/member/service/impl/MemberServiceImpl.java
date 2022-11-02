package com.project.gryllo.member.service.impl;

import com.project.gryllo.components.MailComponents;
import com.project.gryllo.member.domain.Member;
import com.project.gryllo.member.exception.MemberNotEmailAuthException;
import com.project.gryllo.member.exception.MemberStopUserException;
import com.project.gryllo.member.model.MemberInput;
import com.project.gryllo.member.repository.MemberRepository;
import com.project.gryllo.member.service.MemberService;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {

	private final MemberRepository memberRepository;
	private final MailComponents mailComponents;

	@Override
	public boolean register(MemberInput parameter) {

		Optional<Member> optionalMember = memberRepository.findById(parameter.getUserId());
		if (optionalMember.isPresent()) {
			return false;
		}
		String encPassword = BCrypt.hashpw(parameter.getPassword(), BCrypt.gensalt());
		String uuid = UUID.randomUUID().toString();

		Member member = Member.builder()
			.userId(parameter.getUserId())
			.nickName(parameter.getNickName())
			.password(encPassword)
			.regDt(LocalDateTime.now())
			.emailAuthYn(false)
			.emailAuthKey(uuid)
			.build();
		memberRepository.save(member);

		String email = parameter.getUserId();
		String subject = "Welcome to Gryllo";
		String text = "<p>Welcome to Gryllo.<p><p>아래 링크를 클릭하셔서 가입을 완료 하세요.</p>"
			+ "<div><a href ='http://localhost:8080/member/email-auth?id=" + uuid
			+ "> 인증하기 </a></div>";
		mailComponents.sendMail(email, subject, text);

		return true;
	}

	@Override
	public boolean emailAuth(String uuid) {
		Optional<Member> optionalMember = memberRepository.findByEmailAuthKey(uuid);
		if (!optionalMember.isPresent()) {
			return false;
		}

		Member member = optionalMember.get();

		if (member.isEmailAuthYn()) {
			return false;
		}

		member.setEmailAuthYn(true);
		member.setEmailAuthDt(LocalDateTime.now());
		memberRepository.save(member);

		return true;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Member> optionalMember = memberRepository.findById(username);
		if (!optionalMember.isPresent()) {
			throw new UsernameNotFoundException("회원 정보가 존재하지 않습니다.");
		}

		Member member = optionalMember.get();

		if (Member.MEMBER_STATUS_REQ.equals(member.getUserStatus())) {
			throw new MemberNotEmailAuthException("이메일 활성화 이후에 로그인을 해주세요.");
		}

		if (Member.MEMBER_STATUS_STOP.equals(member.getUserStatus())) {
			throw new MemberStopUserException("정지 회원 입니다.");
		}

		if (Member.MEMBER_STATUS_WITHDRAW.equals(member.getUserStatus())) {
			throw new MemberStopUserException("탈퇴 회원 입니다.");
		}

		List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
		grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));

		if (member.isAdminYn()) {
			grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		}

		return new User(member.getUserId(), member.getPassword(), grantedAuthorities);

	}
}
