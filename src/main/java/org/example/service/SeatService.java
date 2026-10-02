package org.example.service;

import org.example.model.Seat;

import java.util.List;

public interface SeatService {

    boolean addSeat(Seat seat);

    Seat fetchSeat(int seatId);

    boolean editSeat(Seat seat);

    boolean removeSeat(int seatId);

    List<Seat> getAvailableSeats(int showId);
}