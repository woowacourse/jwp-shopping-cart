insert into member (email, name, password)
values ('ari@wooteco.com', '아리', '1ebe88ec1665d6f66f0925713af52a92cc340bbd95d168680e26037097baa00f'),
       ('rex@wooteco.com', '렉스', '1ebe88ec1665d6f66f0925713af52a92cc340bbd95d168680e26037097baa00f'),
       ('huni@wooteco.com', '후니', '1ebe88ec1665d6f66f0925713af52a92cc340bbd95d168680e26037097baa00f'),
       ('maru@wooteco.com', '마루', '1ebe88ec1665d6f66f0925713af52a92cc340bbd95d168680e26037097baa00f'),
       ('cocacola@wooteco.com', '코카콜라', '1ebe88ec1665d6f66f0925713af52a92cc340bbd95d168680e26037097baa00f'),
       ('hope@wooteco.com', '호프', '1ebe88ec1665d6f66f0925713af52a92cc340bbd95d168680e26037097baa00f')
;

insert into product (name, price, image_url)
values ('아이스아메리카노', 2500, 'americanoUrl'),
       ('아이스라떼', 3000, 'latteUrl'),
       ('아이스초코', 3500, 'chocolateUrl')
;

insert
into cart_item (member_id, product_id, quantity)
values (1, 1, 1),
       (1, 2, 1)
;
