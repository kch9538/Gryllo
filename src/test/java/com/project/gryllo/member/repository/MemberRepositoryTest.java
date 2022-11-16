package com.project.gryllo.member.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.project.gryllo.common.AppConfig;
import com.project.gryllo.member.entity.Member;
import com.project.gryllo.member.entity.Privacy;
import java.util.List;
import java.util.stream.IntStream;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;

@Import(value = AppConfig.class)
@DataJpaTest
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    EntityManager em;

    @BeforeEach
    void init() {
        IntStream.range(0, 51).forEach(i -> {
            Privacy privacy = Privacy.create("1234", i + "@naver.com");
            Member member = new Member(privacy, "nickname" + i, "이름");
            memberRepository.save(member);
        });
    }

    @Nested
    @DisplayName("닉네임으로 프로필 검색")
    class searchByNicknameTest {

        @DisplayName("성공")
        @Test
        public void success() {
            //given
            //when
            int pageSize = 50;
            List<MemberProfileDto> findProfiles =
                    memberRepository.searchProfiles("ick", PageRequest.of(0, pageSize));

            //then
            assertThat(findProfiles).hasSize(pageSize);
        }

        @DisplayName("결과 없음")
        @Test
        public void isEmpty() {
            //given
            //when
            int pageSize = 50;
            List<MemberProfileDto> findProfiles =
                    memberRepository.searchProfiles(" ", PageRequest.of(0, pageSize));

            //then
            assertThat(findProfiles.size()).isEqualTo(0);
        }
    }



    @Nested
    @DisplayName("이메일이 정확히 일치하는 회원 조회")
    class findByPrivacyEmailTest {

        @DisplayName("성공")
        @Test
        public void success() {
            //given
            //when
            String email = "0@naver.com";
            Member findMember = memberRepository.findByPrivacyEmail(email).orElse(null);

            //then
            assertThat(findMember.getPrivacy().getEmail()).isEqualTo(email);
        }

        @DisplayName("결과 없음")
        @Test
        public void isNull() {
            //given
            //when
            String email = "-1@naver.com";
            Member findMember = memberRepository.findByPrivacyEmail(email).orElse(null);

            //then
            assertThat(findMember).isNull();
        }
    }


    @Nested
    @DisplayName("닉네임이 정확히 일치하는 회원 조회")
    class findByNicknameTest {

        @DisplayName("성공")
        @Test
        public void success() {
            //given
            //when
            String nickname = "nickname0";
            Member findMember = memberRepository.findByNickname(nickname).orElse(null);

            //then
            assertThat(findMember.getNickname()).isEqualTo(nickname);
        }

        @DisplayName("결과 없음")
        @Test
        public void isNull() {
            //given
            //when
            String nickname = "nickname-1";
            Member findMember = memberRepository.findByNickname(nickname).orElse(null);

            //then
            assertThat(findMember).isNull();
        }
    }
}