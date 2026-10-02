package org.example.dao;

import org.example.model.BookedSeat;

public interface BookedSeatDAO {

    boolean storeBookedSeat(BookedSeat bookedSeat);

    BookedSeat readBookedSeat(int bookedSeatId);

    boolean modifyBookedSeat(BookedSeat bookedSeat);

    boolean removeBookedSeat(int bookedSeatId);
}