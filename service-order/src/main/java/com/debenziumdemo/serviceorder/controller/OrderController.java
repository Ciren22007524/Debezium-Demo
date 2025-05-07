package com.debenziumdemo.serviceorder.controller;

import com.debenziumdemo.serviceorder.domain.OrderItem;
import com.debenziumdemo.serviceorder.domain.TestOrder;
import com.debenziumdemo.serviceorder.dto.ProductOrderRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.debenziumdemo.serviceorder.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    // 取得所有訂單
    @GetMapping
    public List<TestOrder> getOrderList() {
        return orderService.getOrderList();
    }

    // 取得指定訂單資訊
    @GetMapping("/{orderId}")
    public TestOrder getOrder(@PathVariable Long orderId) {
        return orderService.getOrder(orderId);
    }

    // 取得訂單的所有項目
    @GetMapping("/{orderId}/items")
    public List<OrderItem> getOrderItems(@PathVariable Long orderId) {
        return orderService.getOrderItemListFromOrder(orderId);
    }

    // 根據商品查詢它被哪些訂單用到
    @GetMapping("/product/{productId}/items")
    public List<OrderItem> getOrderItemsByProduct(@PathVariable Long productId) {
        return orderService.getOrderItemListFromProduct(productId);
    }

    // 建立訂單
    @PostMapping
    public void createOrder(@RequestBody List<ProductOrderRequest> productList) throws JsonProcessingException {
        orderService.createOrder(productList);
    }
}
