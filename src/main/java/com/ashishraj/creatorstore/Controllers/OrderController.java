package com.ashishraj.creatorstore.Controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ashishraj.creatorstore.Services.OrderService;
import com.ashishraj.creatorstore.dto.OrderRequest;
import com.ashishraj.creatorstore.entities.Order;

import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public Order createOrder(@Valid @RequestBody OrderRequest orderRequest) {
        // logic to create an order
        return orderService.CreateOrder(orderRequest);
    }
    
    // get all orders
    @GetMapping
    public List<Order>getAllOrders() {
        // logic to get all orders
        return orderService.getAllOrders();
    }

    // get order by id
    @GetMapping("/{id}")
    public Order getOrderById(@PathVariable Long id) {
        // logic to get an order by id
        return orderService.getOrderById(id);
    }
    
}
