package com.project.gryllo.member.service;


import static com.project.gryllo.common.exception.form.FormExceptionType.DUPLICATE_EMAIL;
import static com.project.gryllo.common.exception.form.FormExceptionType.DUPLICATE_NICKNAME;
import static com.project.gryllo.common.exception.form.FormExceptionType.INCORRECT_PASSWORD;
import static com.project.gryllo.common.exception.form.FormExceptionType.NOT_FOUND_ACCOUNT_BY_Email;
import static com.project.gryllo.common.exception.form.FormExceptionType.NOT_FOUND_ACCOUNT_BY_NICKNAME;

import com.project.gryllo.member.entity.Member;
import com.project.gryllo.member.entity.Privacy;
import com.project.gryllo.member.repository.MemberProfileDto;
import com.project.gryllo.member.repository.MemberRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public Long signUp(MemberDto memberDto) {
        validateDuplication(memberDto.getNickname(), memberDto.getEmail());

        Member member = new Member(
                Privacy.create(memberDto.getPassword(), memberDto.getEmail()),
                memberDto.getNickname(),
                memberDto.getName());

        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplication(String nickname, String email) {
        memberRepository.findByNickname(nickname).ifPresent(member1 -> {
            throw DUPLICATE_NICKNAME.getException();
        });

        if (Privacy.isEmail(email) &&
                memberRepository.findByPrivacyEmail(email).isPresent()) {
            throw DUPLICATE_EMAIL.getException();
        }
    }

    public Member signIn(String signInId, String password) {
        Member findMember = findBySignIdOrThrow(signInId);
        validatePassword(password, findMember);

        return findMember;
    }

    private Member findBySignIdOrThrow(String signId) {
        if (Privacy.isEmail(signId)) {
            return memberRepository.findByPrivacyEmail(signId)
                    .orElseThrow(NOT_FOUND_ACCOUNT_BY_Email::getException);
        } else {
            return memberRepository.findByNickname(signId)
                    .orElseThrow(NOT_FOUND_ACCOUNT_BY_NICKNAME::getException);
        }
    }

    private void validatePassword(String password, Member findMember) {
        if (!findMember.isCorrectPassword(password)) {
            throw INCORRECT_PASSWORD.getException();
        }
    }

    public List<MemberProfileDto> searchProfiles(String nickname, Pageable pageable) {
        return memberRepository.searchProfiles(nickname, pageable);
    }
}
