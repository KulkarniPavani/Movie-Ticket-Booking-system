package org.example.service;

import org.example.model.BookedSeat;

public interface BookedSeatService {

    boolean storeBookedSeat(BookedSeat bookedSeat);

    BookedSeat readBookedSeat(int bookedSeatId);

    boolean modifyBookedSeat(BookedSeat bookedSeat);

    boolean removeBookedSeat(int bookedSeatId);
}