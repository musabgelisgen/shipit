package com.db.shipit.controllers;

import com.db.shipit.models.Subscription;
import com.db.shipit.repositories.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.db.shipit.ShipitApplication.currentUser;
@Controller
public class SubscriptionController {
    @Autowired
    SubscriptionRepository subscriptionRepository;

    @GetMapping("/my_subscriptions")
    public String showSubscriptions (Model model) throws ParseException {
        List<Subscription> subscriptions = subscriptionRepository.getSubscriptionByID(currentUser.getID());
        Subscription latestSubscription = subscriptions.get(0);
        Subscription currSubscription = null;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date latestSubDate=formatter.parse(latestSubscription.getEndDate());
        Date currDate = new Date();
        if((latestSubDate.compareTo(currDate) > 0) && latestSubscription.getSubscriptionTier() >  latestSubscription.getUsedPackageRights()){
            currSubscription = latestSubscription;
        }

        model.addAttribute("subscription", currSubscription);

        model.addAttribute("subscription_history", subscriptions);

        return "my_subscriptions";
    }
}
