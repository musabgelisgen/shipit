package com.db.shipit.controllers;

import com.db.shipit.models.Package;
import com.db.shipit.models.Subscription;
import com.db.shipit.repositories.CustomerRepository;
import com.db.shipit.repositories.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.db.shipit.ShipitApplication.currentUser;
@Controller
public class SubscriptionController {
    @Autowired
    SubscriptionRepository subscriptionRepository;

    @Autowired
    CustomerRepository customerRepository;

    @PostMapping("/my_subscriptions")
    public String buttonClicked(Model model, @ModelAttribute("button") String button, @RequestParam(value = "sub_tier", required = false, defaultValue = "-1") String tier) throws ParseException {
        if(button.equals("cancel"))
            subscriptionRepository.cancelSubscription();
        else if(button.equals("renew")){
            subscriptionRepository.renewSubscription();
        }

        if(!tier.equals("-1")){
            int tierValue = Integer.parseInt(tier);
            int cost;
            switch(tierValue) {
                case 5:
                    cost = 50;
                    break;
                case 10:
                    cost = 80;
                    break;
                case 15:
                    cost = 100;
                    break;
                default:
                    cost = 50;
            }
            if(customerRepository.getCustomerBalance() >= cost) {

                customerRepository.changeCustomerBalance(-cost);
                subscriptionRepository.addSubscription(tierValue);
            }

        }
        return "redirect:/my_subscriptions";
    }

    @GetMapping("/my_subscriptions")
    public String showSubscriptions (Model model) throws ParseException {
        List<Subscription> subscriptions = subscriptionRepository.getSubscriptionByID(currentUser.getID());
        Subscription latestSubscription = subscriptions.get(0);
        Subscription currSubscription = null;

        if(latestSubscription.isIs_active())
            currSubscription = latestSubscription;

        model.addAttribute("subscription", currSubscription);

        model.addAttribute("subscription_history", subscriptions);

        return "my_subscriptions";
    }
}
