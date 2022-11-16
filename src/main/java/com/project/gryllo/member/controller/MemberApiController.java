package com.project.gryllo.member.controller;

import com.project.gryllo.member.controller.dto.MemberResponse;
import com.project.gryllo.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;
    
    @GetMapping("/members")
    public MemberResponse searchMembers(
            @RequestParam(defaultValue = "") String nickname, @PageableDefault(size = 50) Pageable pageable) {
        return new MemberResponse(memberService.searchProfiles(nickname, pageable));
    }
}
