insert into member (email, name, password)
values ('ari@wooteco.com', '아리', '1ebe88ec1665d6f66f0925713af52a92cc340bbd95d168680e26037097baa00f'),
       ('rex@wooteco.com', '렉스', '1ebe88ec1665d6f66f0925713af52a92cc340bbd95d168680e26037097baa00f'),
       ('huni@wooteco.com', '후니', '1ebe88ec1665d6f66f0925713af52a92cc340bbd95d168680e26037097baa00f'),
       ('maru@wooteco.com', '마루', '1ebe88ec1665d6f66f0925713af52a92cc340bbd95d168680e26037097baa00f'),
       ('cocacola@wooteco.com', '코카콜라', '1ebe88ec1665d6f66f0925713af52a92cc340bbd95d168680e26037097baa00f'),
       ('hope@wooteco.com', '호프', '1ebe88ec1665d6f66f0925713af52a92cc340bbd95d168680e26037097baa00f')
;

insert into product (name, price, image_url)
values ('꼬북칩', 2500, '꼬북칩.jpg'),
       ('포카칩', 1500, '포카칩.jpg'),
       ('콜라', 1200, '콜라.jpg')
;

insert into cart_item (member_id, product_id, quantity)
values (1, 1, 4),
       (1, 2, 10),
       (2, 1, 3),
       (4, 1, 3),
       (4, 2, 3)
;

insert into orders (member_id)
values (1),
       (4),
       (4)
;

insert into orders_detail(orders_id, product_id, quantity)
values (1, 1, 4),
       (1, 2, 10),
       (2, 1, 3),
       (3, 2, 3)
;
