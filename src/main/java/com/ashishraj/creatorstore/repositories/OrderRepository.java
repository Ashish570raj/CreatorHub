package com.ashishraj.creatorstore.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ashishraj.creatorstore.entities.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
