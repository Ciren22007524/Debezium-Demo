CREATE TABLE IF NOT EXISTS test_order (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    version INT,
    status VARCHAR(255),
    total_amount INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS order_item (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    order_id BIGINT,
    product_id BIGINT,
    quantity INT,
    unit_price DECIMAL,
    sub_total DECIMAL
);

CREATE TABLE IF NOT EXISTS outbox_event (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    aggregatetype VARCHAR(255) NOT NULL,
    aggregateid BIGINT NOT NULL,
    eventtype VARCHAR(255) NOT NULL,
    payload JSON NOT NULL,
    createdat TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    -- 日後排程清理欄位
    processed TINYINT(1) NOT NULL DEFAULT 0,
    processed_at TIMESTAMP NULL,
    KEY ix_event_createdat (createdat),
    KEY ix_event_processed (processed, createdat)
);