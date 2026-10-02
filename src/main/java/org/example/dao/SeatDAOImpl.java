package org.example.dao;

import org.example.model.Seat;
import org.example.model.Theatre;
import org.example.util.DBConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SeatDAOImpl implements SeatDAO {

    private static final Logger logger =
            LoggerFactory.getLogger(SeatDAOImpl.class);

    private static final String ADD_SEAT =
            "INSERT INTO seats " +
                    "(theatre_id, seat_number, seat_type, price) " +
                    "VALUES (?, ?, ?, ?)";

    private static final String FETCH_SEAT =
            "SELECT * FROM seats WHERE seat_id = ?";

    private static final String EDIT_SEAT =
            "UPDATE seats SET theatre_id = ?, seat_number = ?, " +
                    "seat_type = ?, price = ? WHERE seat_id = ?";

    private static final String REMOVE_SEAT =
            "DELETE FROM seats WHERE seat_id = ?";

    private static final String GET_AVAILABLE_SEATS =
            "SELECT s.* FROM seats s " +
                    "WHERE s.theatre_id = " +
                    "(SELECT theatre_id FROM shows WHERE show_id = ?) " +
                    "AND NOT EXISTS (" +
                    "SELECT 1 FROM booked_seats bs " +
                    "JOIN bookings b ON bs.booking_id = b.booking_id " +
                    "WHERE bs.seat_id = s.seat_id " +
                    "AND b.show_id = ?" +
                    ")";

    @Override
    public boolean addSeat(Seat seat) {

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(ADD_SEAT)) {

            statement.setInt(
                    1,
                    seat.getTheatre().getTheatreId()
            );

            statement.setString(
                    2,
                    seat.getSeatNumber()
            );

            statement.setString(
                    3,
                    seat.getSeatType()
            );

            statement.setBigDecimal(
                    4,
                    seat.getPrice()
            );

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                logger.info("Seat added successfully");
                return true;
            }

        } catch (SQLException e) {
            logger.error("Error while adding seat", e);
        }

        return false;
    }

    @Override
    public Seat fetchSeat(int seatId) {

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(FETCH_SEAT)) {

            statement.setInt(1, seatId);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {

                Seat seat = new Seat();

                seat.setSeatId(
                        resultSet.getInt("seat_id")
                );

                Theatre theatre = new Theatre();

                theatre.setTheatreId(
                        resultSet.getInt("theatre_id")
                );

                seat.setTheatre(theatre);

                seat.setSeatNumber(
                        resultSet.getString("seat_number")
                );

                seat.setSeatType(
                        resultSet.getString("seat_type")
                );

                seat.setPrice(
                        resultSet.getBigDecimal("price")
                );

                logger.info(
                        "Seat found: seatId={}",
                        seatId
                );

                return seat;
            }

        } catch (SQLException e) {
            logger.error("Error while finding seat", e);
        }

        return null;
    }

    @Override
    public boolean editSeat(Seat seat) {

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(EDIT_SEAT)) {

            statement.setInt(
                    1,
                    seat.getTheatre().getTheatreId()
            );

            statement.setString(
                    2,
                    seat.getSeatNumber()
            );

            statement.setString(
                    3,
                    seat.getSeatType()
            );

            statement.setBigDecimal(
                    4,
                    seat.getPrice()
            );

            statement.setInt(
                    5,
                    seat.getSeatId()
            );

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                logger.info(
                        "Seat updated successfully: seatId={}",
                        seat.getSeatId()
                );
                return true;
            }

        } catch (SQLException e) {
            logger.error("Error while updating seat", e);
        }

        return false;
    }

    @Override
    public boolean removeSeat(int seatId) {

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(REMOVE_SEAT)) {

            statement.setInt(1, seatId);

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                logger.info(
                        "Seat deleted successfully: seatId={}",
                        seatId
                );
                return true;
            }

        } catch (SQLException e) {
            logger.error("Error while deleting seat", e);
        }

        return false;
    }

    @Override
    public List<Seat> getAvailableSeats(int showId) {

        List<Seat> seats = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(
                             GET_AVAILABLE_SEATS)) {

            statement.setInt(1, showId);
            statement.setInt(2, showId);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                Seat seat = new Seat();

                seat.setSeatId(
                        resultSet.getInt("seat_id")
                );

                Theatre theatre = new Theatre();

                theatre.setTheatreId(
                        resultSet.getInt("theatre_id")
                );

                seat.setTheatre(theatre);

                seat.setSeatNumber(
                        resultSet.getString("seat_number")
                );

                seat.setSeatType(
                        resultSet.getString("seat_type")
                );

                seat.setPrice(
                        resultSet.getBigDecimal("price")
                );

                seats.add(seat);
            }

            logger.info(
                    "Available seats fetched for showId={}",
                    showId
            );

        } catch (SQLException e) {
            logger.error(
                    "Error while fetching available seats",
                    e
            );
        }

        return seats;
    }
}