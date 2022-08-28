package com.shop.controller;

import com.shop.dto.MemberFormDto;
import com.shop.repository.MemberRepository;
import com.shop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(value = "/member")
@Controller
@RequiredArgsConstructor
public class MemberController {

    private MemberService memberService;
    private MemberRepository memberRepository;

    @GetMapping(value = "/new")
    public String memberForm(Model model){
        model.addAttribute("memberFormDto", new MemberFormDto());
        return "member/memberForm";
    }
}
