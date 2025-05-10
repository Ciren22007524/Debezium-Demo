package com.debeziumdemo.serviceproduct.service;

import com.debeziumdemo.serviceproduct.dao.TestProductRepository;
import com.debeziumdemo.serviceproduct.domain.TestProduct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {
    private final TestProductRepository productRepository;

    public List<TestProduct> getProductList() {
        return productRepository.findAll();
    }

    public TestProduct getProduct(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found: " + productId));
    }

    public List<TestProduct> getProductsByIds(List<Long> ids) {
        return productRepository.findAllById(ids);
    }
}
