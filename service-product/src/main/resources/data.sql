INSERT INTO test_product (version, `name`, description, price, stock, status, img_url)
SELECT 1, '測試商品 A', '這是 A 的描述', 100.00, 10, 'SALE', 'https://cdn.fontrip.com/fonticket-test/photo/165873598431307625_750x500.webp'
    WHERE NOT EXISTS (SELECT 1 FROM test_product WHERE name = '測試商品 A');

INSERT INTO test_product (version, `name`, description, price, stock, status, img_url)
SELECT 1, '測試商品 B', '這是 B 的描述', 200.00, 20, 'WAIT_SALE', 'https://cdn.fontrip.com/fonticket-test/photo/166389682227044500_450x300.webp'
    WHERE NOT EXISTS (SELECT 1 FROM test_product WHERE name = '測試商品 B');

INSERT INTO test_product (version, `name`, description, price, stock, status, img_url)
SELECT 1, '測試商品 C', '這是 C 的描述', 300.00, 30, 'SOLDOUT', 'https://cdn.fontrip.com/fonticket-test/photo/166565669455663884_450x300.webp'
    WHERE NOT EXISTS (SELECT 1 FROM test_product WHERE name = '測試商品 C');

INSERT INTO test_product (version, `name`, description, price, stock, status, img_url)
SELECT 1, '測試商品 D', '這是 D 的描述', 400.00, 40, 'EDIT', 'https://cdn.fontrip.com/fonticket-test/photo/164421420775117452_450x300.webp'
    WHERE NOT EXISTS (SELECT 1 FROM test_product WHERE name = '測試商品 D');

INSERT INTO test_product (version, `name`, description, price, stock, status, img_url)
SELECT 1, '測試商品 E', '這是 E 的描述', 500.00, 50, 'EXPIRED', 'https://cdn.fontrip.com/fonticket-test/photo/165112330056220158_450x300.webp'
    WHERE NOT EXISTS (SELECT 1 FROM test_product WHERE name = '測試商品 E');