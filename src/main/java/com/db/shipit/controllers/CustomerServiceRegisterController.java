package com.db.shipit.controllers;

import com.db.shipit.models.Branch;
import com.db.shipit.models.CustomerService;
import com.db.shipit.repositories.BranchRepository;
import com.db.shipit.repositories.CustomerServiceRepository;
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
public class CustomerServiceRegisterController {

    @Autowired
    CustomerServiceRepository customerRepository;

    @Autowired
    BranchRepository branchRepository;

    @GetMapping("/cs_register")
    public String customer_service_register(Model model){
        if(currentUser != null)
            return "redirect:/my_account";
        List<String> branches = branchRepository.getAllBranches().stream().map(Branch::getName).collect(Collectors.toList());
        model.addAttribute("branches", branches);
        model.addAttribute("customer_service", new CustomerService());

        return "customer_service_register";
    }

    @PostMapping("/cs_register")
    public String customer_service_register_post(Model model, @ModelAttribute("customer_service") CustomerService customerService){
        System.out.println(customerService);

        customerRepository.saveCustomerService(customerService);
        return "customer_service_register";
    }
}

