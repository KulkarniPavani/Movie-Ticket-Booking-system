package org.example.dao;

import org.example.model.Booking;
import org.example.model.Show;
import org.example.model.User;
import org.example.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class BookingDAOImpl implements BookingDAO {

    @Override
    public boolean saveBooking(Booking booking) {
        String sql = "INSERT INTO bookings (user_id, show_id, total_amount, booking_status, booking_date) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, booking.getUser() != null ? booking.getUser().getUserId() : 0);
            pstmt.setInt(2, booking.getShow() != null ? booking.getShow().getShowId() : 0);
            pstmt.setBigDecimal(3, booking.getTotalAmount());
            pstmt.setString(4, booking.getBookingStatus() != null ? booking.getBookingStatus() : "CONFIRMED");

            if (booking.getBookingDate() != null) {
                pstmt.setTimestamp(5, Timestamp.valueOf(booking.getBookingDate()));
            } else {
                pstmt.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
            }

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean saveBookingWithSeats(Booking booking, List<String> selectedSeats) {
        String sql = "INSERT INTO bookings (user_id, show_id, total_amount, booking_status, booking_date, seats) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, booking.getUser() != null ? booking.getUser().getUserId() : 0);
            pstmt.setInt(2, booking.getShow() != null ? booking.getShow().getShowId() : 0);
            pstmt.setBigDecimal(3, booking.getTotalAmount());
            pstmt.setString(4, booking.getBookingStatus() != null ? booking.getBookingStatus() : "CONFIRMED");

            if (booking.getBookingDate() != null) {
                pstmt.setTimestamp(5, Timestamp.valueOf(booking.getBookingDate()));
            } else {
                pstmt.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
            }

            pstmt.setString(6, selectedSeats != null ? String.join(",", selectedSeats) : "");

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Booking findBooking(int bookingId) {
        String sql = "SELECT * FROM bookings WHERE booking_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, bookingId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToBooking(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public boolean changeBooking(Booking booking) {
        String sql = "UPDATE bookings SET user_id = ?, show_id = ?, total_amount = ?, booking_status = ?, booking_date = ? WHERE booking_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, booking.getUser() != null ? booking.getUser().getUserId() : 0);
            pstmt.setInt(2, booking.getShow() != null ? booking.getShow().getShowId() : 0);
            pstmt.setBigDecimal(3, booking.getTotalAmount());
            pstmt.setString(4, booking.getBookingStatus());

            if (booking.getBookingDate() != null) {
                pstmt.setTimestamp(5, Timestamp.valueOf(booking.getBookingDate()));
            } else {
                pstmt.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
            }

            pstmt.setInt(6, booking.getBookingId());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Booking> getBookingsByUser(int userId) {
        List<Booking> bookings = new ArrayList<>();
        String sql = "SELECT * FROM bookings WHERE user_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    bookings.add(mapResultSetToBooking(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return bookings;
    }

    @Override
    public List<String> getBookedSeatsForShow(int showId) {
        List<String> bookedSeats = new ArrayList<>();
        String sql = "SELECT seats FROM bookings WHERE show_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, showId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String seatsRaw = rs.getString("seats");
                    if (seatsRaw != null && !seatsRaw.trim().isEmpty()) {
                        String[] seatsArr = seatsRaw.split(",");
                        for (String seat : seatsArr) {
                            bookedSeats.add(seat.trim().toUpperCase());
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return bookedSeats;
    }

    private Booking mapResultSetToBooking(ResultSet rs) throws SQLException {
        Booking booking = new Booking();
        booking.setBookingId(rs.getInt("booking_id"));

        User user = new User();
        user.setUserId(rs.getInt("user_id"));
        booking.setUser(user);

        Show show = new Show();
        show.setShowId(rs.getInt("show_id"));
        booking.setShow(show);

        booking.setTotalAmount(rs.getBigDecimal("total_amount"));
        booking.setBookingStatus(rs.getString("booking_status"));

        Timestamp timestamp = rs.getTimestamp("booking_date");
        if (timestamp != null) {
            booking.setBookingDate(timestamp.toLocalDateTime());
        }

        return booking;
    }
}