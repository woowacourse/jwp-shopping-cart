-- product 테스트 데이터
insert into PRODUCT (name, image_url, price)
values ('TEST1',
        'https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2F4ZJ4c%2Fbtq9E5eWgJy%2FNGk22wSskBbp6D5lED2jlK%2Fimg.jpg',
        4000);
insert into PRODUCT (name, image_url, price)
values ('TEST2',
        'https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2F4ZJ4c%2Fbtq9E5eWgJy%2FNGk22wSskBbp6D5lED2jlK%2Fimg.jpg',
        4100);
insert into PRODUCT (name, image_url, price)
values ('TEST3',
        'https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2F4ZJ4c%2Fbtq9E5eWgJy%2FNGk22wSskBbp6D5lED2jlK%2Fimg.jpg',
        4200);
insert into PRODUCT (name, image_url, price)
values ('TEST4',
        'https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2F4ZJ4c%2Fbtq9E5eWgJy%2FNGk22wSskBbp6D5lED2jlK%2Fimg.jpg',
        4300);

-- member 테스트 데이터
insert into member (email, password)
values ('hongSile@wooteco.com', 'hongSile');

insert into member (email, password)
values ('seovalue@wooteco.com', 'soevalue');

-- cart 테스트 데이터
insert into cart (member_id, product_id)
values (1L, 1L);
