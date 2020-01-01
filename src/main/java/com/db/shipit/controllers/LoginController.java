package com.db.shipit.controllers;

import com.db.shipit.models.Customer;
import com.db.shipit.models.User;
import com.db.shipit.repositories.CustomerRepository;
import com.db.shipit.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import static com.db.shipit.ShipitApplication.currentUser;

@Controller
public class LoginController {

    @Autowired
    UserRepository userRepository;


    @GetMapping("")
    public String login (Model model){
        if(currentUser != null)
            return "redirect:/my_account";

        model.addAttribute("user", new User());
        return "login";
    }

    @PostMapping("")
    public String checkCredentials (Model model, @ModelAttribute("user") User user){
        User user1 = userRepository.checkCredentials(user);

        if (user1 != null){
            System.out.println(user1);
            currentUser = user1;
            return "redirect:/my_account";
        }
        else
            System.out.println("User Not Found");

        return "login";
    }
}

//todo: determine if user is customer or customer service
//todo: then create session depending on the type
//todo: redirect to index page
