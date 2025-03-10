package com.maider.erp.web.controllers;

import com.maider.erp.domain.entities.User;
import com.maider.erp.domain.services.UserService;
import com.maider.erp.domain.result.Result;
import com.maider.erp.domain.result.Success;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class SignupController {
    @Autowired
    UserService userService;

    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(
            @RequestParam String name,
            @RequestParam String password,
            Model model) {
        User user = new User(name, password, new String[]{"USER"});
        Result<User, String> newUser = userService.createUser(user);

        if (newUser instanceof Success<User, String>) {
            return "redirect:/login";
        }
        model.addAttribute("error", "Username is already registered.");
        return "signup";
    }
}
