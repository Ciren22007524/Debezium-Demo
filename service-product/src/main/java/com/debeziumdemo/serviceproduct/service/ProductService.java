package com.debeziumdemo.serviceproduct.service;

import com.debeziumdemo.serviceproduct.domain.model.ProductStatus;
import com.debeziumdemo.serviceproduct.domain.repository.TestProductRepository;
import com.debeziumdemo.serviceproduct.domain.model.TestProduct;
import com.debeziumdemo.serviceproduct.domain.state.ProductState;
import com.debeziumdemo.serviceproduct.domain.state.ProductStateFactory;
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
    private final ProductStateFactory stateFactory;

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
    public void decreaseStock(Long productId, int qty) {

        TestProduct product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        ProductState state = stateFactory.of(product.getStatus());
        ProductStatus next = state.decreaseStock(product, qty);
        product.setStatus(next);

        productRepository.save(product);
        log.info("扣庫存完成：productId={}, qty={}, remain={}, status={}",
                productId, qty, product.getStock(), next);
    }
}
