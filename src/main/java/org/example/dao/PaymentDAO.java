package org.example.dao;

import org.example.model.Payment;

public interface PaymentDAO {

    boolean addPayment(Payment payment);

    Payment fetchPayment(int paymentId);

    boolean editPayment(Payment payment);

    boolean removePayment(int paymentId);

    Payment findPaymentByBookingId(int bookingId);

    boolean makePayment(Payment payment);
}