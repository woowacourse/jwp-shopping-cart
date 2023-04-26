DROP TABLE IF EXISTS product;

CREATE TABLE PRODUCT
(
    id      BIGINT      NOT NULL AUTO_INCREMENT,
    name    VARCHAR(20) NOT NULL,
    img_url VARCHAR(255),
    price   INT         NOT NULL,
    PRIMARY KEY (id)
);

INSERT INTO PRODUCT (name, img_url, price)
values ('피자',
        'https://cdn.dominos.co.kr/admin/upload/goods/20200311_x8StB1t3.jpg',
        13000);
INSERT INTO PRODUCT (name, img_url, price)
values ('샐러드',
        'https://m.subway.co.kr/upload/menu/K-%EB%B0%94%EB%B9%84%ED%81%90-%EC%83%90%EB%9F%AC%EB%93%9C-%EB%8B%A8%ED%92%88_20220413025007802.png',
        20000);
INSERT INTO PRODUCT (name, img_url, price)
values ('치킨',
        'https://cdn.thescoop.co.kr/news/photo/202010/41306_58347_1055.jpg',
        10000);
