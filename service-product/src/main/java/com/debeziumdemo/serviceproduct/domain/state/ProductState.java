package com.debeziumdemo.serviceproduct.domain.state;

import com.debeziumdemo.serviceproduct.domain.model.ProductStatus;
import com.debeziumdemo.serviceproduct.domain.model.TestProduct;

public interface ProductState {
    // 告訴工廠自己對應哪個 enum
    ProductStatus supportedStatus();

    /** @return 下一個狀態 (若無轉移則回傳原狀態) */
    ProductStatus decreaseStock(TestProduct product, int qty);
}
