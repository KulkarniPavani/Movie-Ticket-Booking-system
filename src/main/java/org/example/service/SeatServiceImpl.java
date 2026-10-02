package org.example.service;

import org.example.dao.SeatDAO;
import org.example.dao.SeatDAOImpl;
import org.example.model.Seat;

import java.util.List;

public class SeatServiceImpl implements SeatService {

    private final SeatDAO seatDAO;

    public SeatServiceImpl() {
        this.seatDAO = new SeatDAOImpl();
    }

    public SeatServiceImpl(SeatDAO seatDAO) {
        this.seatDAO = seatDAO;
    }

    @Override
    public boolean addSeat(Seat seat) {

        if (seat == null ||
                seat.getTheatre() == null ||
                seat.getTheatre().getTheatreId() <= 0 ||
                seat.getSeatNumber() == null ||
                seat.getSeatNumber().isBlank() ||
                seat.getPrice() == null) {

            return false;
        }

        return seatDAO.addSeat(seat);
    }

    @Override
    public Seat fetchSeat(int seatId) {

        if (seatId <= 0) {
            return null;
        }

        return seatDAO.fetchSeat(seatId);
    }

    @Override
    public boolean editSeat(Seat seat) {

        if (seat == null ||
                seat.getSeatId() <= 0 ||
                seat.getTheatre() == null ||
                seat.getTheatre().getTheatreId() <= 0 ||
                seat.getSeatNumber() == null ||
                seat.getSeatNumber().isBlank() ||
                seat.getPrice() == null) {

            return false;
        }

        return seatDAO.editSeat(seat);
    }

    @Override
    public boolean removeSeat(int seatId) {

        if (seatId <= 0) {
            return false;
        }

        return seatDAO.removeSeat(seatId);
    }

    @Override
    public List<Seat> getAvailableSeats(int showId) {

        if (showId <= 0) {
            return List.of();
        }

        return seatDAO.getAvailableSeats(showId);
    }
}