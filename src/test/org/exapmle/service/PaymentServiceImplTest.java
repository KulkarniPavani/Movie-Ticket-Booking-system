package org.example.service;

import org.example.dao.PaymentDAO;
import org.example.model.Booking;
import org.example.model.Payment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PaymentServiceImplTest {

    private PaymentDAO paymentDAO;
    private PaymentServiceImpl paymentService;

    @BeforeEach
    void setUp() {
        paymentDAO = Mockito.mock(PaymentDAO.class);
        paymentService = new PaymentServiceImpl(paymentDAO);
    }

    @Test
    void testAddPayment() {
        Booking booking = new Booking();
        booking.setBookingId(1);

        Payment payment = new Payment();
        payment.setBooking(booking);
        payment.setAmount(new BigDecimal("400.00"));
        payment.setPaymentMethod("UPI");
        payment.setPaymentStatus("SUCCESS");
        payment.setPaymentDate(LocalDateTime.now());

        when(paymentDAO.addPayment(payment)).thenReturn(true);

        boolean result = paymentService.addPayment(payment);

        assertTrue(result);
        verify(paymentDAO).addPayment(payment);
    }

    @Test
    void testFetchPayment() {
        Payment payment = new Payment();
        payment.setPaymentId(1);

        when(paymentDAO.fetchPayment(1)).thenReturn(payment);

        Payment result = paymentService.fetchPayment(1);

        assertNotNull(result);
        assertEquals(1, result.getPaymentId());
        verify(paymentDAO).fetchPayment(1);
    }

    @Test
    void testEditPayment() {
        Booking booking = new Booking();
        booking.setBookingId(1);

        Payment payment = new Payment();
        payment.setPaymentId(1);
        payment.setBooking(booking);
        payment.setAmount(new BigDecimal("400.00"));
        payment.setPaymentMethod("UPI");
        payment.setPaymentStatus("SUCCESS");
        payment.setPaymentDate(LocalDateTime.now());

        when(paymentDAO.editPayment(payment)).thenReturn(true);

        boolean result = paymentService.editPayment(payment);

        assertTrue(result);
        verify(paymentDAO).editPayment(payment);
    }

    @Test
    void testRemovePayment() {
        when(paymentDAO.removePayment(1)).thenReturn(true);

        boolean result = paymentService.removePayment(1);

        assertTrue(result);
        verify(paymentDAO).removePayment(1);
    }

    @Test
    void testFindPaymentByBookingId() {
        Payment payment = new Payment();
        payment.setPaymentId(1);

        when(paymentDAO.findPaymentByBookingId(1)).thenReturn(payment);

        Payment result = paymentService.findPaymentByBookingId(1);

        assertNotNull(result);
        assertEquals(1, result.getPaymentId());
        verify(paymentDAO).findPaymentByBookingId(1);
    }

    @Test
    void testMakePayment() {
        Booking booking = new Booking();
        booking.setBookingId(1);

        Payment payment = new Payment();
        payment.setBooking(booking);
        payment.setAmount(new BigDecimal("400.00"));
        payment.setPaymentMethod("UPI");

        when(paymentDAO.makePayment(payment)).thenReturn(true);

        boolean result = paymentService.makePayment(payment);

        assertTrue(result);
        verify(paymentDAO).makePayment(payment);
    }
}