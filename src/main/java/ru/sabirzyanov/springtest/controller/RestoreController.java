package ru.sabirzyanov.springtest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.sabirzyanov.springtest.service.UserService;

@Controller
public class RestoreController {
    @Autowired
    private UserService userService;

    @GetMapping("/restore_account")
    public String restore() {
        Authentication auth = SecurityContextHolder
                .getContext()
                .getAuthentication();

        if (!(auth instanceof AnonymousAuthenticationToken)) {
            return "redirect:user/profile";
        }
        return "restoreAccount";
    }

    @PostMapping("/restore_account")
    public String restoreAccount(@RequestParam String email, Model model) {
        model.addAttribute("oldEmail", email);
        userService.restoreAccount(email, model);
        return "restoreAccount";
    }
}
