package com.globalkart.Controller;


import com.globalkart.Service.PaymentService;
import com.globalkart.dto.PaymentVerifyRequest;
import com.globalkart.model.Payment;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/create")
    public Payment createPayment(@RequestParam Long orderId) throws Exception {
        return paymentService.createPayment(orderId);
    }
    @PostMapping("/verify")
    public String verifyPayment(@RequestBody PaymentVerifyRequest request) {
        return paymentService.verifyPayment(request);
    }


}
