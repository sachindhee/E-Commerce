package com.globalkart.Controller;

import com.globalkart.Service.CartService;
import com.globalkart.model.Cart;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/Cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService){
        this.cartService=cartService;
    }

    @PostMapping("/add/{userId}/{productId}/{quantity}")
    public Cart addToCart(@RequestParam Long userId,
                          @RequestParam Long productId,
                          @RequestParam Integer quantity) {

        return cartService.addToCart(userId, productId, quantity);
    }




}
