package com.globalkart.Controller;

import com.globalkart.Service.CartService;
import com.globalkart.model.Cart;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService){
        this.cartService = cartService;
    }

    @PostMapping("/add")
    public Cart addToCart(@RequestParam Long userId,
                          @RequestParam Long productId,
                          @RequestParam Integer quantity) {
        return cartService.addToCart(userId, productId, quantity);
    }

    @DeleteMapping("/remove")
    public Cart removeItem(@RequestParam Long userId,
                           @RequestParam Long productId) {
        return cartService.removeItemFromCart(userId, productId);
    }


    @DeleteMapping("/clear")
    public String clearCart(@RequestParam Long userId) {
        cartService.clearCart(userId);
        return "Cart cleared successfully";
    }


}






