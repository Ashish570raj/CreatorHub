package com.ashishraj.creatorstore.repositories;

import com.ashishraj.creatorstore.entities.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OrderItemsRepository  extends JpaRepository<OrderItem, Long> {

}
