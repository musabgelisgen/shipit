package com.db.shipit.controllers;

import com.db.shipit.models.Customer;
import com.db.shipit.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomerController {

    @Autowired
    CustomerRepository customerRepository;

    @GetMapping("/register")
    public String register(Model model){
        String[] cities = new String[]{"Ankara", "Istanbul", "Izmir"};
        model.addAttribute("cities", cities);
        model.addAttribute("customer", new Customer());

        return "customer_register";
    }

    @PostMapping("/register")
    public String register_post(Model model, @ModelAttribute("customer") Customer customer){
        System.out.println(customer);

        customerRepository.save(customer);
        return "customer_register";
    }


}
