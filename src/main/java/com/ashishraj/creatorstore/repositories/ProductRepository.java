package com.ashishraj.creatorstore.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ashishraj.creatorstore.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
