package com.db.shipit.controllers;

import com.db.shipit.models.Branch;
import com.db.shipit.models.Customer;
import com.db.shipit.models.Package;
import com.db.shipit.repositories.BranchRepository;
import com.db.shipit.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class PackageController {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    BranchRepository branchRepository;

    @GetMapping("/send_package")
    public String sendPackage (Model model){
        List<String> receivers = customerRepository.getAllCustomers().stream().map(Customer::getIdAndFullName).collect(Collectors.toList());
        model.addAttribute("receivers", receivers);

        List<String> branches = branchRepository.getAllBranches().stream().map(Branch::getBranchAndCityName).collect(Collectors.toList());
        model.addAttribute("branches", branches);

        model.addAttribute("package", new Package());
        return "send_package";
    }

    @PostMapping("/send_package")
    public String commitSendPackage (Model model, @ModelAttribute("package") Package pack){
        System.out.println(pack);
        return "send_package";
    }

    @GetMapping("/package/{id}")
    public String getPackageInfo (@PathVariable String id, Model model){


        return "package";
    }
}
