package com.CPAN_228_Group10.CPAN_228_FinalProject.humber.warehouse.model.controller;

import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;

public class LogoutController {
    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
