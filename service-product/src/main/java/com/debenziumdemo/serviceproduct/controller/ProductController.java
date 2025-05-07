package com.debenziumdemo.serviceproduct.controller;

import com.debenziumdemo.serviceproduct.domain.TestProduct;
import com.debenziumdemo.serviceproduct.service.ProductService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostConstruct
    public void init() {
        System.out.println(">>> ProductController loaded");
    }

    @GetMapping
    public List<TestProduct> getAllProduct() {
        return productService.getProductList();
    }

    @GetMapping("/{id}")
    public TestProduct getProduct(@PathVariable Long id) {
        return productService.getProduct(id);
    }

    @PostMapping("/batch")
    public List<TestProduct> getProducts(@RequestBody List<Long> ids) {
        return productService.getProductsByIds(ids);
    }
}
