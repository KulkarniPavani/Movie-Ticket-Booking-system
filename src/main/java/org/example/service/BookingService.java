package org.example.service;

import org.example.model.Booking;
import org.example.model.Seat;

import java.util.List;

public interface BookingService {

    boolean saveBooking(Booking booking);

    Booking findBooking(int bookingId);

    boolean changeBooking(Booking booking);

    List<Booking> getBookingsByUser(int userId);

    boolean bookTicket(Booking booking, List<Seat> selectedSeats);
}