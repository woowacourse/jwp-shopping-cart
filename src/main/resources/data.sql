DROP TABLE IF EXISTS product;

CREATE TABLE product
(
    id      BIGINT AUTO_INCREMENT,
    name    VARCHAR(45) NOT NULL,
    price   INT         NOT NULL,
    img_url CLOB(10K) NOT NULL,
    PRIMARY KEY (id)
);

INSERT INTO product(id, name, price, img_url)
VALUES (1, '상품명 테스트1', 100, 'https://cdn.pixabay.com/photo/2014/06/03/19/38/test-361512_960_720.jpg'),
       (2, '상품명 테스트2', 101, 'https://t1.daumcdn.net/cartoon/7520d517fbe0271cdceb64e57ff1b26aa2abb680');
