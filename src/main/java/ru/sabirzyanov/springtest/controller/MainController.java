package ru.sabirzyanov.springtest.controller;

/**
 * Created by Marselius on 11.12.2018.
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.sabirzyanov.springtest.domain.Customer;
import ru.sabirzyanov.springtest.domain.User;
import ru.sabirzyanov.springtest.repos.CustomerRepository;

@Controller
public class MainController {

    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping("/")
    public String greeting(Model model) {
        return "greeting";
    }

    @GetMapping("/main")
    public String main(@RequestParam(required = false, defaultValue = "") String firstName, Model model) {
        Iterable<Customer> customers = customerRepository.findAll();

        if (firstName != null && !firstName.isEmpty()) {
            customers = customerRepository.findByFirstName(firstName);
        }
        else customers = customerRepository.findAll();

        model.addAttribute("customers", customers);
        model.addAttribute("firstName", firstName);

        return "main";
    }

    @PostMapping("/main")
    public String addCustomer(
                              @AuthenticationPrincipal User user,
                              @RequestParam String firstName,
                              @RequestParam String secondName,
                              @RequestParam String phoneNumber,
                              @RequestParam Long points,
                              Model model) {
        Customer customer = new Customer(firstName, secondName, phoneNumber, points, user);
        customerRepository.save(customer);

        Iterable<Customer> customers = customerRepository.findAll();

        model.addAttribute("customers", customers);

        return "main";
    }

}