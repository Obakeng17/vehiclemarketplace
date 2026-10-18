package com.bucks.vehiclemarketplace.controller;

import com.bucks.vehiclemarketplace.model.Booking;
import com.bucks.vehiclemarketplace.model.Vehicle;
import com.bucks.vehiclemarketplace.service.BookingService;
import com.bucks.vehiclemarketplace.service.VehicleService;
import com.bucks.vehiclemarketplace.service.EmailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private EmailService emailService;

    // ===============================
    // CUSTOMER BOOKING PAGE
    // ===============================

    @GetMapping("/book/{id}")
    public String showBookingForm(@PathVariable Long id, Model model) {

        Vehicle vehicle = vehicleService
                .getVehicleById(id)
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));

        Booking booking = new Booking();
        booking.setVehicle(vehicle);

        model.addAttribute("vehicle", vehicle);
        model.addAttribute("booking", booking);

        return "testDrive";
    }

    // ===============================
    // SAVE BOOKING
    // ===============================

    @PostMapping("/book/save")
    public String saveBooking(@ModelAttribute Booking booking) {

        booking.setStatus("Pending");

        // Save booking to database
        bookingService.saveBooking(booking);

        // Customer's full name
        String customerName = booking.getFirstName()
                + " " + booking.getLastName();

        // Vehicle name
        String vehicleName = booking.getVehicle().getMake()
                + " " + booking.getVehicle().getModel();

        // ===============================
        // CUSTOMER EMAIL
        // ===============================

        String customerSubject =
                "Bucks Motors - Test Drive Booking Confirmation";

        String customerBody =
                "Dear " + customerName + ",\n\n"
                + "Thank you for booking a test drive with Bucks Motors.\n\n"
                + "Booking Details:\n"
                + "-------------------------\n"
                + "Vehicle: " + vehicleName + "\n"
                + "Booking Date: " + booking.getBookingDate() + "\n"
                + "Booking Time: " + booking.getBookingTime() + "\n"
                + "Status: Pending\n"
                + "-------------------------\n\n"
                + "Your booking has been received successfully.\n"
                + "We will contact you once your booking has been reviewed.\n\n"
                + "Regards,\n"
                + "Bucks Motors";

        // Send confirmation email
        emailService.sendEmail(
                booking.getEmail(),
                customerSubject,
                customerBody
        );

        return "redirect:/booking-success";
    }

    // ===============================
    // SUCCESS PAGE
    // ===============================

    @GetMapping("/booking-success")
    public String bookingSuccess() {

        return "booking-success";
    }

    // ===============================
    // ADMIN BOOKINGS PAGE
    // ===============================

    @GetMapping("/admin/bookings")
    public String adminBookings(Model model) {

        model.addAttribute("bookings",
                bookingService.getAllBookings());

        model.addAttribute("totalBookings",
                bookingService.totalBookings());

        model.addAttribute("pendingBookings",
                bookingService.pendingBookings());

        model.addAttribute("approvedBookings",
                bookingService.approvedBookings());

        model.addAttribute("declinedBookings",
                bookingService.declinedBookings());

        model.addAttribute("completedBookings",
                bookingService.completedBookings());

        return "admin-bookings";
    }

    // ===============================
    // APPROVE BOOKING
    // ===============================

    @GetMapping("/admin/bookings/approve/{id}")
    public String approveBooking(@PathVariable Long id) {

        Booking booking = bookingService.getBookingById(id);

        booking.setStatus("Approved");

        bookingService.saveBooking(booking);

        return "redirect:/admin/bookings";
    }

    // ===============================
    // DECLINE BOOKING
    // ===============================

    @GetMapping("/admin/bookings/decline/{id}")
    public String declineBooking(@PathVariable Long id) {

        Booking booking = bookingService.getBookingById(id);

        booking.setStatus("Declined");

        bookingService.saveBooking(booking);

        return "redirect:/admin/bookings";
    }

    // ===============================
    // COMPLETE BOOKING
    // ===============================

    @GetMapping("/admin/bookings/complete/{id}")
    public String completeBooking(@PathVariable Long id) {

        Booking booking = bookingService.getBookingById(id);

        booking.setStatus("Completed");

        bookingService.saveBooking(booking);

        return "redirect:/admin/bookings";
    }

}