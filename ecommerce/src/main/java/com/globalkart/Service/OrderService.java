package com.globalkart.Service;

import com.globalkart.Repository.CartRepository;
import com.globalkart.Repository.OrderRepository;
import com.globalkart.Repository.UserRepository;
import com.globalkart.dto.OrderItemResponseDTO;
import com.globalkart.dto.OrderResponseDTO;
import com.globalkart.model.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;

    public OrderService(UserRepository userRepository,
                        CartRepository cartRepository,
                        OrderRepository orderRepository) {
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
        this.orderRepository = orderRepository;
    }



    public Order placeOrder(Long userId){
        User user = userRepository.findById(userId).orElseThrow(()-> new RuntimeException("User not found"));
        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        if (cart.getItems().isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }


        Order order = new Order();
        order.setUser(user);
        order.setStatus("PLACED");

        List<OrderItem> orderItems = new ArrayList<>();
        BigDecimal totalAmount = BigDecimal.ZERO;

        for (CartItem cartItem : cart.getItems()) {
            OrderItem item = new OrderItem();
            item.setOrder(order);
            item.setProduct(cartItem.getProduct());
            item.setQuantity(cartItem.getQuantity());
            item.setPrice(cartItem.getPrice());

            orderItems.add(item);

            totalAmount = totalAmount.add(
                    cartItem.getPrice()
                            .multiply(BigDecimal.valueOf(cartItem.getQuantity()))
            );
        }

        order.setItems(orderItems);
        order.setTotalAmount(totalAmount);

        Order savedOrder = orderRepository.save(order);

        // ✅ Clear cart after order
        cart.getItems().clear();
        cartRepository.save(cart);

        return savedOrder;
    }

    public List<OrderResponseDTO> getOrderHistory(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Order> orders = orderRepository.findByUser(user);

        List<OrderResponseDTO> response = new ArrayList<>();

        for (Order order : orders) {

            OrderResponseDTO dto = new OrderResponseDTO();
            dto.setOrderId(order.getId());
            dto.setOrderDate(order.getCreatedAt());
            dto.setStatus(order.getStatus());
            dto.setTotalAmount(order.getTotalAmount());

            List<OrderItemResponseDTO> itemDTOs = new ArrayList<>();

            for (OrderItem item : order.getItems()) {
                OrderItemResponseDTO itemDTO = new OrderItemResponseDTO();
                itemDTO.setProductId(item.getProduct().getId());
                itemDTO.setProductName(item.getProduct().getName());
                itemDTO.setQuantity(item.getQuantity());
                itemDTO.setPrice(item.getPrice());
                itemDTOs.add(itemDTO);
            }

            dto.setItems(itemDTOs);
            response.add(dto);
        }

        return response;
    }



}
