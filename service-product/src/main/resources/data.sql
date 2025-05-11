INSERT INTO test_product (version, `name`, description, price, stock, status, img_url)
SELECT 1, '新竹市立動物園', '新竹市立動物園', 50.00, 10, 'SALE', '/images/HsinchuZoo.jpg'
    WHERE NOT EXISTS (SELECT 1 FROM test_product WHERE name = '新竹市立動物園');

INSERT INTO test_product (version, `name`, description, price, stock, status, img_url)
SELECT 1, '太魯閣國家公園', '太魯閣國家公園', 200.00, 20, 'SALE', '/images/TarokoNationalPark.jpg'
    WHERE NOT EXISTS (SELECT 1 FROM test_product WHERE name = '太魯閣國家公園');

INSERT INTO test_product (version, `name`, description, price, stock, status, img_url)
SELECT 1, '阿里山國家森林遊樂區', '阿里山國家森林遊樂區', 300.00, 30, 'SALE', '/images/AlishanNationalForestRecreationAreamainEntrance.jpg'
    WHERE NOT EXISTS (SELECT 1 FROM test_product WHERE name = '阿里山國家森林遊樂區');

INSERT INTO test_product (version, `name`, description, price, stock, status, img_url)
SELECT 1, '日月潭纜車', '日月潭纜車', 300.00, 40, 'SALE', '/images/SunMoonLakeRopeway.jpg'
    WHERE NOT EXISTS (SELECT 1 FROM test_product WHERE name = '日月潭纜車');

INSERT INTO test_product (version, `name`, description, price, stock, status, img_url)
SELECT 1, '野柳地質公園', '野柳地質公園', 120.00, 50, 'SALE', '/images/Yehliu.jpg'
    WHERE NOT EXISTS (SELECT 1 FROM test_product WHERE name = '野柳地質公園');