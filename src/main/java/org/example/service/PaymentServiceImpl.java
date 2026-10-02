package org.example.service;

import org.example.dao.PaymentDAO;
import org.example.dao.PaymentDAOImpl;
import org.example.model.Payment;

public class PaymentServiceImpl implements PaymentService {

    private final PaymentDAO paymentDAO;

    public PaymentServiceImpl() {
        this.paymentDAO = new PaymentDAOImpl();
    }

    public PaymentServiceImpl(PaymentDAO paymentDAO) {
        this.paymentDAO = paymentDAO;
    }

    @Override
    public boolean addPayment(Payment payment) {

        if (payment == null ||
                payment.getBooking() == null ||
                payment.getBooking().getBookingId() <= 0 ||
                payment.getAmount() == null ||
                payment.getAmount().signum() < 0 ||
                payment.getPaymentMethod() == null ||
                payment.getPaymentMethod().isBlank() ||
                payment.getPaymentStatus() == null ||
                payment.getPaymentStatus().isBlank() ||
                payment.getPaymentDate() == null) {

            return false;
        }

        return paymentDAO.addPayment(payment);
    }

    @Override
    public Payment fetchPayment(int paymentId) {

        if (paymentId <= 0) {
            return null;
        }

        return paymentDAO.fetchPayment(paymentId);
    }

    @Override
    public boolean editPayment(Payment payment) {

        if (payment == null ||
                payment.getPaymentId() <= 0 ||
                payment.getBooking() == null ||
                payment.getBooking().getBookingId() <= 0 ||
                payment.getAmount() == null ||
                payment.getAmount().signum() < 0 ||
                payment.getPaymentMethod() == null ||
                payment.getPaymentMethod().isBlank() ||
                payment.getPaymentStatus() == null ||
                payment.getPaymentStatus().isBlank() ||
                payment.getPaymentDate() == null) {

            return false;
        }

        return paymentDAO.editPayment(payment);
    }

    @Override
    public boolean removePayment(int paymentId) {

        if (paymentId <= 0) {
            return false;
        }

        return paymentDAO.removePayment(paymentId);
    }

    @Override
    public Payment findPaymentByBookingId(int bookingId) {

        if (bookingId <= 0) {
            return null;
        }

        return paymentDAO.findPaymentByBookingId(bookingId);
    }

    @Override
    public boolean makePayment(Payment payment) {

        if (payment == null ||
                payment.getBooking() == null ||
                payment.getBooking().getBookingId() <= 0 ||
                payment.getAmount() == null ||
                payment.getAmount().signum() <= 0 ||
                payment.getPaymentMethod() == null ||
                payment.getPaymentMethod().isBlank()) {

            return false;
        }

        return paymentDAO.makePayment(payment);
    }
}