package com.maider.erp.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AccessDeniedController {

    @GetMapping("/403")
    public String accessDenied() {
        return "403";
    }
}
