CREATE TABLE IF NOT EXISTS test_product (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    version INT,
    `name` VARCHAR(255),
    description TEXT,
    price DECIMAL,
    stock INT,
    status VARCHAR(255),
    img_url VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS outbox_event (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    aggregate_type VARCHAR(255),
    aggregate_id BIGINT,
    event_type VARCHAR(255),
    payload TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);