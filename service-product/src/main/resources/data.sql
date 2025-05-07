INSERT INTO test_product (version, `name`, description, price, stock, status)
SELECT 1, '測試商品 A', '這是 A 的描述', 100.00, 10, 'ENABLED'
    WHERE NOT EXISTS (SELECT 1 FROM test_product WHERE name = '測試商品 A');

INSERT INTO test_product (version, `name`, description, price, stock, status)
SELECT 1, '測試商品 B', '這是 B 的描述', 200.00, 20, 'ENABLED'
    WHERE NOT EXISTS (SELECT 1 FROM test_product WHERE name = '測試商品 B');

INSERT INTO test_product (version, `name`, description, price, stock, status)
SELECT 1, '測試商品 C', '這是 C 的描述', 300.00, 30, 'DISABLED'
    WHERE NOT EXISTS (SELECT 1 FROM test_product WHERE name = '測試商品 C');

INSERT INTO test_product (version, `name`, description, price, stock, status)
SELECT 1, '測試商品 D', '這是 D 的描述', 400.00, 40, 'ENABLED'
    WHERE NOT EXISTS (SELECT 1 FROM test_product WHERE name = '測試商品 D');

INSERT INTO test_product (version, `name`, description, price, stock, status)
SELECT 1, '測試商品 E', '這是 E 的描述', 500.00, 50, 'DISABLED'
    WHERE NOT EXISTS (SELECT 1 FROM test_product WHERE name = '測試商品 E');