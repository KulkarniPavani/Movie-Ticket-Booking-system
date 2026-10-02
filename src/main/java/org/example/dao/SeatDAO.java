package org.example.dao;

import org.example.model.Seat;

import java.util.List;

public interface SeatDAO {

    boolean addSeat(Seat seat);

    Seat fetchSeat(int seatId);

    boolean editSeat(Seat seat);

    boolean removeSeat(int seatId);

    List<Seat> getAvailableSeats(int showId);
}