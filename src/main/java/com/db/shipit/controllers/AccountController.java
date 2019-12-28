package com.db.shipit.controllers;

import com.db.shipit.models.Customer;
import com.db.shipit.models.CustomerService;
import com.db.shipit.models.Subscription;
import com.db.shipit.models.User;
import com.db.shipit.repositories.CustomerRepository;
import com.db.shipit.repositories.CustomerServiceRepository;
import com.db.shipit.repositories.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


import java.text.ParseException;
import java.util.List;

import static com.db.shipit.ShipitApplication.currentUser;

@Controller
public class AccountController {
    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    SubscriptionRepository subscriptionRepository;

    @Autowired
    CustomerServiceRepository customerServiceRepository;

    @PostMapping("/my_account")
    public String buttonClicked(Model model, @RequestParam(value = "credits", required = false, defaultValue = "-1") String credits, @RequestParam(value = "logout", required = false, defaultValue = "False") String logout) throws ParseException {
        if(logout.equals("true")){
            currentUser = null;
            model.addAttribute("user", new User());
            return "login";
        }
        int creditValue = Integer.parseInt(credits);
        if(creditValue != -1)
            customerRepository.changeCustomerBalance(creditValue);

        return "redirect:/my_account";
    }

    @GetMapping("/my_account")
    public String showInformation (Model model){
        if(currentUser == null) {
            model.addAttribute("user", new User());
            return "login";
        }
        else if(customerRepository.searchCustomerFromId(currentUser.getID()) != null) {
            Customer customer = customerRepository.searchCustomerFromId(currentUser.getID());
            List<Subscription> subscriptions = subscriptionRepository.getSubscriptionByID(customer.getID());
            Subscription sub = subscriptions.get(0);
            String custSubscription = "-";
            if (sub.isIs_active())
                custSubscription = sub.getID();

            model.addAttribute("customer", customer);
            model.addAttribute("custSubscription", custSubscription);

            return "my_account";
        }
        else{
            CustomerService customerService = customerServiceRepository.searchCustomerServiceById(currentUser.getID());
            model.addAttribute("customerService", customerService);

            return "customer_service_account";
        }
    }
}
