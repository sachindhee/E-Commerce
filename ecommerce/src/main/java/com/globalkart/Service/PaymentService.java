package com.globalkart.Service;

import com.globalkart.Repository.OrderRepository;
import com.globalkart.Repository.PaymentRepository;
import com.globalkart.model.Order;

import com.globalkart.model.Payment;
import com.razorpay.RazorpayClient;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PaymentService {

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
}
