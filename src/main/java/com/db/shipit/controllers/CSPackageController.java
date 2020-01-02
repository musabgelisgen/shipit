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
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class CSPackageController {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    BranchRepository branchRepository;

    @Autowired
    PackageRepository packageRepository;

    @GetMapping("/cs_send_package")
    public String sendPackage (Model model){
        List<String> receivers = customerRepository.getAllCustomers().stream().map(Customer::getIdAndFullName).collect(Collectors.toList());
        model.addAttribute("receivers", receivers);

        List<String> branches = branchRepository.getAllBranches().stream().map(Branch::getBranchAndCityName).collect(Collectors.toList());
        model.addAttribute("branches", branches);

        model.addAttribute("package", new Package());
        return "cs_send_package";
    }

    @PostMapping("/cs_send_package")
    public String commitSendPackage (Model model, @ModelAttribute("package") Package packet){
        System.out.println(packet);
        packageRepository.commitPackage(packet);
        return "cs_send_package";
    }

    @GetMapping("/cs_packages")
    public String getAllPackages (
            @RequestParam(value = "receiver", required = false, defaultValue = "false") boolean receiver,
            @RequestParam(value = "sender", required = false, defaultValue = "false") boolean sender,
            @RequestParam(value = "preparing", required = false, defaultValue = "false") boolean preparing,
            @RequestParam(value = "onTransfer", required = false, defaultValue = "false") boolean onTransfer,
            @RequestParam(value = "onBranch", required = false, defaultValue = "false") boolean onBranch,
            @RequestParam(value = "delivered", required = false, defaultValue = "false") boolean delivered,
            @RequestParam(value = "declined", required = false, defaultValue = "false") boolean declined,
            Model model){

        Map<String, Boolean> modifications = new HashMap<>();
        modifications.put("receiver", receiver);
        modifications.put("sender", sender);
        modifications.put("preparing", preparing);
        modifications.put("onTransfer", onTransfer);
        modifications.put("onBranch", onBranch);
        modifications.put("delivered", delivered);
        modifications.put("declined", declined);

        List<Package> packages = packageRepository.getAllBranchPackages(modifications);
        model.addAttribute("packages", packages);

        return "cs_packages";
    }

    @GetMapping("/cs_packages/{id}")
    public String getPackageInfo (@PathVariable String id, Model model){
        return "cs_package";
    }
}
