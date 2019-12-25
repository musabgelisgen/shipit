package com.db.shipit.controllers;

import com.db.shipit.models.Branch;
import com.db.shipit.models.Customer;
import com.db.shipit.models.Package;
import com.db.shipit.models.User;
import com.db.shipit.repositories.BranchRepository;
import com.db.shipit.repositories.CustomerRepository;
import com.db.shipit.repositories.PackageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class PackageController {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    BranchRepository branchRepository;

    @Autowired
    PackageRepository packageRepository;

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
    public String commitSendPackage (Model model, @ModelAttribute("package") Package packet){
        System.out.println(packet);
        packageRepository.commitPackage(packet);
        return "send_package";
    }

    @GetMapping("/packages")
    public String getAllPackages (Model model){
        User user = new User().setID("99c02f");
        Map<String, Integer> modifications = new HashMap<>();

        List<Package> packages = packageRepository.getAllPackages(user, modifications);

        return "package";
    }

    @GetMapping("/package/{id}")
    public String getPackageInfo (@PathVariable String id, Model model){


        return "package";
    }
}
