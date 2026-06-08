package com.ashishraj.creatorstore.Services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ashishraj.creatorstore.entities.Product;
import com.ashishraj.creatorstore.repositories.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public Product createProduct(Product product) {
        // logic to create a product
        return productRepository.save(product);
    }   
    
    public Product updateProduct (Long id, Product Product) {
        // logic to update a product by id
        Product existingProduct = productRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Product not found with id " + id));

        existingProduct.setName(Product.getName());
        existingProduct.setDescription(Product.getDescription());
        existingProduct.setCategory(Product.getCategory());
        existingProduct.setPrice(Product.getPrice());
        existingProduct.setStockQuantity(Product.getStockQuantity());

        return productRepository.save(existingProduct);
    }
    
    public List<Product> getAllProducts() {
        // logic to get all products
        return productRepository.findAll();
    }

    public Product getProductById (Long id) {
        // logic to get a product by id
        return productRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Product not found with id " + id));
    }   

    public void deleteProduct (Long id) {
        // logic to delete a product by id
        productRepository.deleteById(id);
    }   
}
