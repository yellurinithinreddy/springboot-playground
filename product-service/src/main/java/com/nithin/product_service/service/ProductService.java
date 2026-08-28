package com.nithin.product_service.service;

import com.nithin.product_service.entity.Product;
import com.nithin.product_service.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;

    public Product create(Product product) {
        log.info("Creating a product with details: "+product.toString());
        return productRepository.save(product);
    }


    public List<Product> getAllProducts() {
        log.info("Retrieving all products");
        return productRepository.findAll();
    }
}
