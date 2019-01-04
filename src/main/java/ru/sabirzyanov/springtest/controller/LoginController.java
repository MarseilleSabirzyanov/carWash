package ru.sabirzyanov.springtest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.sabirzyanov.springtest.domain.User;
import ru.sabirzyanov.springtest.repos.UserRepository;

import java.util.Map;


//TODO вывод сообщения о том, что email не активирован
@Controller
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/login")
    public String login() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (!(auth instanceof AnonymousAuthenticationToken)) {

            /* The user is logged in :) */
            return "user/profile";
        }

        return "login";
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam String username,
                            BindingResult bindingResult,
                            Model model) {

        User user = userRepository.findByUsername(username);
        if (user.getActivationCode() != null) {
            model.addAttribute("errorMessage", "Email not activated");
            return "login";
        }

        if (bindingResult.hasErrors()) {
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);

            model.mergeAttributes(errors);

            return "login";
        }

        return "redirect:/main";
    }

}
