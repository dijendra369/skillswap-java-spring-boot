package com.skillswap.controller;

import com.skillswap.repository.ExchangeRequestRepository;
import com.skillswap.repository.SkillRepository;
import com.skillswap.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {
    private final UserRepository users;
    private final SkillRepository skills;
    private final ExchangeRequestRepository requests;

    public DashboardController(UserRepository users, SkillRepository skills, ExchangeRequestRepository requests) {
        this.users = users; this.skills = skills; this.requests = requests;
    }

    @GetMapping("/dashboard")
    public String dashboard(Authentication auth, Model model) {
        var user = users.findByEmailIgnoreCase(auth.getName()).orElseThrow();
        model.addAttribute("user", user);
        model.addAttribute("mySkills", skills.findByOwnerOrderByCreatedAtDesc(user));
        model.addAttribute("incoming", requests.findByReceiverOrderByCreatedAtDesc(user));
        model.addAttribute("outgoing", requests.findBySenderOrderByCreatedAtDesc(user));
        return "dashboard";
    }
}
