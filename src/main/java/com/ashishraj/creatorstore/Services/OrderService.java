package com.ashishraj.creatorstore.Services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ashishraj.creatorstore.dto.OrderItemRequest;
import com.ashishraj.creatorstore.dto.OrderRequest;
import com.ashishraj.creatorstore.entities.Order;
import com.ashishraj.creatorstore.entities.OrderItem;
import com.ashishraj.creatorstore.entities.Product;
import com.ashishraj.creatorstore.repositories.OrderRepository;
import com.ashishraj.creatorstore.repositories.ProductRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class OrderService {
    

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    @Transactional
    public Order CreateOrder(OrderRequest orderRequest) {
        // logic to create an order

        List<OrderItem> orderItems = new ArrayList<>();
        BigDecimal totalPrice = BigDecimal.ZERO;
        Order order = new Order();
        order.setCustomerName(orderRequest.getCustomerName());
        order.setCustomerEmail(orderRequest.getCustomerEmail());
        order.setStatus("CONFIRMED");

        for (OrderItemRequest itemRequest : orderRequest.getItems()) {
           Product product = productRepository.findById(itemRequest.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found with id " + itemRequest.getProductId()));
           
            // check the product stock
            if (product.getStockQuantity() < itemRequest.getQuantity()) {
                throw new RuntimeException("Insufficient stock for product id " + itemRequest.getProductId());   
            }
            
            //calculate total price
            BigDecimal itemTotal = product.getPrice().multiply(BigDecimal.valueOf(itemRequest.getQuantity()));
            totalPrice = totalPrice.add(itemTotal);

            // update the product table with latest stock quantity
            product.setStockQuantity(product.getStockQuantity() - itemRequest.getQuantity());
            productRepository.save(product);

            // Builder pattern to create order item
            OrderItem orderItem=OrderItem.builder()
                    .product(product)
                    .quantity(itemRequest.getQuantity())
                    .price(product.getPrice())
                    .order(order)
                    .build();
            
            orderItems.add(orderItem);
        }
        order.setOrderItems(orderItems);
        order.setTotalPrice(totalPrice);

        return orderRepository.save(order);
    }

    public List<Order> getAllOrders() {
        // logic to get all orders
        return orderRepository.findAll();
    }

    public Order getOrderById(Long id) {
        // logic to get an order by id
        return orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found with id " + id));
    }
}
