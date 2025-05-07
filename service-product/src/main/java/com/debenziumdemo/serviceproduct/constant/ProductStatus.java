package com.debenziumdemo.serviceproduct.constant;

public enum ProductStatus {
    CREATED,      // 初始建立
    AVAILABLE,    // 可購買
    OUT_OF_STOCK, // 缺貨
    DISCONTINUED, // 已下架 / 停售
    DELETED       // 刪除（視實務是否保留資料）
}
