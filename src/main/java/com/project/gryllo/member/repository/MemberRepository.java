package com.project.gryllo.member.repository;

import com.project.gryllo.member.domain.Member;
import java.util.List;
import java.util.Optional;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, String> {


	Optional<Member> findByEmailAuthKey(String emailAuthKey);
}
