package com.globalkart.Repository;

import com.globalkart.model.Order;
import com.globalkart.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> {

    List<Order> findByUser(User user);
}
