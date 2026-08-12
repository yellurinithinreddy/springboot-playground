package com.nithin.inventory_service.service;

import com.nithin.inventory_service.dto.OrderRequestDto;
import com.nithin.inventory_service.dto.OrderRequestItemDto;
import com.nithin.inventory_service.dto.ProductDto;
import com.nithin.inventory_service.entity.Product;
import com.nithin.inventory_service.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;


    public List<ProductDto> getProducts() {
        log.info("Fetching all inventory items");
        return productRepository.findAll()
                .stream()
                .map(product -> modelMapper.map(product, ProductDto.class))
                .toList();
    }

    public ProductDto getProductById(Long productId) {
        log.info("Fetching Product with ID: {}",productId);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("product not found with Id: "+productId));
        return modelMapper.map(product,ProductDto.class);
    }

    @Transactional
    public Double reduceStocks(OrderRequestDto orderRequestDto) {
        Double totalPrice = 0.0;
        for(OrderRequestItemDto orderRequestItemDto:orderRequestDto.getItems()){
            Long productId = orderRequestItemDto.getProductId();
            Integer quantity = orderRequestItemDto.getQuantity();

            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new RuntimeException("No product found"));

            if(product.getStock() < quantity){
                throw new RuntimeException("No enough stock");
            }
            product.setStock(product.getStock()-quantity);
            totalPrice += product.getPrice()*quantity;
            productRepository.save(product);
        }

        return totalPrice;
    }


    @Transactional
    public Void addStock(OrderRequestDto orderRequestDto) {

        List<OrderRequestItemDto> items = orderRequestDto.getItems();

        for(OrderRequestItemDto orderRequestItemDto:items){
            Long productId = orderRequestItemDto.getProductId();
            Integer quantity = orderRequestItemDto.getQuantity();
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new RuntimeException("product not found"));
            product.setStock(product.getStock()+quantity);
            productRepository.save(product);
        }

        return null;
    }
}
