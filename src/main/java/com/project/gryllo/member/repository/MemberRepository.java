package com.project.gryllo.member.repository;

import com.project.gryllo.member.domain.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, String> {


	Optional<Member> findByEmailAuthKey(String emailAuthKey);
}
