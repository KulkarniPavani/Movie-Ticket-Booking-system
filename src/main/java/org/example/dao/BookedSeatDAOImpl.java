package org.example.dao;

import org.example.model.BookedSeat;
import org.example.model.Booking;
import org.example.model.Seat;
import org.example.util.DBConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BookedSeatDAOImpl implements BookedSeatDAO {

    private static final Logger logger =
            LoggerFactory.getLogger(BookedSeatDAOImpl.class);

    private static final String STORE_BOOKED_SEAT =
            "INSERT INTO booked_seats (seat_id, booking_id) VALUES (?, ?)";

    private static final String READ_BOOKED_SEAT =
            "SELECT * FROM booked_seats WHERE booked_seat_id = ?";

    private static final String MODIFY_BOOKED_SEAT =
            "UPDATE booked_seats SET seat_id = ?, booking_id = ? " +
                    "WHERE booked_seat_id = ?";

    private static final String REMOVE_BOOKED_SEAT =
            "DELETE FROM booked_seats WHERE booked_seat_id = ?";


    @Override
    public boolean storeBookedSeat(BookedSeat bookedSeat) {

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(STORE_BOOKED_SEAT)) {

            statement.setInt(
                    1,
                    bookedSeat.getSeat().getSeatId()
            );

            statement.setInt(
                    2,
                    bookedSeat.getBooking().getBookingId()
            );

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                logger.info("Booked seat added successfully");
                return true;
            }

        } catch (SQLException e) {
            logger.error("Error while adding booked seat", e);
        }

        return false;
    }


    @Override
    public BookedSeat readBookedSeat(int bookedSeatId) {

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(READ_BOOKED_SEAT)) {

            statement.setInt(1, bookedSeatId);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {

                BookedSeat bookedSeat = new BookedSeat();

                bookedSeat.setBookedSeatId(
                        resultSet.getInt("booked_seat_id")
                );

                Seat seat = new Seat();
                seat.setSeatId(
                        resultSet.getInt("seat_id")
                );
                bookedSeat.setSeat(seat);

                Booking booking = new Booking();
                booking.setBookingId(
                        resultSet.getInt("booking_id")
                );
                bookedSeat.setBooking(booking);

                logger.info(
                        "Booked seat found: bookedSeatId={}",
                        bookedSeatId
                );

                return bookedSeat;
            }

        } catch (SQLException e) {
            logger.error("Error while finding booked seat", e);
        }

        return null;
    }


    @Override
    public boolean modifyBookedSeat(BookedSeat bookedSeat) {

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(MODIFY_BOOKED_SEAT)) {

            statement.setInt(
                    1,
                    bookedSeat.getSeat().getSeatId()
            );

            statement.setInt(
                    2,
                    bookedSeat.getBooking().getBookingId()
            );

            statement.setInt(
                    3,
                    bookedSeat.getBookedSeatId()
            );

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                logger.info(
                        "Booked seat updated successfully: bookedSeatId={}",
                        bookedSeat.getBookedSeatId()
                );
                return true;
            }

        } catch (SQLException e) {
            logger.error("Error while updating booked seat", e);
        }

        return false;
    }


    @Override
    public boolean removeBookedSeat(int bookedSeatId) {

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(REMOVE_BOOKED_SEAT)) {

            statement.setInt(1, bookedSeatId);

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                logger.info(
                        "Booked seat deleted successfully: bookedSeatId={}",
                        bookedSeatId
                );
                return true;
            }

        } catch (SQLException e) {
            logger.error("Error while deleting booked seat", e);
        }

        return false;
    }
}