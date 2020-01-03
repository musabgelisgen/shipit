package com.db.shipit.controllers;

import com.db.shipit.models.User;
import com.db.shipit.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

import static com.db.shipit.ShipitApplication.currentUser;

@Controller
public class StatisticsController {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    BranchRepository branchRepository;

    @Autowired
    PackageRepository packageRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    SubscriptionRepository subscriptionRepository;

    @GetMapping("/top_senders")
    public String getTopSenders (Model model) {
        if(currentUser == null) {
            model.addAttribute("user", new User());
            return "login";
        }
        else if(customerRepository.searchCustomerFromId(currentUser.getID()) == null) {
            List<Map<String, Object>> top_senders = packageRepository.getTopSenders();
            model.addAttribute("top_senders", top_senders);
            return "top_senders";
        }
        else
            return "redirect:/my_account";
    }

    @GetMapping("/branch_statistics")
    public String getBranchStatistics (Model model) {
        if(currentUser == null) {
            model.addAttribute("user", new User());
            return "login";
        }
        else if(customerRepository.searchCustomerFromId(currentUser.getID()) == null) {
            Map<String, String> branchStatistics = packageRepository.getBranchStatistics();
            model.addAttribute("branchStatistics", branchStatistics);
            return "branchStatistics";
        }
        else
            return "redirect:/my_account";
    }
}
