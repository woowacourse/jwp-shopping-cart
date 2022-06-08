insert into customer (email, nickname, password)
values ('email@email.com', '파랑', 'password123!'),
       ('puterism@email.com', '파랑', 'password123!'),
       ('tanney-102@email.com', '파랑', 'password123!'),
       ('jho2301@email.com', '파랑', 'password123!'),
       ('365kim@email.com', '파랑', 'password123!'),
       ('dudtjr913@email.com', '파랑', 'password123!'),
       ('jum0@email.com', '파랑', 'password123!'),
       ('hyuuunjukim@email.com', '파랑', 'password123!'),
       ('zereight@email.com', '파랑', 'password123!'),
       ('devhyun637@email.com', '파랑', 'password123!'),
       ('swon3210@email.com', '파랑', 'password123!'),
       ('bigsaigon333@email.com', '파랑', 'password123!'),
       ('yungo1846@email.com', '파랑', 'password123!'),
       ('zigsong@email.com', '파랑', 'password123!'),
       ('iborymagic@email.com', '파랑', 'password123!'),
       ('0307kwon@email.com', '파랑', 'password123!'),
       ('gwangyeol-im@email.com', '파랑', 'password123!'),
       ('shinsehantan@email.com', '파랑', 'password123!'),
       ('ddongule@email.com', '파랑', 'password123!'),
       ('seojihwan@email.com', '파랑', 'password123!'),
       ('0imbean0@email.com', '파랑', 'password123!'),
       ('sunyoungkwon@email.com', '파랑', 'password123!'),
       ('hchayan@email.com', '파랑', 'password123!'),
       ('2sooy@email.com', '파랑', 'password123!'),
       ('yujo11@email.com', '파랑', 'password123!'),
       ('sunhpark42@email.com', '파랑', 'password123!')
;

insert into product (name, price, stock, image_url)
values ('캠핑 의자', 35000, 10, 'https://thawing-fortress-83192.herokuapp.com/static/images/camping-chair.jpg'),
       ('그릴', 100000, 10, 'https://thawing-fortress-83192.herokuapp.com/static/images/grill.jpg'),
       ('아이스박스', 20000, 10, 'https://thawing-fortress-83192.herokuapp.com/static/images/icebox.jpg'),
       ('소풍 바구니', 15000, 10, 'https://thawing-fortress-83192.herokuapp.com/static/images/picnic-box.jpg'),
       ('가방', 55000, 10, 'https://thawing-fortress-83192.herokuapp.com/static/images/bag.jpg'),
       ('컵', 10000, 10, 'https://thawing-fortress-83192.herokuapp.com/static/images/cup.jpg'),
       ('손전등', 45000, 10, 'https://thawing-fortress-83192.herokuapp.com/static/images/flashlight.jpg'),
       ('무쇠팬', 34000, 10, 'https://thawing-fortress-83192.herokuapp.com/static/images/frying-pan.jpg'),
       ('해먹', 50000, 10, 'https://thawing-fortress-83192.herokuapp.com/static/images/hammock.jpg'),
       ('휴대용 전등', 30000, 10, 'https://thawing-fortress-83192.herokuapp.com/static/images/lamp.jpg'),
       ('침낭', 40000, 10, 'https://thawing-fortress-83192.herokuapp.com/static/images/sleeping-bag.jpg'),
       ('텀블러', 25000, 10, 'https://thawing-fortress-83192.herokuapp.com/static/images/tumbler.jpg'),
       ('도끼', 52000, 10, 'https://thawing-fortress-83192.herokuapp.com/static/images/ax.jpg'),
       ('모카포트', 40000, 10, 'https://thawing-fortress-83192.herokuapp.com/static/images/coffee-maker.jpg'),
       ('버너', 33000, 10, 'https://thawing-fortress-83192.herokuapp.com/static/images/burner.jpg'),
       ('불꽃놀이', 5000, 10, 'https://thawing-fortress-83192.herokuapp.com/static/images/firecracker.jpg'),
       ('더치오븐 삼각대', 100000, 10, 'https://thawing-fortress-83192.herokuapp.com/static/images/tripod.jpg'),
       ('스위스 칼', 26700, 10, 'https://thawing-fortress-83192.herokuapp.com/static/images/swiss-knife.jpg'),
       ('테이블', 55000, 10, 'https://thawing-fortress-83192.herokuapp.com/static/images/table.jpg')
;

insert into cart_item (customer_id, product_id, quantity)
values (1L, 1L, 1),
       (1L, 2L, 1)
;
