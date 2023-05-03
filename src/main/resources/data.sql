INSERT
IGNORE INTO product(product_id, name, price, image_url)
VALUES (1, '스프링', 1000, 'https://previews.123rf.com/images/marucyan/marucyan1211/marucyan121100106/16114832-새싹.jpg'),
       (2, '빙봉', 10000,
        'https://mblogthumb-phinf.pstatic.net/MjAxNjEyMTNfMTk4/MDAxNDgxNjAwMDE1ODM1.mxPz7FBOV9DF5ryoeYEAcfZfBwMeFOYp-p03N7RAO68g.ICYGpmoaW0Q0wSenF5JQzQ4LZ3aqRWazx66DTqYgJFcg.JPEG.jeenyjin/Screenshot_2016-12-12-13-48-12-1.jpg?type=w800');

INSERT
IGNORE INTO user_info(email, password)
    VALUES ('jena@naver.com', 'pwjena12'),
    ('spring@gmail.com', 'pwspring34');
