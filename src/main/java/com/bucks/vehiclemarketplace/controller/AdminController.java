package com.bucks.vehiclemarketplace.controller;

import com.bucks.vehiclemarketplace.model.Vehicle;
import com.bucks.vehiclemarketplace.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Value("${upload.path}")
    private String uploadPath;

    @Autowired
    private VehicleService vehicleService;

    // ==========================
    // Dashboard
    // ==========================
    @GetMapping("/dashboard")
    public String dashboard(Model model) {

        model.addAttribute("vehicles", vehicleService.getAllVehicles());
        model.addAttribute("totalVehicles", vehicleService.getTotalVehicles());
        model.addAttribute("dieselVehicles", vehicleService.getDieselVehicles());

        return "admin-dashboard";
    }

    // ==========================
    // Show Add Vehicle Form
    // ==========================
    @GetMapping("/add")
    public String showAddVehicleForm(Model model) {

        model.addAttribute("vehicle", new Vehicle());

        return "add-vehicle";
    }

    // ==========================
    // Save Vehicle
    // ==========================
    @PostMapping("/save")
    public String saveVehicle(
            @ModelAttribute Vehicle vehicle,
            @RequestParam("imageFile") MultipartFile imageFile) {

        try {

            if (!imageFile.isEmpty()) {

                Path uploadDirectory = Paths.get(uploadPath);

                if (!Files.exists(uploadDirectory)) {
                    Files.createDirectories(uploadDirectory);
                }

                String fileName = imageFile.getOriginalFilename();

                Path filePath = uploadDirectory.resolve(fileName);

                Files.copy(imageFile.getInputStream(), filePath);

                vehicle.setImageUrl(fileName);
            }

            vehicleService.saveVehicle(vehicle);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "redirect:/admin/dashboard";
    }

    // ==========================
    // Edit Vehicle
    // ==========================
    @GetMapping("/edit/{id}")
    public String editVehicle(@PathVariable Long id, Model model) {

        Vehicle vehicle = vehicleService.getVehicleById(id)
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));

        model.addAttribute("vehicle", vehicle);

        return "edit-vehicle";
    }

    // ==========================
    // Delete Vehicle
    // ==========================
    @GetMapping("/delete/{id}")
    public String deleteVehicle(@PathVariable Long id) {

        vehicleService.deleteVehicle(id);

        return "redirect:/admin/dashboard";
    }

    // ==========================
    // Search
    // ==========================
    @GetMapping("/search")
    public String searchVehicles(
            @RequestParam("keyword") String keyword,
            Model model) {

        model.addAttribute("vehicles", vehicleService.searchVehicles(keyword));
        model.addAttribute("totalVehicles", vehicleService.getTotalVehicles());
        model.addAttribute("dieselVehicles", vehicleService.getDieselVehicles());

        return "admin-dashboard";
    }

}