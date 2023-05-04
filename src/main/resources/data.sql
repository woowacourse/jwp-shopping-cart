INSERT INTO product (id, name, image_url, price)
VALUES ('1',
        '치킨',
        'https://pelicana.co.kr/resources/images/menu/best_menu02_200824.jpg',
        1000);

INSERT INTO product (id, name, image_url, price)
VALUES ('2',
        '피자',
        'https://cdn.dominos.co.kr/admin/upload/goods/20210226_GYHC7RpD.jpg',
        10000);

INSERT INTO product (id, name, image_url, price)
VALUES ('3',
        '떡볶이',
        'https://www.sincham.com/images/main/main_vis01.jpg',
        20000);

INSERT INTO product (id, name, image_url, price)
VALUES ('4',
        '파스타',
        'https://img-cf.kurly.com/shop/data/goodsview/20220406/gv20000299645_1.jpg',
        30000);

INSERT INTO member (id, email, password)
VALUES ('1', 'a@a.com', 'password1');
INSERT INTO member (id, email, password)
VALUES ('2', 'b@b.com', 'password2');

INSERT INTO cart (member_id, product_id)
VALUES ('1', '1');
INSERT INTO cart (member_id, product_id)
VALUES ('1', '2');