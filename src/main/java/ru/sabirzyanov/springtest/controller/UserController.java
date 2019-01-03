package ru.sabirzyanov.springtest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.sabirzyanov.springtest.domain.Role;
import ru.sabirzyanov.springtest.domain.User;
import ru.sabirzyanov.springtest.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;



@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public String userList(@RequestParam(required = false, defaultValue = "") String username,
                                                                              Model model,
                           @PageableDefault(sort = { "id" }, direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<User> page;
        if (username != null && !username.isEmpty()) {
            List<User> userList = new ArrayList<>();
            if (userService.findUser(username) != null ) {
                userList.add(userService.findUser(username));
                model.addAttribute("usersList", userList);
            }
            else {
                page = userService.findAll(pageable);
                model.addAttribute("errorMessage", "User not found");
                model.addAttribute("page", page);
            }
        } else {
            page = userService.findAll(pageable);
            model.addAttribute("page", page);
        }

        model.addAttribute("username", username);
        model.addAttribute("url", "/user");

        return "user";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("{user}")
    public String userEditForm(@PathVariable User user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());

        return "userEdit";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("{user}")
    public String userSave(
            @AuthenticationPrincipal User admin,
            Model model,
            @RequestParam String username,
            @RequestParam String email,
            @RequestParam Long score,
            @RequestParam Map<String, String> form,
            @PathVariable @RequestParam("userId") User user
    ) {

        if(!userService.saveUser(user, username, email, score, admin, form)) {
            //TODO вывод ошибки о том, что такой юзер существует
            model.addAttribute("messageType", "danger");
            model.addAttribute("message", "A user with same username already exist");
        }
        return "redirect:/user";
    }

    @GetMapping("profile")
    public String getProfile(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("username", user.getUsername());
        model.addAttribute("email", user.getEmail());

        return "profile";
    }

    @PostMapping("profile")
    public String updateProfile(
            @AuthenticationPrincipal User user,
            @RequestParam String password,
            @RequestParam String email
    ) {
        userService.updateProfile(user, password, email);

        return "redirect:/user/profile";
    }
}
