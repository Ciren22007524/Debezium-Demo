package com.debeziumdemo.serviceorder.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductOrderRequest {
    private Long productId;       // 商品 ID
    private Integer quantity;     // 購買數量
    private BigDecimal price;     // 當下下單的單價（由前端或 product service 傳進來）
}
