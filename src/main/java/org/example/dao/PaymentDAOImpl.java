package org.example.dao;

import org.example.model.Booking;
import org.example.model.Payment;
import org.example.util.DBConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PaymentDAOImpl implements PaymentDAO {

    private static final Logger logger =
            LoggerFactory.getLogger(PaymentDAOImpl.class);

    private static final String ADD_PAYMENT =
            "INSERT INTO payments " +
                    "(booking_id, amount, payment_method, payment_status, payment_date) " +
                    "VALUES (?, ?, ?, ?, ?)";

    private static final String FETCH_PAYMENT =
            "SELECT * FROM payments WHERE payment_id = ?";

    private static final String EDIT_PAYMENT =
            "UPDATE payments SET booking_id = ?, amount = ?, " +
                    "payment_method = ?, payment_status = ?, payment_date = ? " +
                    "WHERE payment_id = ?";

    private static final String REMOVE_PAYMENT =
            "DELETE FROM payments WHERE payment_id = ?";

    private static final String FIND_PAYMENT_BY_BOOKING_ID =
            "SELECT * FROM payments WHERE booking_id = ?";

    @Override
    public boolean addPayment(Payment payment) {

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(ADD_PAYMENT)) {

            statement.setInt(
                    1,
                    payment.getBooking().getBookingId()
            );

            statement.setBigDecimal(
                    2,
                    payment.getAmount()
            );

            statement.setString(
                    3,
                    payment.getPaymentMethod()
            );

            statement.setString(
                    4,
                    payment.getPaymentStatus()
            );

            statement.setTimestamp(
                    5,
                    java.sql.Timestamp.valueOf(
                            payment.getPaymentDate()
                    )
            );

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                logger.info("Payment added successfully");
                return true;
            }

        } catch (SQLException e) {
            logger.error(
                    "Error while adding payment",
                    e
            );
        }

        return false;
    }

    @Override
    public Payment fetchPayment(int paymentId) {

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(FETCH_PAYMENT)) {

            statement.setInt(1, paymentId);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {

                Payment payment = new Payment();

                payment.setPaymentId(
                        resultSet.getInt("payment_id")
                );

                Booking booking = new Booking();

                booking.setBookingId(
                        resultSet.getInt("booking_id")
                );

                payment.setBooking(booking);

                payment.setAmount(
                        resultSet.getBigDecimal("amount")
                );

                payment.setPaymentMethod(
                        resultSet.getString("payment_method")
                );

                payment.setPaymentStatus(
                        resultSet.getString("payment_status")
                );

                java.sql.Timestamp paymentDate =
                        resultSet.getTimestamp("payment_date");

                if (paymentDate != null) {
                    payment.setPaymentDate(
                            paymentDate.toLocalDateTime()
                    );
                }

                logger.info(
                        "Payment found: paymentId={}",
                        paymentId
                );

                return payment;
            }

        } catch (SQLException e) {
            logger.error(
                    "Error while finding payment",
                    e
            );
        }

        return null;
    }

    @Override
    public boolean editPayment(Payment payment) {

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(EDIT_PAYMENT)) {

            statement.setInt(
                    1,
                    payment.getBooking().getBookingId()
            );

            statement.setBigDecimal(
                    2,
                    payment.getAmount()
            );

            statement.setString(
                    3,
                    payment.getPaymentMethod()
            );

            statement.setString(
                    4,
                    payment.getPaymentStatus()
            );

            statement.setTimestamp(
                    5,
                    java.sql.Timestamp.valueOf(
                            payment.getPaymentDate()
                    )
            );

            statement.setInt(
                    6,
                    payment.getPaymentId()
            );

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                logger.info(
                        "Payment updated successfully: paymentId={}",
                        payment.getPaymentId()
                );
                return true;
            }

        } catch (SQLException e) {
            logger.error(
                    "Error while updating payment",
                    e
            );
        }

        return false;
    }

    @Override
    public boolean removePayment(int paymentId) {

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(REMOVE_PAYMENT)) {

            statement.setInt(1, paymentId);

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                logger.info(
                        "Payment deleted successfully: paymentId={}",
                        paymentId
                );
                return true;
            }

        } catch (SQLException e) {
            logger.error(
                    "Error while deleting payment",
                    e
            );
        }

        return false;
    }

    @Override
    public Payment findPaymentByBookingId(int bookingId) {

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(
                             FIND_PAYMENT_BY_BOOKING_ID)) {

            statement.setInt(1, bookingId);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {

                Payment payment = new Payment();

                payment.setPaymentId(
                        resultSet.getInt("payment_id")
                );

                Booking booking = new Booking();

                booking.setBookingId(
                        resultSet.getInt("booking_id")
                );

                payment.setBooking(booking);

                payment.setAmount(
                        resultSet.getBigDecimal("amount")
                );

                payment.setPaymentMethod(
                        resultSet.getString("payment_method")
                );

                payment.setPaymentStatus(
                        resultSet.getString("payment_status")
                );

                java.sql.Timestamp paymentDate =
                        resultSet.getTimestamp("payment_date");

                if (paymentDate != null) {
                    payment.setPaymentDate(
                            paymentDate.toLocalDateTime()
                    );
                }

                logger.info(
                        "Payment found for bookingId={}",
                        bookingId
                );

                return payment;
            }

        } catch (SQLException e) {
            logger.error(
                    "Error while finding payment by booking",
                    e
            );
        }

        return null;
    }

    @Override
    public boolean makePayment(Payment payment) {

        return addPayment(payment);
    }
}