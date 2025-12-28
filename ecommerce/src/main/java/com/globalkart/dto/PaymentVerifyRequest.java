package com.globalkart.dto;

import lombok.Data;

@Data
public class PaymentVerifyRequest {
    private String razorpayPaymentId;
    private String razorpayOrderId;
    private String razorpaySignature;
}