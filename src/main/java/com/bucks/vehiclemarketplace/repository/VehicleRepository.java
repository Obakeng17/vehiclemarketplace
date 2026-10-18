package com.bucks.vehiclemarketplace.repository;

import com.bucks.vehiclemarketplace.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    long countByFuelType(String fuelType);

    List<Vehicle> findByMakeContainingIgnoreCase(String make);

}