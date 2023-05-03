INSERT IGNORE INTO item(name, item_url, price) values ('치킨', 'https://image.homeplus.kr/td/f42afe4b-e0a8-4c79-a07c-aaee40c93a57', 10000);
INSERT IGNORE INTO item(name, item_url, price) values ('샐러드', 'https://www.raracost.com/images/sub/salad1.png', 20000);
INSERT IGNORE INTO item(name, item_url, price) values ('피자', 'https://www.ryupizza.com/wp-content/uploads/pizza401_1-1.jpg', 30000);
INSERT IGNORE INTO member(email, name, phone_number, password) values ('admin@naver.com', 'test', '01098765432', 'qwer1234');
INSERT IGNORE INTO cart(member_email, item_id) values ('admin@naver.com', 1);

