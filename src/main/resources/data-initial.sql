INSERT INTO product (name, price, category, image_url)
values ('치킨', 20000, 'FOOD',
        'https://i.namu.wiki/i/8yy6XiTsE0gUGm7L1vYk9hcdUqgAqmToXyUoqnTycsiH1ibSyX7odxQVBGzdThqFH6NIi_-ehK5AoYgHL7fa2qeBEAmM-2nd_lCY656m6Go9aEz94ZDbZCzM1Qkp0bJm29IOkRysn7LqHX3y8mduXQ.webp')
     , ('아이폰14 PRO', 1200000, 'ELECTRONIC',
        'https://store.storeimages.cdn-apple.com/8756/as-images.apple.com/is/iphone14pro-digitalmat-gallery-3-202209?wid=728&hei=666&fmt=png-alpha&.v=1663346233180')
     , ('고퍼우드 g110', 170000, 'INSTRUMENT',
        'https://m.gopherwood.co.kr/web/product/big/201910/48430668d3e313d9446d19a9b32afb83.jpg');

INSERT INTO cart_user (email, cart_password)
values ('a@a.com', 'password1'),
       ('b@b.com', 'password2');

INSERT INTO cart_user_product (cart_user_id, product_id)
values (1, 1),
       (1, 2),
       (1, 3),
       (2, 3);
