package org.example.dao;

import org.example.model.Booking;

import java.util.List;

public interface BookingDAO {

    boolean saveBooking(Booking booking);

    Booking findBooking(int bookingId);

    boolean changeBooking(Booking booking);

    List<Booking> getBookingsByUser(int userId);

    List<String> getBookedSeatsForShow(int showId);
}