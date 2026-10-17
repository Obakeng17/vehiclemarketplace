package com.bucks.vehiclemarketplace.service;

import com.bucks.vehiclemarketplace.model.Vehicle;
import com.bucks.vehiclemarketplace.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;

    // Get all vehicles
    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }

    // Get vehicle by ID
    public Optional<Vehicle> getVehicleById(Long id) {
        return vehicleRepository.findById(id);
    }

    // Save a vehicle
    public Vehicle saveVehicle(Vehicle vehicle) {
        return vehicleRepository.save(vehicle);
    }

    // Delete a vehicle
    public void deleteVehicle(Long id) {
        vehicleRepository.deleteById(id);
    }

    //
    public long getTotalVehicles() {
    return vehicleRepository.count();
}

public long getDieselVehicles() {
    return vehicleRepository.countByFuelType("Diesel");
}

public List<Vehicle> searchVehicles(String make) {
    return vehicleRepository.findByMakeContainingIgnoreCase(make);
}
}