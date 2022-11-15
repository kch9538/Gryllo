package com.project.gryllo.repository;

import com.project.gryllo.entity.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

	Optional<Member> findByEmail(String email);

	Optional<Member> findByNickName(String nickName);

	boolean existsByNickName(String nickName);

	boolean existsByEmail(String email);

}
