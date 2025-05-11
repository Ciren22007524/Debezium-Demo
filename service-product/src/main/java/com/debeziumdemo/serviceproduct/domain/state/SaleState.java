package com.debeziumdemo.serviceproduct.domain.state;

import com.debeziumdemo.serviceproduct.domain.model.ProductStatus;
import com.debeziumdemo.serviceproduct.domain.model.TestProduct;
import org.springframework.stereotype.Component;

@Component
class SaleState implements ProductState {

    @Override
    public ProductStatus supportedStatus() { return ProductStatus.SALE; }

    @Override
    public ProductStatus decreaseStock(TestProduct p, int qty) {
        int remain = p.getStock() - qty;
        if (remain < 0) throw new IllegalStateException("庫存不足");

        p.setStock(remain);
        return remain == 0 ? ProductStatus.SOLDOUT : p.getStatus();
    }
}
