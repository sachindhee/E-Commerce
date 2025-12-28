package com.globalkart.Repository;

import com.globalkart.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment,Long> {
    Optional<Payment> findByRazorpayOrderId(String razorpayOrderId);
}
