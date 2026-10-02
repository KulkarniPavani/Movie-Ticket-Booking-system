package org.example.service;

import org.example.dao.BookingDAO;
import org.example.dao.BookingDAOImpl;
import org.example.model.BookedSeat;
import org.example.model.Booking;
import org.example.model.Seat;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BookingServiceImpl implements BookingService {

    private final BookingDAO bookingDAO;
    private final SeatService seatService;
    private final BookedSeatService bookedSeatService;

    public BookingServiceImpl() {
        this.bookingDAO = new BookingDAOImpl();
        this.seatService = new SeatServiceImpl();
        this.bookedSeatService = new BookedSeatServiceImpl();
    }

    public BookingServiceImpl(
            BookingDAO bookingDAO,
            SeatService seatService,
            BookedSeatService bookedSeatService) {

        this.bookingDAO = bookingDAO;
        this.seatService = seatService;
        this.bookedSeatService = bookedSeatService;
    }

    @Override
    public boolean saveBooking(Booking booking) {
        if (booking == null ||
                booking.getShow() == null ||
                booking.getShow().getShowId() <= 0 ||
                booking.getUser() == null ||
                booking.getUser().getUserId() <= 0 ||
                booking.getBookingDate() == null ||
                booking.getTotalAmount() == null ||
                booking.getTotalAmount().signum() < 0 ||
                booking.getBookingStatus() == null ||
                booking.getBookingStatus().isBlank()) {
            return false;
        }

        return bookingDAO.saveBooking(booking);
    }

    @Override
    public Booking findBooking(int bookingId) {
        if (bookingId <= 0) {
            return null;
        }

        return bookingDAO.findBooking(bookingId);
    }

    @Override
    public boolean changeBooking(Booking booking) {
        if (booking == null ||
                booking.getBookingId() <= 0) {
            return false;
        }

        return bookingDAO.changeBooking(booking);
    }

    @Override
    public List<Booking> getBookingsByUser(int userId) {
        if (userId <= 0) {
            return List.of();
        }

        return bookingDAO.getBookingsByUser(userId);
    }

    @Override
    public boolean bookTicket(
            Booking booking,
            List<Seat> selectedSeats) {

        if (booking == null ||
                booking.getShow() == null ||
                booking.getShow().getShowId() <= 0 ||
                booking.getUser() == null ||
                booking.getUser().getUserId() <= 0 ||
                selectedSeats == null ||
                selectedSeats.isEmpty() ||
                booking.getTotalAmount() == null ||
                booking.getTotalAmount().signum() <= 0) {
            return false;
        }

        int showId = booking.getShow().getShowId();

        List<Seat> availableSeats =
                seatService.getAvailableSeats(showId);

        Set<Integer> availableSeatIds = new HashSet<>();

        for (Seat seat : availableSeats) {
            availableSeatIds.add(seat.getSeatId());
        }

        for (Seat seat : selectedSeats) {
            if (seat == null ||
                    !availableSeatIds.contains(seat.getSeatId())) {
                return false;
            }
        }

        if (booking.getBookingDate() == null) {
            booking.setBookingDate(LocalDateTime.now());
        }

        boolean bookingCreated =
                bookingDAO.saveBooking(booking);

        if (!bookingCreated) {
            return false;
        }

        for (Seat seat : selectedSeats) {
            BookedSeat bookedSeat = new BookedSeat();

            bookedSeat.setSeat(seat);
            bookedSeat.setBooking(booking);

            boolean seatStored =
                    bookedSeatService.storeBookedSeat(bookedSeat);

            if (!seatStored) {
                return false;
            }
        }

        return true;
    }
}