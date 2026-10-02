package org.example.controller;

import org.example.model.Seat;
import org.example.service.SeatService;
import org.example.service.SeatServiceImpl;

import java.util.List;

public class SeatController {

    private final SeatService seatService =
            new SeatServiceImpl();

    public boolean addSeat(Seat seat) {
        return seatService.addSeat(seat);
    }

    public Seat fetchSeat(int seatId) {
        return seatService.fetchSeat(seatId);
    }

    public boolean editSeat(Seat seat) {
        return seatService.editSeat(seat);
    }

    public boolean removeSeat(int seatId) {
        return seatService.removeSeat(seatId);
    }

    public List<Seat> getAvailableSeats(int showId) {
        return seatService.getAvailableSeats(showId);
    }
}