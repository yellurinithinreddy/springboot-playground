package com.nithin.SpringDataJPADemo.JPATutorial.controllers;

import com.nithin.SpringDataJPADemo.JPATutorial.entities.ProductEntity;
import com.nithin.SpringDataJPADemo.JPATutorial.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductRepository productRepository;
    private final int PAGE_SIZE = 5;

    @GetMapping
    public Page<ProductEntity> getAllProducts(
            @RequestParam(defaultValue = "0") Integer pageNumber,
            @RequestParam(defaultValue = "id") String sortBy){
//        return productRepository.findBy(Sort.by(Sort.Direction.DESC,sortBy));// by default ASC,
//        return productRepository.findBy(Sort.by(
//                Sort.Order.asc(sortBy),
//                Sort.Order.desc("price")
//                )
//        );
//        return productRepository.findByOrderByPriceDesc();// here we are tightly coupled with method names so we can use Sort to pass the sort column dynamically
        Pageable pageable = PageRequest.of(pageNumber,PAGE_SIZE);
        return productRepository.findBy(pageable);

    }
}
