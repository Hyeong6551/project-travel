package com.example.gatewayservice.controller;

import com.example.gatewayservice.entity.Member;
import com.example.gatewayservice.service.MemberService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/mypage")
public class MypageViewController {

    @Autowired
    private MemberService memberService;

    @GetMapping({"", "/"})
    public String mypage() {
        return "/mypage/mypage";
    }

    @GetMapping("/myinfo")
    public String myinfo(HttpSession session, Model model) {
        session.setAttribute("no", 1);
        Long no = (Long) session.getAttribute("no");
        Member member = memberService.getUserByNo(no);
        model.addAttribute("user", member);
        return "/mypage/myinfo";
    }
}
