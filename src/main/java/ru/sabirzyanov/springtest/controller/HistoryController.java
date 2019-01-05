package ru.sabirzyanov.springtest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.sabirzyanov.springtest.domain.History;
import ru.sabirzyanov.springtest.service.HistoryService;
import ru.sabirzyanov.springtest.service.UserService;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping("/history")
@PreAuthorize("hasAuthority('ADMIN')")
public class HistoryController {

    @Autowired
    HistoryService historyService;

    @Autowired
    UserService userService;

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

    @GetMapping
    public String HistorySearch(
            @RequestParam(required = false, defaultValue = "") String username,
            @RequestParam(required = false, defaultValue = "2000-01-01") @DateTimeFormat(pattern="yyyy-MM-dd") Date dateFrom,
            @RequestParam(required = false, defaultValue = "today") @DateTimeFormat(pattern="yyyy-MM-dd") Date dateTo,
            @RequestParam(required = false, defaultValue = "") String admin,
            Model model,
            @PageableDefault(sort = { "id" }, direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<History> page;

        if(username != null && !username.isEmpty() && admin != null && !admin.isEmpty() && dateFrom != null && dateTo != null) {

            if (userService.findUser(username) == null && userService.findUser(admin) == null) {
                model.addAttribute("errorMessage", "User and admin not found");
                page = historyService.findDate(dateFrom, dateTo, pageable);
                model.addAttribute("page", page);
            }
            else if (userService.findUser(username) == null) {
                model.addAttribute("errorMessage", "User not found");
                page = historyService.findDate(dateFrom, dateTo, pageable);
                model.addAttribute("page", page);
            }
            else if (userService.findUser(admin) == null) {
                model.addAttribute("errorMessage", "Admin not found");
                page = historyService.findDate(dateFrom, dateTo, pageable);
                model.addAttribute("page", page);
            }
            else {
                page = historyService.findUserAdminDate(
                        userService.findUser(username).getId(),
                        userService.findUser(admin).getId(),
                        dateFrom, dateTo, pageable
                );
                model.addAttribute("page", page);
            }
        }
        else if (username != null && !username.isEmpty()) {
            if (userService.findUser(username) == null) {
                model.addAttribute("errorMessage", "User not found");
                page = historyService.findDate(dateFrom, dateTo, pageable);
                model.addAttribute("page", page);
            }
            else {
                page = historyService.findUserDate(userService.findUser(username).getId(), dateFrom, dateTo, pageable);
                model.addAttribute("page", page);
            }
        } else if (admin != null && !admin.isEmpty()) {
            if (userService.findUser(admin) == null) {
                model.addAttribute("errorMessage", "Admin not found");
                page = historyService.findDate(dateFrom, dateTo, pageable);
                model.addAttribute("page", page);
            } else {
                page = historyService.findAdminDate(userService.findUser(admin).getId(), dateFrom, dateTo, pageable);
                model.addAttribute("page", page);
            }
        }
            else {
                page = historyService.findDate(dateFrom, dateTo, pageable);
                model.addAttribute("page", page);
        }

        model.addAttribute("username", username);
        model.addAttribute("url", "/history");
        model.addAttribute("admin", admin);

        return "history";
    }

}
