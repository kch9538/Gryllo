package com.project.gryllo.member.repository;

import java.util.List;
import org.springframework.data.domain.Pageable;


public interface MemberCustomRepository {

    List<MemberProfileDto> searchProfiles(String nickname, Pageable pageable);
}
