package org.example.controller;

import org.example.model.Payment;
import org.example.service.PaymentService;
import org.example.service.PaymentServiceImpl;

public class PaymentController {

    private final PaymentService paymentService =
            new PaymentServiceImpl();

    public boolean addPayment(Payment payment) {
        return paymentService.addPayment(payment);
    }

    public Payment fetchPayment(int paymentId) {
        return paymentService.fetchPayment(paymentId);
    }

    public boolean editPayment(Payment payment) {
        return paymentService.editPayment(payment);
    }

    public boolean removePayment(int paymentId) {
        return paymentService.removePayment(paymentId);
    }

    public Payment findPaymentByBookingId(int bookingId) {
        return paymentService.findPaymentByBookingId(bookingId);
    }

    public boolean makePayment(Payment payment) {
        return paymentService.makePayment(payment);
    }
}