package com.ashishraj.creatorstore.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "orders")
@Getter
@Setter 
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "customer_name",nullable = false)
    private String CustomerName;

    @Column(name = "customer_email",nullable = false)
    private String CustomerEmail;

    @Column(name = "status",nullable = false)
    private String Status;

    @Column(name = "total_price",nullable = false)
    private BigDecimal totalPrice;
    
    @JsonManagedReference
    @OneToMany(mappedBy = "order",cascade=CascadeType.ALL)
    private List<OrderItem> orderItems;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}
