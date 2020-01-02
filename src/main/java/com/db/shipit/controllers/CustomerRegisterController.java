package com.db.shipit.controllers;

import com.db.shipit.models.Branch;
import com.db.shipit.models.Customer;
import com.db.shipit.repositories.BranchRepository;
import com.db.shipit.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.stream.Collectors;

import static com.db.shipit.ShipitApplication.currentUser;

@Controller
public class CustomerRegisterController {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    BranchRepository branchRepository;

    @GetMapping("/register")
    public String register(Model model){
        if(currentUser != null)
            return "redirect:/my_account";

        List<String> cities = branchRepository.getAllBranches().stream().map(Branch::getCity_name).collect(Collectors.toList());
        model.addAttribute("cities", cities);
        model.addAttribute("customer", new Customer());

        return "customer_register";
    }

    @PostMapping("/register")
    public String register_post(Model model, @ModelAttribute("customer") Customer customer){
        System.out.println(customer);

        customerRepository.saveCustomer(customer);
        return "customer_register";
    }


}
