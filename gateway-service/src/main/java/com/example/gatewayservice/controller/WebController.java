package com.example.gatewayservice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class WebController {

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("message", "MSA 프로젝트에 오신 것을 환영합니다");
        return "index";
    }

    @GetMapping("/signin")
    public String login(Model model) {
        model.addAttribute("message", "로그인");
        return "signin";
    }

    @GetMapping("/signup")
    public String register(Model model) {
        model.addAttribute("message", "회원 가입");
        return "signup";
    }


    @GetMapping("/admin")
    public String admin(Model model) {
        model.addAttribute("message", "관리자 페이지");
        return "admin";
    }

    @GetMapping("/admin/destination")
    public String destination(Model model) {
        model.addAttribute("message", "관리자 페이지");
        return "destination";
    }



    @GetMapping("/package")
    public String packageList() {
        return "packagelist"; // templates/packagelist.html
    }



    @GetMapping("/package/detail/{id}")
    public String packageDetailPage(@PathVariable Long id, Model model) {
        model.addAttribute("packageId", id);
        return "packagedetail"; // templates/packagedetail.html
    }


}