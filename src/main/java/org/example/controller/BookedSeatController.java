package org.example.controller;

import org.example.model.BookedSeat;
import org.example.service.BookedSeatService;
import org.example.service.BookedSeatServiceImpl;

public class BookedSeatController {

    private final BookedSeatService bookedSeatService =
            new BookedSeatServiceImpl();

    public boolean storeBookedSeat(BookedSeat bookedSeat) {
        return bookedSeatService.storeBookedSeat(bookedSeat);
    }

    public BookedSeat readBookedSeat(int bookedSeatId) {
        return bookedSeatService.readBookedSeat(bookedSeatId);
    }

    public boolean modifyBookedSeat(BookedSeat bookedSeat) {
        return bookedSeatService.modifyBookedSeat(bookedSeat);
    }

    public boolean removeBookedSeat(int bookedSeatId) {
        return bookedSeatService.removeBookedSeat(bookedSeatId);
    }
}