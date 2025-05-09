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
    aggregatetype VARCHAR(255),
    aggregateid BIGINT,
    eventtype VARCHAR(255),
    payload TEXT,
    createdat TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);