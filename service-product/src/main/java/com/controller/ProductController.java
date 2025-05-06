package com.controller;

import com.domain.TestProduct;
import com.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

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
