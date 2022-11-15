package com.project.gryllo.service;

import com.project.gryllo.dto.auth.AuthMemberInfo;
import com.project.gryllo.dto.member.AuthMember;
import com.project.gryllo.dto.member.EditMember;
import com.project.gryllo.dto.member.LoggedInMember;
import com.project.gryllo.dto.member.MemberDto;
import com.project.gryllo.dto.member.MemberRegister;
import com.project.gryllo.entity.FileInfo;
import com.project.gryllo.entity.Member;
import com.project.gryllo.repository.MemberRepository;
import com.project.gryllo.service.auth.AuthClient;
import com.project.gryllo.service.rule.ImageFileNaming;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
public class MemberService {
	private final MemberRepository memberRepository;
	private final FileService fileService;
	private final AuthClient authClient;

	public MemberService(MemberRepository memberRepository, FileService fileService, AuthClient authClient) {
		this.memberRepository = memberRepository;
		this.fileService = fileService;
		this.authClient = authClient;
	}

	public void save(MemberRegister memberRegister) {
		checkDuplicatedAttributes(memberRegister.getNickName(), memberRegister.getEmail());
		memberRepository.save(Member.builder()
			.email(memberRegister.getEmail())
			.nickName(memberRegister.getNickName())
			.password(memberRegister.getPassword())
			.userName(memberRegister.getUserName())
			.bio("")
			.build());
	}

	private void checkDuplicatedAttributes(String nickName, String email) {
		if (memberRepository.existsByNickName(nickName)) {
			throw new RuntimeException("이미 사용중인 닉네임입니다.");
		}
		if (memberRepository.existsByEmail(email)) {
			throw new RuntimeException("이미 사용중인 이메일입니다.");
		}
	}

	@Transactional(readOnly = true)
	public List<Member> findAll() {
		return memberRepository.findAll();
	}

	@Transactional(readOnly = true)
	public MemberDto findUserInfoById(long userId, LoggedInMember loggedInMember) {
		Member member = findUserById(userId);
		member.checkEmail(loggedInMember.getEmail());
		return MemberDto.builder()
			.id(member.getId())
			.nickName(member.getNickName())
			.userName(member.getUserName())
			.password(member.getPassword())
			.bio(member.getBio())
			.build();
	}

	@Transactional(readOnly = true)
	public Member findUserById(long userId) {
		return memberRepository.findById(userId)
			.orElseThrow(RuntimeException::new);
	}

	public LoggedInMember update(long userId, EditMember editMember, LoggedInMember loggedInMember) {
		Member member = findUserById(userId);
		checkDuplicatedNickName(editMember, member);
		Optional<MultipartFile> maybeFile = editMember.getFile();
		FileInfo fileInfo = member.getFileInfo();
		if (maybeFile.isPresent()) {
			fileInfo = fileService.save(maybeFile.get(), new ImageFileNaming());
		}
		member.update(member.builder()
			.nickName(editMember.getNickName())
			.userName(editMember.getUserName())
			.password(editMember.getPassword())
			.bio(editMember.getBio())
			.fileInfo(fileInfo)
			.build()
			, loggedInMember.getEmail());

		return loggedInMember.builder()
			.id(member.getId())
			.nickName(member.getNickName())
			.userName(member.getUserName())
			.email(member.getEmail())
			.build();
	}

	private void checkDuplicatedNickName(EditMember editMember, Member member) {
		if (!member.equalsNickName(editMember.getNickName()) &&
			memberRepository.existsByNickName(editMember.getNickName())) {
			throw new RuntimeException("이미 사용중인 닉네임입니다.");
		}
	}

	public LoggedInMember login(AuthMember authMember) {
		Member member = findByEmail(authMember.getEmail())
			.orElseThrow(() -> new RuntimeException("회원정보가 일치하지 않습니다."));
		member.checkPassword(authMember.getPassword());
		return LoggedInMember.builder()
			.id(member.getId())
			.nickName(member.getNickName())
			.userName(member.getUserName())
			.email(member.getEmail())
			.build();
	}

	private Optional<Member> findByEmail(String email) {
		return memberRepository.findByEmail(email);
	}

	public LoggedInMember auth(String code) {
		String accessToken = authClient.getToken(code);
		String email = authClient.getUserEmail(accessToken);

		return findByEmail(email).map(maybeUser -> {
			maybeUser.changeToOAuthUser();
			return LoggedInMember.builder()
				.id(maybeUser.getId())
				.nickName(maybeUser.getNickName())
				.userName(maybeUser.getUserName())
				.email(maybeUser.getEmail())
				.build();
		}).orElseGet(() -> LoggedInMember.builder()
			.id(saveAuthUser(accessToken, email).getId())
			.nickName(saveAuthUser(accessToken, email).getNickName())
			.userName(saveAuthUser(accessToken, email).getUserName())
			.email(saveAuthUser(accessToken, email).getEmail())
			.build());
	}

	private Member saveAuthUser(String accessToken, String email) {
		AuthMemberInfo memberInfo = authClient.getUserInformation(accessToken);
		return memberRepository.save(Member.builder()
			.email(email)
			.nickName(UUID.randomUUID().toString().substring(10))
			.password(String.valueOf(email.hashCode()))
			.userName(Optional.ofNullable(memberInfo.getName()).orElse("이름을 바꿔주세요."))
			.isOauthUser(true)
			.build());
	}

	public void deleteUserById(long id, LoggedInMember loggedInMember) {
		Member member = findByEmail(loggedInMember.getEmail())
			.orElseThrow(RuntimeException::new);
		if (member.isNotSameUser(id)) {
			throw new RuntimeException();
		}
		memberRepository.deleteById(id);
	}

	@Transactional(readOnly = true)
	public byte[] findProfileImageById(long userId) {
		Member member = findUserById(userId);
		FileInfo fileInfo = member.getFileInfo();
		return fileService.readFileByFileInfo(fileInfo);
	}

	@Transactional(readOnly = true)
	public Member findByNickName(String nickName) {
		return memberRepository.findByNickName(nickName).orElseThrow(RuntimeException::new);
	}
}
