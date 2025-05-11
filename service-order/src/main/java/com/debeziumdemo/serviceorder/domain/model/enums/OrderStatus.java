package com.debeziumdemo.serviceorder.domain.model.enums;

public enum OrderStatus {
    CREATED,        // 使用者剛下單，尚未付款
    PAID,           // 完成付款
    PROCESSING,     // 商家處理中
    COMPLETED,      // 訂單完成（用戶收到）
    CANCELLED       // 訂單取消
}
