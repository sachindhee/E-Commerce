package com.globalkart.Service;

import com.globalkart.Repository.CartItemRepository;
import com.globalkart.Repository.CartRepository;
import com.globalkart.Repository.ProductRepository;
import com.globalkart.Repository.UserRepository;
import com.globalkart.model.Cart;
import com.globalkart.model.CartItem;
import com.globalkart.model.Product;
import com.globalkart.model.User;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public CartService(CartRepository cartRepository,
                       CartItemRepository cartItemRepository,
                       UserRepository userRepository,
                       ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    public Cart addToCart(Long userId, Long productId, Integer quantity) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Cart cart = cartRepository.findByUser(user)
                .orElseGet(() -> {
                    Cart c = new Cart();
                    c.setUser(user);
                    return cartRepository.save(c);
                });

        // ✅ CHECK: product already in cart?
        for (CartItem item : cart.getItems()) {
            if (item.getProduct().getId().equals(productId)) {
                item.setQuantity(item.getQuantity() + quantity);
                return cartRepository.save(cart);
            }
        }

        // ✅ Else: create new cart item
        CartItem item = new CartItem();
        item.setCart(cart);
        item.setProduct(product);
        item.setQuantity(quantity);
        item.setPrice(product.getPrice());

        cart.getItems().add(item);
        return cartRepository.save(cart);
    }


    @Transactional
    public Cart removeItemFromCart(Long userId, Long productId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        // product match hone wala item remove karo
        cart.getItems().removeIf(
                item -> item.getProduct().getId().equals(productId)
        );

        return cartRepository.save(cart);

    }

    @Transactional
    public void clearCart(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        cart.getItems().clear();
        cartRepository.save(cart);
    }
}