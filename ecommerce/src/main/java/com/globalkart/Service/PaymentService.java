package com.globalkart.Service;

import com.globalkart.Repository.OrderRepository;
import com.globalkart.Repository.PaymentRepository;
import com.globalkart.dto.PaymentVerifyRequest;
import com.globalkart.model.Order;

import com.globalkart.model.Payment;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.razorpay.Utils;
import jakarta.transaction.Transactional;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PaymentService {

    @Value("${razorpay.key.secret}")
    private String razorpayKeySecret;

    private final RazorpayClient razorpayClient;
    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;

    public PaymentService(RazorpayClient razorpayClient,
                          OrderRepository orderRepository,
                          PaymentRepository paymentRepository) {
        this.razorpayClient = razorpayClient;
        this.orderRepository = orderRepository;
        this.paymentRepository = paymentRepository;
    }

    public Payment createPayment(Long orderId) throws Exception {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        JSONObject options = new JSONObject();
        options.put("amount", order.getTotalAmount().multiply(new BigDecimal(100))); // paise
        options.put("currency", "INR");
        options.put("receipt", "order_rcpt_" + orderId);

        com.razorpay.Order razorpayOrder = razorpayClient.orders.create(options);

        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setAmount(order.getTotalAmount());
        payment.setRazorpayOrderId(razorpayOrder.get("id"));
        payment.setStatus("CREATED");

        return paymentRepository.save(payment);
    }



    @Transactional

    public String verifyPayment(PaymentVerifyRequest request) {

        Payment payment = paymentRepository
                .findByRazorpayOrderId(request.getRazorpayOrderId())
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        try {
            JSONObject options = new JSONObject();
            options.put("razorpay_order_id", request.getRazorpayOrderId());
            options.put("razorpay_payment_id", request.getRazorpayPaymentId());
            options.put("razorpay_signature", request.getRazorpaySignature());

            boolean isValid = Utils.verifyPaymentSignature(
                    options,
                    razorpayKeySecret
            );

            if (!isValid) {
                payment.setStatus("FAILED");
                paymentRepository.save(payment);
                throw new RuntimeException("Payment verification failed");
            }

            // ✅ SUCCESS
            payment.setStatus("SUCCESS");
            paymentRepository.save(payment);

            Order order = payment.getOrder();
            order.setStatus("PAID");
            orderRepository.save(order);

            return "Payment verified successfully";

        } catch (RazorpayException e) {
            payment.setStatus("FAILED");
            paymentRepository.save(payment);
            throw new RuntimeException("Razorpay signature verification error", e);
        }
    }



}
