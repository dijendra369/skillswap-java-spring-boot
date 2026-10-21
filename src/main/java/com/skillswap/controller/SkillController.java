package com.skillswap.controller;

import com.skillswap.model.Skill;
import com.skillswap.model.User;
import com.skillswap.repository.SkillRepository;
import com.skillswap.repository.UserRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@Validated
public class SkillController {
    private final SkillRepository skills;
    private final UserRepository users;

    public SkillController(SkillRepository skills, UserRepository users) {
        this.skills = skills; this.users = users;
    }

    private User currentUser(Authentication auth) {
        return users.findByEmailIgnoreCase(auth.getName()).orElseThrow();
    }

    @GetMapping("/skills")
    public String browse(@RequestParam(defaultValue="") String q, Authentication auth, Model model) {
        User me = currentUser(auth);
        var listings = q.isBlank()
            ? skills.findByTypeAndOwner_IdNotOrderByCreatedAtDesc(Skill.SkillType.TEACH, me.getId())
            : skills.findByTypeAndOwner_IdNotAndTitleContainingIgnoreCaseOrderByCreatedAtDesc(
                Skill.SkillType.TEACH, me.getId(), q.trim());
        model.addAttribute("listings", listings);
        model.addAttribute("q", q);
        return "skills";
    }

    @GetMapping("/skills/new")
    public String newSkill(Model model) {
        model.addAttribute("skillTypes", Skill.SkillType.values());
        return "skill-form";
    }

    @PostMapping("/skills")
    public String create(@RequestParam @NotBlank @Size(max=80) String title,
                         @RequestParam @NotBlank @Size(max=30) String category,
                         @RequestParam(defaultValue="") @Size(max=700) String description,
                         @RequestParam Skill.SkillType type,
                         Authentication auth, Model model) {
        User me = currentUser(auth);
        skills.save(new Skill(title, category, description, type, me));
        return "redirect:/dashboard";
    }

    @PostMapping("/skills/{id}/delete")
    public String delete(@PathVariable Long id, Authentication auth) {
        User me = currentUser(auth);
        Skill skill = skills.findById(id).orElseThrow();
        if (!skill.getOwner().getId().equals(me.getId())) {
            throw new org.springframework.web.server.ResponseStatusException(
                org.springframework.http.HttpStatus.FORBIDDEN);
        }
        skills.delete(skill);
        return "redirect:/dashboard";
    }
}
