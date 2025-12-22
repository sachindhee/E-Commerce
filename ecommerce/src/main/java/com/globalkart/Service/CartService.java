package com.globalkart.Service;

import com.globalkart.Repository.CartItemRepository;
import com.globalkart.Repository.CartRepository;
import com.globalkart.Repository.ProductRepository;
import com.globalkart.Repository.UserRepository;
import com.globalkart.model.Cart;
import com.globalkart.model.CartItem;
import com.globalkart.model.Product;
import com.globalkart.model.User;
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

        CartItem item = new CartItem();
        item.setCart(cart);
        item.setProduct(product);
        item.setQuantity(quantity);
        item.setPrice(product.getPrice());

        cart.getItems().add(item);
        cartItemRepository.save(item);

        return cartRepository.save(cart);
    }


}
