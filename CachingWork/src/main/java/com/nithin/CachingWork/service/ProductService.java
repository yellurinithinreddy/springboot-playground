package com.nithin.CachingWork.service;

import com.nithin.CachingWork.entity.Product;
import com.nithin.CachingWork.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    @CachePut(cacheNames = "products",key = "#result.id")
    public Product create(Product product) {
        List<Product> list = productRepository.findByProductName(product.getProductName());

        if(!list.isEmpty()) throw new RuntimeException("Duplicate Product Names");
        return productRepository.save(product);
    }


    @Cacheable(cacheNames = "products",key = "#productId")
    public Product getProduct(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

    }

    @CachePut(cacheNames = "products",key = "#productId")
    public Product updateProduct(Long productId, Product product) {
        Product savedProduct = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        product.setId(savedProduct.getId());
        modelMapper.map(product,savedProduct);
        return productRepository.save(savedProduct);
    }
}
