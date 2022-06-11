insert into member (email, name, password)
values ('ari@wooteco.com', '아리', '1ebe88ec1665d6f66f0925713af52a92cc340bbd95d168680e26037097baa00f'),
       ('rex@wooteco.com', '렉스', '1ebe88ec1665d6f66f0925713af52a92cc340bbd95d168680e26037097baa00f'),
       ('huni@wooteco.com', '후니', '1ebe88ec1665d6f66f0925713af52a92cc340bbd95d168680e26037097baa00f'),
       ('maru@wooteco.com', '마루', '1ebe88ec1665d6f66f0925713af52a92cc340bbd95d168680e26037097baa00f'),
       ('cocacola@wooteco.com', '코카콜라', '1ebe88ec1665d6f66f0925713af52a92cc340bbd95d168680e26037097baa00f'),
       ('hope@wooteco.com', '호프', '1ebe88ec1665d6f66f0925713af52a92cc340bbd95d168680e26037097baa00f')
;

insert into product (name, price, image_url)
values ('캐스터네츠 커스텀캣타워H_가드형',
        619000,
        'https://image.ohou.se/i/bucketplace-v2-development/uploads/productions/1608536490_103005_1.jpg?gif=1&w=1280&h=1280&c=c'),
       ('관절에 무리없는 캣워커/캣휠 120CM 대형사이즈',
        124000,
        'https://image.ohou.se/i/bucketplace-v2-development/uploads/productions/164776373888845264.jpg?gif=1&w=1280&h=1280&c=c'),
       ('펫토 세탁이 필요없는 강아지집 고양이집 숨숨집',
        99000,
        'https://image.ohou.se/i/bucketplace-v2-development/uploads/productions/162442749999617696.jpg?gif=1&w=1280&h=1280&c=c'),
       ('더테이블 자동급식기 화이트',
        119200,
        'https://image.ohou.se/i/bucketplace-v2-development/uploads/productions/164550636211665211.png?gif=1&w=1280&h=1280&c=c'),
       ('NEW 컬러추가 강아지 고양이 기절 댕냥쿠션/방석',
        26900,
        'https://image.ohou.se/i/bucketplace-v2-development/uploads/productions/165095245734958156.jpg?gif=1&w=1280&h=1280&c=c'),
       ('PETMARVEL 펫드라이룸 강아지 고양이 드라이기',
        167900,
        'https://image.ohou.se/i/bucketplace-v2-development/uploads/productions/images/162686064836045500.jpg?gif=1&w=1280&h=1280&c=c'),
       ('펫빈백 방석 펫소파',
        16900,
        'https://image.ohou.se/i/bucketplace-v2-development/uploads/productions/161225604925143188.jpg?gif=1&w=1280&h=1280&c=c'),
       ('냥틀 고양이 창문, 창틀선반 캣타워',
        24800,
        'https://image.ohou.se/i/bucketplace-v2-development/uploads/productions/images/163539162531225534.png?gif=1&w=1280&h=1280&c=c'),
       ('캣링크 WIFI 자동 고양이 화장실',
        42500,
        'https://image.ohou.se/i/bucketplace-v2-development/uploads/productions/images/162276338204677539.png?gif=1&w=1280&h=1280&c=c'),
       ('웨이브팟 반려동물 급수기',
        59000,
        'https://image.ohou.se/i/bucketplace-v2-development/uploads/productions/164601050125053383.png?gif=1&w=1280&h=1280&c=c'),
       ('공사가 필요없는 안전문 찐템! 간편 설치 펫도어',
        188000,
        'https://image.ohou.se/i/bucketplace-v2-development/uploads/productions/161060168158484166.jpg?gif=1&w=1280&h=1280&c=c'),
       ('베이직 슬라이드 스텝',
        32900,
        'https://image.ohou.se/i/bucketplace-v2-development/uploads/productions/images/162304907138525247.jpg?gif=1&w=1280&h=1280&c=c'),
       ('윈도우 캣타워 고양이 캣워커',
        39900,
        'https://image.ohou.se/i/bucketplace-v2-development/uploads/productions/162604963124399544.jpg?gif=1&w=1280&h=1280&c=c'),
       ('애견 미끄럼방지매트 계단형',
        2900,
        'https://image.ohou.se/i/bucketplace-v2-development/uploads/productions/156922752639820729.jpg?gif=1&w=1280&h=1280&c=c'),
       ('화이트 코튼 바스켓',
        13200,
        'https://image.ohou.se/i/bucketplace-v2-development/uploads/productions/157967973242309481.jpeg?gif=1&w=1280&h=1280&c=c'),
       ('네모백팩 고양이이동장 강아지가방',
        49800,
        'https://image.ohou.se/i/bucketplace-v2-development/uploads/productions/images/162201873263985107.jpg?gif=1&w=1280&h=1280&c=c'),
       ('강아지 이동가방 콤피슬링백',
        49000,
        'https://image.ohou.se/i/bucketplace-v2-development/uploads/productions/164146941904977456.jpg?gif=1&w=1280&h=1280&c=c')
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
