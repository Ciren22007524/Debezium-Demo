package com.debeziumdemo.serviceproduct.domain.state;

import com.debeziumdemo.serviceproduct.domain.model.ProductStatus;
import com.debeziumdemo.serviceproduct.domain.model.TestProduct;
import org.springframework.stereotype.Component;

@Component
class SoldOutState implements ProductState {

    @Override
    public ProductStatus supportedStatus() {
        return ProductStatus.SOLDOUT;
    }

    @Override
    public ProductStatus decreaseStock(TestProduct p, int qty) {
        // SOLDOUT 時不可以有任何扣庫存的行為
        throw new IllegalStateException(
                "商品已售完，productId=" + p.getId() + "，無法再扣庫存");
    }
}
