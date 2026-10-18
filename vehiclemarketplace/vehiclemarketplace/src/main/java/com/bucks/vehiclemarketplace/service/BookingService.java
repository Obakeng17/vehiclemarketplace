package com.bucks.vehiclemarketplace.service;

import com.bucks.vehiclemarketplace.model.Booking;
import com.bucks.vehiclemarketplace.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    public Booking saveBooking(Booking booking) {
        return bookingRepository.save(booking);
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public Booking getBookingById(Long id) {
        return bookingRepository.findById(id).orElse(null);
    }

    public void deleteBooking(Long id) {
        bookingRepository.deleteById(id);
    }

    public List<Booking> getPendingBookings() {
        return bookingRepository.findByStatus("Pending");
    }

    public List<Booking> searchBookings(String keyword) {
        return bookingRepository
                .findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(
                        keyword,
                        keyword
                );
    }

    public long totalBookings() {
        return bookingRepository.count();
    }

    public long pendingBookings() {
        return bookingRepository.findByStatus("Pending").size();
    }

    public long approvedBookings() {
        return bookingRepository.findByStatus("Approved").size();
    }

    public long declinedBookings() {
        return bookingRepository.findByStatus("Declined").size();
    }

    public long completedBookings() {
        return bookingRepository.findByStatus("Completed").size();
    }
}