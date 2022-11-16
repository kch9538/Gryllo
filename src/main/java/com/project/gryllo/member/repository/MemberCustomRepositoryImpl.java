package com.project.gryllo.member.repository;


import static com.project.gryllo.member.entity.QMember.member;
import static com.project.gryllo.member.entity.QMemberImage.memberImage;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
public class MemberCustomRepositoryImpl implements MemberCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<MemberProfileDto> searchProfiles(String nickname, Pageable pageable) {
        return queryFactory
                .select(new QMemberProfileDto(
                        member.id,
                        member.name,
                        member.nickname,
                        memberImage.storeFileName.concat(".").concat(memberImage.extension)
                ))
                .from(member)
                .where(member.nickname.contains(nickname))
                .leftJoin(member.memberImage, memberImage)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }
}
