package org.example.service;

import org.example.dao.BookedSeatDAO;
import org.example.model.BookedSeat;
import org.example.model.Booking;
import org.example.model.Seat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookedSeatServiceImplTest {

    private BookedSeatDAO bookedSeatDAO;
    private BookedSeatServiceImpl bookedSeatService;

    @BeforeEach
    void setUp() {
        bookedSeatDAO = Mockito.mock(BookedSeatDAO.class);
        bookedSeatService = new BookedSeatServiceImpl(bookedSeatDAO);
    }

    @Test
    void testStoreBookedSeat() {
        Seat seat = new Seat();
        seat.setSeatId(1);

        Booking booking = new Booking();
        booking.setBookingId(1);

        BookedSeat bookedSeat = new BookedSeat();
        bookedSeat.setSeat(seat);
        bookedSeat.setBooking(booking);

        when(bookedSeatDAO.storeBookedSeat(bookedSeat)).thenReturn(true);

        boolean result = bookedSeatService.storeBookedSeat(bookedSeat);

        assertTrue(result);
        verify(bookedSeatDAO).storeBookedSeat(bookedSeat);
    }

    @Test
    void testReadBookedSeat() {
        BookedSeat bookedSeat = new BookedSeat();
        bookedSeat.setBookedSeatId(1);

        when(bookedSeatDAO.readBookedSeat(1)).thenReturn(bookedSeat);

        BookedSeat result = bookedSeatService.readBookedSeat(1);

        assertNotNull(result);
        assertEquals(1, result.getBookedSeatId());
        verify(bookedSeatDAO).readBookedSeat(1);
    }

    @Test
    void testModifyBookedSeat() {
        Seat seat = new Seat();
        seat.setSeatId(1);

        Booking booking = new Booking();
        booking.setBookingId(1);

        BookedSeat bookedSeat = new BookedSeat();
        bookedSeat.setBookedSeatId(1);
        bookedSeat.setSeat(seat);
        bookedSeat.setBooking(booking);

        when(bookedSeatDAO.modifyBookedSeat(bookedSeat)).thenReturn(true);

        boolean result = bookedSeatService.modifyBookedSeat(bookedSeat);

        assertTrue(result);
        verify(bookedSeatDAO).modifyBookedSeat(bookedSeat);
    }

    @Test
    void testRemoveBookedSeat() {
        when(bookedSeatDAO.removeBookedSeat(1)).thenReturn(true);

        boolean result = bookedSeatService.removeBookedSeat(1);

        assertTrue(result);
        verify(bookedSeatDAO).removeBookedSeat(1);
    }
}