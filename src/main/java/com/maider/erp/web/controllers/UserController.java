package com.maider.erp.web.controllers;

import com.maider.erp.domain.entities.User;
import com.maider.erp.domain.services.UserService;
import com.maider.erp.domain.result.Result;
import com.maider.erp.domain.result.Success;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;


@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public String getAll(Model model) {
        SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Result<List<User>, String> users = userService.getAll();
        if (users instanceof Success<?, ?>) {
            List<User> usersList = users.getValue();
            model.addAttribute("users", usersList);
            return "users";
        }
        model.addAttribute("error", users.getError());
        return "users";
    }

    @GetMapping("/users/update/{id}")
    public String showUpdateForm(@PathVariable Long id, Model model) {
        Result<User, String> userResult = userService.getUserById(id);
        if (userResult instanceof Success<?, ?>) {
            model.addAttribute("user", userResult.getValue());
        } else {
            model.addAttribute("error", userResult.getError());
        }
        return "updateUser";
    }

    @PostMapping("/users/update/{id}")
    public String updateUser(@PathVariable Long id, @ModelAttribute User updatedUser, RedirectAttributes redirectAttributes) {
        Result<User, String> updateResult = userService.updateUser(id, updatedUser);
        if (updateResult instanceof Success<?, ?>) {
            redirectAttributes.addFlashAttribute("success", "User updated successfully");
        } else {
            redirectAttributes.addFlashAttribute("error", updateResult.getError());
        }
        return "redirect:/users";
    }


    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Result<Boolean, String> deleteResult = userService.deleteById(id);
        if (deleteResult instanceof Success<?, ?>) {
            redirectAttributes.addFlashAttribute("success", "User deleted successfully");
        } else {
            redirectAttributes.addFlashAttribute("error", deleteResult.getError());
        }
        return "redirect:/users";
    }
}
