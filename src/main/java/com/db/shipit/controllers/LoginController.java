package com.db.shipit.controllers;

import com.db.shipit.models.Customer;
import com.db.shipit.models.User;
import com.db.shipit.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("")
    public String login (Model model){

        model.addAttribute("user", new User());
        return "login";
    }

    @PostMapping("")
    public String checkCredentials (Model model, @ModelAttribute("user") User user){
        User user1 = userRepository.checkCredentials(user);

        if (user1 != null){
            System.out.println(user1);
        }

        return "login";
    }
}

//todo: determine if user is customer or customer service
//todo: then create session depending on the type
//todo: redirect to index page
