package com.project.gryllo.member.controller;

import com.project.gryllo.common.auth.SessionConst;
import com.project.gryllo.common.auth.SignInMember;
import com.project.gryllo.common.exception.form.CustomFormException;
import com.project.gryllo.member.controller.dto.MemberSaveForm;
import com.project.gryllo.member.controller.dto.SignInForm;
import com.project.gryllo.member.entity.Member;
import com.project.gryllo.member.service.MemberDto;
import com.project.gryllo.member.service.MemberService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/signup")
    public String signUpPage(MemberSaveForm form) {
        return "signUp";
    }

    @PostMapping("/signup")
    public String signUp(@Valid MemberSaveForm form, BindingResult bindingResult) {
        try {
            if (!bindingResult.hasFieldErrors()) {
                memberService.signUp(new MemberDto(
                        form.getEmail(),
                        form.getName(),
                        form.getNickname(),
                        form.getPassword()));
            }
        } catch (CustomFormException e) {
            bindingResult.rejectValue(e.getField(), e.getErrorCode(), e.getMessage());
        }

        if (bindingResult.hasErrors()) {
            return "signUp";
        }

        return "redirect:/members/signin";
    }

    @GetMapping("/signin")
    public String signInPage(SignInForm form) {
        return "signIn";
    }

    @PostMapping("/signin")
    public String signIn(@Valid SignInForm form, BindingResult bindingResult, HttpServletRequest request) {

        Member findMember = null;
        try {
            if (!bindingResult.hasFieldErrors()) {
                findMember = memberService.signIn(form.getSignInId(), form.getPassword());
            }
        } catch (CustomFormException e) {
            bindingResult.rejectValue(e.getField(), e.getErrorCode(), e.getMessage());
        }

        if (bindingResult.hasErrors()) {
            return "signIn";
        }

        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.SIGN_IN_MEMBER, SignInMember.from(findMember));

        return "redirect:/";
    }
}
