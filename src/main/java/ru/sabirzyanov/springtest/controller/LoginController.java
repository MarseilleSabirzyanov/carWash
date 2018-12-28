package ru.sabirzyanov.springtest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
        return "login";
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam String username,
                            BindingResult bindingResult,
                            Model model) {

        User user = userRepository.findByUsername(username);
        if (user.getActivationCode() != null) {
            model.addAttribute("usernameError", "Email not activated");
        }

        if (bindingResult.hasErrors()) {
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);

            model.mergeAttributes(errors);

            return "login";
        }

        return "redirect:/main";
    }

}
