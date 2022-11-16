package com.project.gryllo.member.repository;

import com.project.gryllo.member.entity.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long>, MemberCustomRepository {

    Optional<Member> findByNickname(String nickname);
    Optional<Member> findByPrivacyEmail(String email);
}
