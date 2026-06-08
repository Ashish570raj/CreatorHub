package com.ashishraj.creatorstore.Controllers;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ashishraj.creatorstore.Services.ProductService;
import com.ashishraj.creatorstore.entities.Product;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;



@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductControllers {
    
    private final ProductService productService;


    @PostMapping
    public Product createProduct(@Valid @RequestBody Product product) {
        // logic to create a product
        return productService.createProduct(product);
    }   
    
    @PutMapping("/{id}")
    public Product updateProduct (@PathVariable Long id, @Valid @RequestBody Product updatedProduct) {
        // logic to update a product by id
        return productService.updateProduct(id, updatedProduct);
    }


    // public String getMethodName(@RequestParam String param) {
    //     return new String();
    // }
    
    @GetMapping
    public List<Product> getAllProducts() {
        // logic to get all products
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public Product getProductById (@PathVariable Long id) {
        // logic to get a product by id
        return productService.getProductById(id);
    }   

    @DeleteMapping("/{id}") 
    public void deleteProduct (@PathVariable Long id) {
        // logic to delete a product by id
        productService.deleteProduct(id);
    }   
     
}
