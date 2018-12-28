package ru.sabirzyanov.springtest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.sabirzyanov.springtest.domain.Customer;
import ru.sabirzyanov.springtest.domain.History;
import ru.sabirzyanov.springtest.repos.HistoryRepository;

@Controller
@RequestMapping("/history")
@PreAuthorize("hasAuthority('ADMIN')")
public class HistoryController {

    @Autowired
    HistoryRepository historyRepository;

    /*@GetMapping
    public String HistoryList(Model model) {
        model.addAttribute("histories", historyRepository.findAll());

        return "history";
    }*/

    @GetMapping
    public String HistorySearch(@RequestParam(required = false, defaultValue = "") Long filter, Model model) {
        model.addAttribute("histories", historyRepository.findAll());

        Iterable<History> histories;

        if (filter != null /*&& !filter.isEmpty()*/) {
            histories = historyRepository.findByTotal(filter);
        }
        else histories = historyRepository.findAll();

        model.addAttribute("customers", histories);
        model.addAttribute("filter", filter);

        return "history";
    }

}
