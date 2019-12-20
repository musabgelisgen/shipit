package com.db.shipit.controllers;

import com.db.shipit.models.User;
import com.db.shipit.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

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
        Optional<User> user1 = userRepository.checkCredentials(user);
        user1.ifPresent(System.out::println);

        return "login";
    }
}
