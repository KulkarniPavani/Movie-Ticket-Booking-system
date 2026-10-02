package org.example.controller;

import org.example.model.Booking;
import org.example.model.Seat;
import org.example.service.BookingService;
import org.example.service.BookingServiceImpl;

import java.util.List;

public class BookingController {

    private final BookingService bookingService =
            new BookingServiceImpl();

    public boolean saveBooking(Booking booking) {
        return bookingService.saveBooking(booking);
    }

    public Booking findBooking(int bookingId) {
        return bookingService.findBooking(bookingId);
    }

    public boolean changeBooking(Booking booking) {
        return bookingService.changeBooking(booking);
    }

    public List<Booking> getBookingsByUser(int userId) {
        return bookingService.getBookingsByUser(userId);
    }

    public boolean bookTicket(
            Booking booking,
            List<Seat> selectedSeats) {
        return bookingService.bookTicket(
                booking,
                selectedSeats
        );
    }
}