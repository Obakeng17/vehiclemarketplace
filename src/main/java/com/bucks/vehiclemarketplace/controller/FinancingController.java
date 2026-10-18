package com.bucks.vehiclemarketplace.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FinancingController {

    @GetMapping("/financing")
    public String financing() {
        return "financing";
    }
}