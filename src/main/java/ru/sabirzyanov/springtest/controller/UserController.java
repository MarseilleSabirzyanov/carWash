package ru.sabirzyanov.springtest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import ru.sabirzyanov.springtest.domain.History;
import ru.sabirzyanov.springtest.domain.Role;
import ru.sabirzyanov.springtest.domain.User;
import ru.sabirzyanov.springtest.service.HistoryService;
import ru.sabirzyanov.springtest.service.UserService;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;


@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private HistoryService historyService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public String userList(@RequestParam(required = false, defaultValue = "") String username,
                                                                              Model model,
                           @PageableDefault(sort = { "id" }, direction = Sort.Direction.DESC) Pageable pageable
    ) {

        model.addAttribute("title", "Список пользователей");
        userService.userListCreator(model, pageable, username);

        return "user";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("{username}")
    public String userEditForm(/*@PathVariable User user,*/ Model model, @PathVariable String username) {
        User user = userService.findUser(username);

        model.addAttribute("title", "Profile");
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        model.addAttribute("oldUsername", user.getUsername());
        model.addAttribute("oldName", user.getName());
        model.addAttribute("oldSurname", user.getSurname());
        model.addAttribute("oldPhone", user.getPhone());
        model.addAttribute("oldEmail", user.getEmail());
        model.addAttribute("oldActivatedPoints", 0);

        return "userEdit";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public String addPoints(@AuthenticationPrincipal User admin,
                            Model model,
                            @RequestParam String usernamePost,
                            @RequestParam Long discount,
                            @RequestParam Long points,
                            @PageableDefault(sort = { "id" }, direction = Sort.Direction.DESC) Pageable pageable
                            ) {

        userService.userListCreator(model, pageable, usernamePost);
        model.addAttribute("pointErrorUsername", usernamePost);

        if (points < 0 && (discount < 0 || discount > 100)) {
            model.addAttribute("pointsError", "Баллы не могут быть отрицательными!");
            model.addAttribute("discountError", "от 0 до 100");
        } else if (points < 0){
            model.addAttribute("pointsError", "Баллы не могут быть отрицательными!");
        } else if (discount < 0 || discount > 100) {
            model.addAttribute("discountError", "от 0 до 100");
        } else {
            userService.addPoints(usernamePost, discount, points, admin);
            return "redirect:user";
        }

        /*List<User> userList = new ArrayList<>();
        userList.add(userService.findUser(usernamePost));
        model.addAttribute("usersList", userList);*/

        return "user";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping(value = "{username}", params = "save")
    public String userSave(
            @AuthenticationPrincipal User admin,
            Model model,
            @RequestParam String name,
            @RequestParam String surname,
            @RequestParam String phone,
            @RequestParam String email,
            @RequestParam Map<String, String> form,
            @RequestParam Long activatedPoints,
            @PathVariable String username
            //@RequestParam("userId") User user
    ) {
        User user = userService.findUser(username);

        model.addAttribute("title", "profile");
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        model.addAttribute("oldName", name);
        model.addAttribute("oldSurname", surname);
        model.addAttribute("oldPhone", phone);
        model.addAttribute("oldEmail", email);
        model.addAttribute("oldActivatedPoints", activatedPoints);

        if (userService.findUserByEmail(email) == null || user.getEmail().equals(email)) {
                userService.saveUser(user, email, form, model, name, surname, phone);
        }
        else {
            model.addAttribute("emailError", "Пользователь с таким же emailом уже существует!");
            return "userEdit";
        }

        return "userEdit";
    }

    /*
    *  500 баллов = 1 услуга
    * */
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping(value = "{username}", params = "activatePoints")
    public String activatePoints(
            @AuthenticationPrincipal User admin,
            @PathVariable String username,
            //@RequestParam("userId") User user,
            @RequestParam Long activatedPoints,
            Model model
    ) {
        User user = userService.findUser(username);

        model.addAttribute("title", "Profile");
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        model.addAttribute("oldName", user.getName());
        model.addAttribute("oldSurname", user.getSurname());
        model.addAttribute("oldPhone", user.getPhone());
        model.addAttribute("oldEmail", user.getEmail());
        model.addAttribute("oldActivatedPoints", activatedPoints);

        if (activatedPoints == 0) {
            return "redirect:/user/" + user.getUsername();
        }
        userService.activatePoints(user, admin, activatedPoints, model);
        return "userEdit";
        //return "redirect:/user/" + user.getId();
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) throws Exception {
        final DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        final CustomDateEditor dateEditor = new CustomDateEditor(df, true) {
            @Override
            public void setAsText(String text) throws IllegalArgumentException {
                if ("today".equals(text)) {
                    setValue(new Date());
                } else {
                    super.setAsText(text);
                }
            }
        };
        binder.registerCustomEditor(Date.class, dateEditor);
    }

    @GetMapping("profile")
    public String getProfile(
            Model model,
            @AuthenticationPrincipal User user,
            @RequestParam(required = false, defaultValue = "2000-01-01") @DateTimeFormat(pattern="yyyy-MM-dd") Date dateFrom,
            @RequestParam(required = false, defaultValue = "today") @DateTimeFormat(pattern="yyyy-MM-dd") Date dateTo,
            Pageable pageable
    ) {
        Page<History> page;
        page = historyService.findUserDate(user.getId(), dateFrom, dateTo, pageable);

        model.addAttribute("title", "Profile");
        model.addAttribute("oldName", user.getName());
        model.addAttribute("oldSurname", user.getSurname());
        model.addAttribute("oldPhone", user.getPhone());
        model.addAttribute("username", user.getUsername());
        model.addAttribute("email", user.getEmail());
        model.addAttribute("score", user.getScore());
        model.addAttribute("page", page);

        return "profile";
    }

    @PostMapping("profile")
    public String updateProfile(
            @AuthenticationPrincipal User user,
            Model model,
            @RequestParam("password2") String passwordConfirmation,
            @RequestParam String password,
            @RequestParam String name,
            @RequestParam String surname,
            @RequestParam String phone,
            @RequestParam String email,
            @RequestParam(required = false, defaultValue = "2000-01-01") @DateTimeFormat(pattern="yyyy-MM-dd") Date dateFrom,
            @RequestParam(required = false, defaultValue = "today") @DateTimeFormat(pattern="yyyy-MM-dd") Date dateTo,
            Pageable pageable
    ) {
        Page<History> page;
        page = historyService.findUserDate(user.getId(), dateFrom, dateTo, pageable);

        model.addAttribute("title", "Profile");
        model.addAttribute("oldName", name);
        model.addAttribute("oldSurname", surname);
        model.addAttribute("oldPhone", phone);
        model.addAttribute("username", user.getUsername());
        model.addAttribute("email", user.getEmail());
        model.addAttribute("score", user.getScore());
        model.addAttribute("page", page);

        if (userService.findUserByEmail(email) != null && !email.equals(user.getEmail())) {
            model.addAttribute("email", email);
            model.addAttribute("emailError", "Такой email уже существует");
            return "profile";
        }
        if (StringUtils.isEmpty(password) && StringUtils.isEmpty(passwordConfirmation))
            userService.updateProfile(user, "", email, model, name, surname, phone);
        else if (!StringUtils.isEmpty(password)) {
            boolean isConfirmEmpty = StringUtils.isEmpty(passwordConfirmation);
            if (isConfirmEmpty) {
                model.addAttribute("password2Error", "Подтверждение пароля не может быть пустым");
                return "profile";
            }

            if (!password.equals(passwordConfirmation)) {
                model.addAttribute("passwordError", "Пароли не совпадают");
                return "profile";
            }
            userService.updateProfile(user, password, email, model, name, surname, phone);
        }

        return "profile";
    }
}
