package org.example.service;

import org.example.dao.BookedSeatDAO;
import org.example.dao.BookedSeatDAOImpl;
import org.example.model.BookedSeat;

public class BookedSeatServiceImpl implements BookedSeatService {

    private final BookedSeatDAO bookedSeatDAO;

    public BookedSeatServiceImpl() {
        this.bookedSeatDAO = new BookedSeatDAOImpl();
    }

    public BookedSeatServiceImpl(BookedSeatDAO bookedSeatDAO) {
        this.bookedSeatDAO = bookedSeatDAO;
    }

    @Override
    public boolean storeBookedSeat(BookedSeat bookedSeat) {

        if (bookedSeat == null ||
                bookedSeat.getSeat() == null ||
                bookedSeat.getSeat().getSeatId() <= 0 ||
                bookedSeat.getBooking() == null ||
                bookedSeat.getBooking().getBookingId() <= 0) {

            return false;
        }

        return bookedSeatDAO.storeBookedSeat(bookedSeat);
    }

    @Override
    public BookedSeat readBookedSeat(int bookedSeatId) {

        if (bookedSeatId <= 0) {
            return null;
        }

        return bookedSeatDAO.readBookedSeat(bookedSeatId);
    }

    @Override
    public boolean modifyBookedSeat(BookedSeat bookedSeat) {

        if (bookedSeat == null ||
                bookedSeat.getBookedSeatId() <= 0 ||
                bookedSeat.getSeat() == null ||
                bookedSeat.getSeat().getSeatId() <= 0 ||
                bookedSeat.getBooking() == null ||
                bookedSeat.getBooking().getBookingId() <= 0) {

            return false;
        }

        return bookedSeatDAO.modifyBookedSeat(bookedSeat);
    }

    @Override
    public boolean removeBookedSeat(int bookedSeatId) {

        if (bookedSeatId <= 0) {
            return false;
        }

        return bookedSeatDAO.removeBookedSeat(bookedSeatId);
    }
}