package com.debeziumdemo.serviceproduct.domain.state;

import com.debeziumdemo.serviceproduct.domain.model.ProductStatus;
import com.debeziumdemo.serviceproduct.domain.model.TestProduct;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class ProductStateFactory {

    private final Map<ProductStatus, ProductState> registry;

    public ProductStateFactory(List<ProductState> states) {
        this.registry = states.stream()
                .collect(Collectors.toMap(ProductState::supportedStatus, Function.identity()));
    }

    /** 找不到就回傳一個會直接丟出 Unsupported 的 State */
    public ProductState of(ProductStatus status) {
        return registry.getOrDefault(status, new ProductState() {
            @Override
            public ProductStatus supportedStatus() {
                return status;      // 讓 factory 查得到 enum
            }

            @Override
            public ProductStatus decreaseStock(TestProduct p, int qty) {
                throw new UnsupportedOperationException(
                        "狀態：" + status + " 不支援扣庫存");
            }
        });
    }

}
