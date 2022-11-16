package com.project.gryllo.common.auth;

import com.project.gryllo.member.entity.Member;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SignInMember {

    private Long memberId;
    private String name;
    private String nickname;
    private String OriginalStoreFileName;

    public static SignInMember from(Member member) {
        return new SignInMember(
                member.getId(),
                member.getName(),
                member.getNickname(),
                member.getMemberImage().getOriginalStoreFileName());
    }
}
