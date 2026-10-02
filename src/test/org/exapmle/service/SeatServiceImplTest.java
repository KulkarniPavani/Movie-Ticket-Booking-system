package org.example.service;

import org.example.dao.SeatDAO;
import org.example.model.Seat;
import org.example.model.Theatre;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SeatServiceImplTest {

    private SeatDAO seatDAO;
    private SeatServiceImpl seatService;

    @BeforeEach
    void setUp() {
        seatDAO = Mockito.mock(SeatDAO.class);
        seatService = new SeatServiceImpl(seatDAO);
    }

    @Test
    void testAddSeat() {

        Theatre theatre = new Theatre();
        theatre.setTheatreId(1);

        Seat seat = new Seat();
        seat.setTheatre(theatre);
        seat.setSeatNumber("A1");
        seat.setSeatType("Regular");
        seat.setPrice(new BigDecimal("200.00"));

        when(seatDAO.addSeat(seat)).thenReturn(true);

        boolean result = seatService.addSeat(seat);

        assertTrue(result);

        verify(seatDAO).addSeat(seat);
    }

    @Test
    void testFetchSeat() {

        Theatre theatre = new Theatre();
        theatre.setTheatreId(1);

        Seat seat = new Seat();
        seat.setSeatId(1);
        seat.setTheatre(theatre);
        seat.setSeatNumber("A1");

        when(seatDAO.fetchSeat(1)).thenReturn(seat);

        Seat result = seatService.fetchSeat(1);

        assertNotNull(result);
        assertEquals("A1", result.getSeatNumber());

        verify(seatDAO).fetchSeat(1);
    }

    @Test
    void testEditSeat() {

        Theatre theatre = new Theatre();
        theatre.setTheatreId(1);

        Seat seat = new Seat();
        seat.setSeatId(1);
        seat.setTheatre(theatre);
        seat.setSeatNumber("A1");
        seat.setSeatType("Regular");
        seat.setPrice(new BigDecimal("200.00"));

        when(seatDAO.editSeat(seat)).thenReturn(true);

        boolean result = seatService.editSeat(seat);

        assertTrue(result);

        verify(seatDAO).editSeat(seat);
    }

    @Test
    void testRemoveSeat() {

        when(seatDAO.removeSeat(1)).thenReturn(true);

        boolean result = seatService.removeSeat(1);

        assertTrue(result);

        verify(seatDAO).removeSeat(1);
    }

    @Test
    void testGetAvailableSeats() {

        Theatre theatre = new Theatre();
        theatre.setTheatreId(1);

        Seat seat = new Seat();
        seat.setSeatId(1);
        seat.setTheatre(theatre);
        seat.setSeatNumber("A1");
        seat.setSeatType("Regular");
        seat.setPrice(new BigDecimal("200.00"));

        List<Seat> seats = List.of(seat);

        when(seatDAO.getAvailableSeats(1)).thenReturn(seats);

        List<Seat> result = seatService.getAvailableSeats(1);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("A1", result.get(0).getSeatNumber());

        verify(seatDAO).getAvailableSeats(1);
    }
}