package com.skillswap.controller;

import com.skillswap.model.ExchangeRequest;
import com.skillswap.model.Skill;
import com.skillswap.model.User;
import com.skillswap.repository.ExchangeRequestRepository;
import com.skillswap.repository.SkillRepository;
import com.skillswap.repository.UserRepository;
import jakarta.validation.constraints.Size;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

@Controller
public class ExchangeRequestController {
    private final ExchangeRequestRepository requests;
    private final SkillRepository skills;
    private final UserRepository users;

    public ExchangeRequestController(ExchangeRequestRepository requests, SkillRepository skills, UserRepository users) {
        this.requests = requests; this.skills = skills; this.users = users;
    }

    private User currentUser(Authentication auth) {
        return users.findByEmailIgnoreCase(auth.getName()).orElseThrow();
    }

    @PostMapping("/requests")
    public String send(@RequestParam Long skillId,
                       @RequestParam(defaultValue="") @Size(max=500) String message,
                       Authentication auth) {
        User sender = currentUser(auth);
        Skill skill = skills.findById(skillId).orElseThrow();
        if (skill.getType() != Skill.SkillType.TEACH || skill.getOwner().getId().equals(sender.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid skill exchange target.");
        }
        if (requests.existsBySenderAndSkillAndStatus(sender, skill, ExchangeRequest.Status.PENDING)) {
            return "redirect:/requests";
        }
        requests.save(new ExchangeRequest(sender, skill.getOwner(), skill, message));
        return "redirect:/requests";
    }

    @GetMapping("/requests")
    public String requests() { return "redirect:/dashboard#requests"; }

    @PostMapping("/requests/{id}/status")
    public String updateStatus(@PathVariable Long id, @RequestParam String status, Authentication auth) {
        User me = currentUser(auth);
        ExchangeRequest request = requests.findById(id).orElseThrow();
        if (!request.getReceiver().getId().equals(me.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        if (request.getStatus() != ExchangeRequest.Status.PENDING) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This request has already been handled.");
        }
        if (!status.equals("ACCEPTED") && !status.equals("DECLINED")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unsupported status.");
        }
        request.setStatus(ExchangeRequest.Status.valueOf(status));
        requests.save(request);
        return "redirect:/dashboard#requests";
    }
}
