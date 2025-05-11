package com.debeziumdemo.serviceproduct.service;

import com.debeziumdemo.serviceproduct.dao.TestProductRepository;
import com.debeziumdemo.serviceproduct.domain.TestProduct;
import jakarta.transaction.Transactional;
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

    @Transactional
    public void decreaseStock(Long productId, int quantity) {
        TestProduct product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found: " + productId));

        int newStock = product.getStock() - quantity;
        if (newStock < 0) {
            throw new IllegalStateException("庫存不足: productId=" + productId);
        }

        product.setStock(newStock);
        productRepository.save(product);
        log.info("扣庫存完成：productId={}, 扣除={}, 剩餘={}", productId, quantity, newStock);
    }
}
