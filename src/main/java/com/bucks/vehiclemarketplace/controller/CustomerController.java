package com.bucks.vehiclemarketplace.controller;

import com.bucks.vehiclemarketplace.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CustomerController {

    @Autowired
    private VehicleService vehicleService;

    @GetMapping("/vehicles")
    public String vehicles(Model model){

        model.addAttribute("vehicles",
                vehicleService.getAllVehicles());

        return "vehicles";
    }

}