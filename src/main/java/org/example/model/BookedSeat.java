package org.example.model;

public class BookedSeat {
    private int bookedSeatId;
    private Seat seat;
    private Booking booking;

    public BookedSeat() {
    }
    public BookedSeat(int bookedSeatId, Seat seat, Booking booking) {
        this.bookedSeatId = bookedSeatId;
        this.seat = seat;
        this.booking = booking;
    }

    public int getBookedSeatId() {
        return bookedSeatId;
    }

    public void setBookedSeatId(int bookedSeatId) {
        this.bookedSeatId = bookedSeatId;
    }

    public Seat getSeat() {
        return seat;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }
}

