package com.project.gryllo.member.controller.dto;

import com.project.gryllo.member.repository.MemberProfileDto;
import java.util.List;
import lombok.Getter;

@Getter
public class MemberResponse {

    private List<MemberProfileDto> members;
    private int count;

    public MemberResponse(List<MemberProfileDto> members) {
        this.members = members;
        this.count = members.size();
    }
}
