package com.skillswap.controller;

import com.skillswap.service.RegistrationService;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@Validated
public class AuthController {
    private final RegistrationService registration;

    public AuthController(RegistrationService registration) { this.registration = registration; }

    @GetMapping("/")
    public String home() { return "index"; }

    @GetMapping("/login")
    public String login() { return "login"; }

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("name", "");
        model.addAttribute("email", "");
        return "register";
    }

    @PostMapping("/register")
    public String register(@RequestParam @NotBlank @Size(max=60) String name,
                           @RequestParam @Email @NotBlank @Size(max=120) String email,
                           @RequestParam @Size(min=8, max=72) String password,
                           Model model) {
        try {
            registration.register(name, email, password);
            model.addAttribute("registered", true);
            return "login";
        } catch (IllegalArgumentException ex) {
            model.addAttribute("error", ex.getMessage());
            model.addAttribute("name", name);
            model.addAttribute("email", email);
            return "register";
        }
    }
}
