package org.example.service;

import org.example.dao.BookingDAO;
import org.example.model.Booking;
import org.example.model.Seat;
import org.example.model.Show;
import org.example.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookingServiceImplTest {

    private BookingDAO bookingDAO;
    private SeatService seatService;
    private BookedSeatService bookedSeatService;
    private BookingServiceImpl bookingService;

    @BeforeEach
    void setUp() {
        bookingDAO = Mockito.mock(BookingDAO.class);
        seatService = Mockito.mock(SeatService.class);
        bookedSeatService = Mockito.mock(BookedSeatService.class);

        bookingService = new BookingServiceImpl(
                bookingDAO,
                seatService,
                bookedSeatService);
    }

    @Test
    void testSaveBooking() {
        Show show = new Show();
        show.setShowId(1);

        User user = new User();
        user.setUserId(1);

        Booking booking = new Booking();
        booking.setShow(show);
        booking.setUser(user);
        booking.setBookingDate(LocalDateTime.now());
        booking.setTotalAmount(new BigDecimal("400.00"));
        booking.setBookingStatus("CONFIRMED");

        when(bookingDAO.saveBooking(booking)).thenReturn(true);

        boolean result = bookingService.saveBooking(booking);

        assertTrue(result);
        verify(bookingDAO).saveBooking(booking);
    }

    @Test
    void testFindBooking() {
        Booking booking = new Booking();
        booking.setBookingId(1);

        when(bookingDAO.findBooking(1)).thenReturn(booking);

        Booking result = bookingService.findBooking(1);

        assertNotNull(result);
        assertEquals(1, result.getBookingId());
        verify(bookingDAO).findBooking(1);
    }

    @Test
    void testChangeBooking() {
        Booking booking = new Booking();
        booking.setBookingId(1);

        when(bookingDAO.changeBooking(booking)).thenReturn(true);

        boolean result = bookingService.changeBooking(booking);

        assertTrue(result);
        verify(bookingDAO).changeBooking(booking);
    }

    @Test
    void testGetBookingsByUser() {
        Booking booking = new Booking();
        booking.setBookingId(1);

        List<Booking> bookings = List.of(booking);

        when(bookingDAO.getBookingsByUser(1)).thenReturn(bookings);

        List<Booking> result = bookingService.getBookingsByUser(1);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1, result.get(0).getBookingId());
        verify(bookingDAO).getBookingsByUser(1);
    }

    @Test
    void testBookTicket() {
        Show show = new Show();
        show.setShowId(1);

        User user = new User();
        user.setUserId(1);

        Booking booking = new Booking();
        booking.setShow(show);
        booking.setUser(user);
        booking.setTotalAmount(new BigDecimal("400.00"));
        booking.setBookingStatus("CONFIRMED");

        Seat seat = new Seat();
        seat.setSeatId(1);

        List<Seat> selectedSeats = List.of(seat);

        when(seatService.getAvailableSeats(1))
                .thenReturn(selectedSeats);

        when(bookingDAO.saveBooking(booking))
                .thenReturn(true);

        when(bookedSeatService.storeBookedSeat(any()))
                .thenReturn(true);

        boolean result =
                bookingService.bookTicket(booking, selectedSeats);

        assertTrue(result);
        assertNotNull(booking.getBookingDate());

        verify(seatService).getAvailableSeats(1);
        verify(bookingDAO).saveBooking(booking);
        verify(bookedSeatService).storeBookedSeat(any());
    }
}