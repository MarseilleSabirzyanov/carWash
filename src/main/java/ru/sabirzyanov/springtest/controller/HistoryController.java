package ru.sabirzyanov.springtest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
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
import ru.sabirzyanov.springtest.repos.HistoryRepository;
import ru.sabirzyanov.springtest.repos.UserRepository;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping("/history")
@PreAuthorize("hasAuthority('ADMIN')")
public class HistoryController {

    @Autowired
    HistoryRepository historyRepository;

    @Autowired
    UserRepository userRepository;

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
            Model model) {
        //model.addAttribute("histories", historyRepository.findAll());
        Iterable<History> histories;

        if(username != null && !username.isEmpty() && admin != null && !admin.isEmpty() && dateFrom != null && dateTo != null) {
            histories = historyRepository.findByUserIdAndAdminIdAndDateBetween(
                    userRepository.findByUsername(username).getId(),
                    userRepository.findByUsername(admin).getId(),
                    dateFrom, dateTo
            );
            model.addAttribute("histories", histories);
        }
        else if (username != null && !username.isEmpty()) {
            histories = historyRepository.findByUserIdAndDateBetween(userRepository.findByUsername(username).getId(), dateFrom, dateTo);
            model.addAttribute("histories", histories);
        } else if (admin != null && !admin.isEmpty()) {
            histories = historyRepository.findByAdminIdAndDateBetween(userRepository.findByUsername(admin).getId(), dateFrom, dateTo);
            model.addAttribute("histories", histories);
        }
            else {
                histories = historyRepository.findByDateBetween(dateFrom, dateTo);
                model.addAttribute("histories", histories);
        }

        model.addAttribute("username", username);
        /*model.addAttribute("dateFrom", dateFrom);
        model.addAttribute("dateTo", dateTo);*/
        model.addAttribute("admin", admin);

        return "history";
    }

}
