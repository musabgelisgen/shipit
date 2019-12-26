package com.db.shipit.controllers;

import com.db.shipit.models.Customer;
import com.db.shipit.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


import static com.db.shipit.ShipitApplication.currentUser;

@Controller
public class AccountController {
    @Autowired
    CustomerRepository customerRepository;

    @GetMapping("/my_account")
    public String showInformation (Model model){
        Customer customer = customerRepository.searchCustomerFromId(currentUser.getID());
        model.addAttribute("customer", customer);

        return "my_account";
    }
}
