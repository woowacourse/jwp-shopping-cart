drop table if exists cart_item;

drop table if exists product;

drop table if exists customer;

create table customer
(
    id         bigint       not null auto_increment,
    username   varchar(255) not null,
    password   varchar(255) not null,
    nickname   varchar(255) not null,
    withdrawal boolean,
    primary key (id)
) engine=InnoDB default charset=utf8mb4;

alter table customer
    add unique key (username);
alter table customer
    add unique key (nickname);

create table product
(
    id        bigint       not null auto_increment,
    name      varchar(255) not null,
    price     integer      not null,
    image_url varchar(255),
    primary key (id)
) engine=InnoDB default charset=utf8mb4;

create table cart_item
(
    id          bigint  not null auto_increment,
    customer_id bigint  not null,
    product_id  bigint  not null,
    quantity    integer not null,
    primary key (id)
) engine=InnoDB default charset=utf8mb4;

alter table cart_item
    add constraint fk_cart_item_to_customer
        foreign key (customer_id) references customer (id);

alter table cart_item
    add constraint fk_cart_item_to_product
        foreign key (product_id) references product (id);

insert into customer (username, password, nickname, withdrawal)
values ('first@naver.com', 'abcd1234!', 'firstCustomer', false);

insert into product (name, price, image_url)
values (
           'SPC삼립 뉴욕샌드위치식빵 (990g×4ea) BOX',
           12090,
           'https://cdn-mart.baemin.com/sellergoods/main/678bd8ec-e5fa-4ae2-be55-2cd290b3f10f.jpg'
       ),
       (
           '생칵테일새우 두절탈각 31/40 IQF 900g 2개',
           32000,
           'https://cdn-mart.baemin.com/sellergoods/main/59d3d65a-f526-4f6c-909c-fe6c61457ee2.jpg'
       ),
       (
           '생칵테일새우 두절탈각 26/30 IQF 900g 2개',
           34400,
           'https://cdn-mart.baemin.com/sellergoods/main/96ca0058-d6ba-4646-ad93-b05a2f696e24.jpg'
       ),
       (
           '생칵테일새우 두절탈각 21/25 IQF 900g 2개',
           36400,
           'https://cdn-mart.baemin.com/sellergoods/main/f44d66d6-55bd-4105-a713-f2c0a520bded.jpg'
       ),
       (
           '밀키스마 멸균우유3.5% 1L 팩 12개',
           19290,
           'https://cdn-mart.baemin.com/sellergoods/main/b4edc575-fa5b-472e-a733-fda7d8a65fcf.jpg'
       )
        ,
       (
           '생새우살 (51/70) 500g 4개',
           35000,
           'https://cdn-mart.baemin.com/sellergoods/main/2d47d961-32b1-480b-bba2-0ca8b35cb84b.jpg'
       ),
       (
           '생새우살 (71/90) 500g 4개',
           32000,
           'https://cdn-mart.baemin.com/sellergoods/main/6b95c66a-c13d-4ccd-9df5-b1af1428a225.jpg'
       ),
       (
           '펩시 콜라 355ml 24개',
           16100,
           'https://cdn-mart.baemin.com/sellergoods/main/84fc0238-0239-4d0e-870b-a9daa6f2c42c.jpg'
       )
        ,
       (
           '생칵테일새우 두절탈각 41/50 IQF 900g 2개',
           30000,
           'https://cdn-mart.baemin.com/sellergoods/main/67332bd1-cf09-4bc3-9b75-c83fba9e6894.jpg'
       ),
       (
           '리치스 스위트콘 대 2.95kg',
           5290,
           'https://cdn-mart.baemin.com/sellergoods/bulk/20201102-231612/15839-main-01.jpg'
       ),
       (
           '생새우살 (91/120) 500g 4개',
           31200,
           'https://cdn-mart.baemin.com/sellergoods/main/3913637e-abee-44b4-b8c1-09eb58f08b49.jpg'
       ),
       (
           '칵테일새우 (51/70) 500g 4개',
           39000,
           'https://cdn-mart.baemin.com/sellergoods/main/0452091a-1181-4c26-8d77-7eaaf9510a17.jpg'
       ),
       (
           '칵테일새우 (31/50) 500g 4개',
           42400,
           'https://cdn-mart.baemin.com/sellergoods/main/49d9f2a2-6c12-47ab-a4fa-6930d053d44f.jpg'
       )
        ,
       (
           '생칵테일새우 두절탈각 16/20 IQF 900g 2개',
           38400,
           'https://cdn-mart.baemin.com/sellergoods/main/c765e6a5-8a4c-4824-85b2-9ceb8c7181a0.jpg'
       ),
       (
           '정스팜 베스트코 와사비맛쌈무 3kg',
           5870,
           'https://cdn-mart.baemin.com/sellergoods/bulk/20201119-140757/16126-main-01.jpg'
       ),
       (
           '정스팜 베스트코 새콤한쌈무 3kg',
           5870,
           'https://cdn-mart.baemin.com/sellergoods/bulk/20201119-140757/16125-main-01.jpg'
       )
        ,
       (
           '자숙새우살 (100/200) 500g 4개',
           35930,
           'https://cdn-mart.baemin.com/sellergoods/main/8fb08bc7-cfcc-4a42-abb5-68a00d79ecdf.jpg'
       ),
       (
           '동원참치 라이트스탠다드 1880g',
           14190,
           'https://cdn-mart.baemin.com/sellergoods/main/6dba64d7-aae5-46b8-a808-30effe951c2c.png'
       ),
       (
           'values햇양파; 국내산 피양파 1망(15kg내외)',
           13900,
           'https://cdn-mart.baemin.com/sellergoods/main/fc15dd1f-b30e-4bed-b046-60338930fc1d.png'
       ),
       (
           '페루산 냉동 딸기 1kg',
           5090,
           'https://cdn-mart.baemin.com/sellergoods/main/eb8ea1ed-7988-4b31-a352-8d27773812de.jpg'
       ),
       (
           '맘스터치앤컴퍼니(해마로) 순살치킨 1kg 5개',
           43300,
           'https://cdn-mart.baemin.com/goods/36/1556013318466m0.jpg'
       ),
       (
           '칵테일새우 (71/90) 500g 4개',
           35000,
           'https://cdn-mart.baemin.com/sellergoods/main/bc07ab97-897b-4140-832f-221af0f36537.jpg'
       )
        ,
       (
           '미미사 분모자 (17mm) 250G',
           1440,
           'https://cdn-mart.baemin.com/sellergoods/main/6ae4c431-6988-41ab-8894-7df7ee7b7cb3.jpg'
       ),
       (
           '옥구농협 못잊어 신동진(신동진/보통/21년)20KG',
           54300,
           'https://cdn-mart.baemin.com/sellergoods/bulk/20220203-195555/23358-main-01.jpg'
       ),
       (
           '델링 치킨 양념 30g 100개',
           22600,
           'https://cdn-mart.baemin.com/sellergoods/bulk/20200731-110944/14449-main-01.jpg'
       ),
       (
           '경성 고춧가루 김치용(중국산) 2.5kg',
           21990,
           'https://cdn-mart.baemin.com/sellergoods/main/e4540ba7-710b-4287-87c0-ba531f6ce069.jpg'
       ),
       (
           '생새우살 (31/50) 500g 4개',
           38000,
           'https://cdn-mart.baemin.com/sellergoods/main/154bd485-c0c6-4ccb-9c2b-f9ec232b18d0.jpg'
       ),
       (
           '친환경 탕용기(소/JH/흑색) 50개',
           18400,
           'https://cdn-mart.baemin.com/sellergoods/main/0b7ed87b-b231-46e8-a374-cd60f619f939.jpeg'
       ),
       (
           '자숙새우살 (200/300) 500g 4개',
           33430,
           'https://cdn-mart.baemin.com/sellergoods/main/c6bf9b8e-81b0-4cb2-958c-bbebb52c68b0.jpg'
       )
        ,
       (
           '쉐프솔루션 통바삭김말이 (35g*40ea) 1.4kg',
           9030,
           'https://cdn-mart.baemin.com/goods/27/159004_1_I.png'
       ),
       (
           '바삭한새우맛이가득한새우칩 1kg',
           3930,
           'https://cdn-mart.baemin.com/sellergoods/main/9c61a8ba-5b4d-477e-9532-ce3255c7229e.jpg'
       ),
       (
           '굿프랜즈 치킨가라아게 1kg',
           10660,
           'https://cdn-mart.baemin.com/sellergoods/main/b87400fc-a42b-416e-9e45-b3750186e7b7.jpg'
       ),
       (
           '자판프리마 1kg',
           3450,
           'https://cdn-mart.baemin.com/sellergoods/bulk/20201102-231612/15857-main-01.jpg'
       ),
       (
           '면사랑 냉면육수 사골맛 (340g*5입) 6개',
           14900,
           'https://cdn-mart.baemin.com/goods/12/155626821431m0.jpg'
       )
        ,
       (
           '오뚜기 옛날조청쌀엿 2.5kg 6개',
           70680,
           'https://cdn-mart.baemin.com/goods/80/%EC%8C%80%EC%97%BF.jpg'
       ),
       (
           '해마로 브래디드 쉬림프(새우함량 40%) 600g',
           8960,
           'https://cdn-mart.baemin.com/sellergoods/main/e6f64600-0f86-495f-b28f-2d7f2c148bee.jpg'
       ),
       (
           '매직랩 컴팩트형 홀리데이 에디션 12개입',
           52900,
           'https://cdn-mart.baemin.com/sellergoods/main/8d3336ff-49a5-4c37-9d54-09696261a8b2.png'
       ),
       (
           'SPC삼립 프리미엄 크로와상 생지 (55g*20ea*2봉) 총 40ea / 2.2kg',
           38170,
           'https://cdn-mart.baemin.com/sellergoods/main/ee5efbfb-ebd4-4ab3-873b-522ea508149d.jpg'
       ),
       (
           'AGROSUPER 칠레산 돈육 무연골삼겹',
           10290,
           'https://cdn-mart.baemin.com/sellergoods/main/f47a8cb6-f7cf-49e0-a571-6434634889b8.jpg'
       ),
       (
           '뚜레반 회초장(초고추장) 13kg',
           24530,
           'https://cdn-mart.baemin.com/sellergoods/main/a332f4bd-0ba6-4976-8fec-3cdc8491e2b9.jpg'
       ),
       (
           '오뚜기 양조 식초 PET 18L',
           12900,
           'https://cdn-mart.baemin.com/sellergoods/bulk/20200922-140211/15151-main-01.jpg'
       ),
       (
           'SPC삼립 미니크로와상 생지 (18g*120ea) 2,16kg',
           23900,
           'https://cdn-mart.baemin.com/sellergoods/main/1edfb299-1ff4-4ad4-8b3f-f29fef068731.jpg'
       )
        ,
       (
           '오뚜기 오쉐프_알뜰당면 14KG / BOX',
           59000,
           'https://cdn-mart.baemin.com/sellergoods/main/4c951a80-80f5-471c-9e79-74b3e4b6d0f7.jpg'
       ),
       (
           '오뚜기 카놀라유 18L',
           64900,
           'https://cdn-mart.baemin.com/goods/35/1554268779110m0.jpg'
       ),
       (
           'values유통기한임박상품;냉동 딸기바나나믹스 1kg',
           4670,
           'https://cdn-mart.baemin.com/sellergoods/main/2cb1c5e4-982d-46ea-9ab0-b7dca8e768ea.jpg'
       ),
       (
           '청정원 햇살담은 양조진간장 15L',
           8340,
           'https://cdn-mart.baemin.com/goods/custom/20200608/11706-main-01.jpg'
       ),
       (
           '백설 돼지갈비양념 10kg',
           33370,
           'https://cdn-mart.baemin.com/sellergoods/main/68174455-b5ca-41e0-afe5-0ec9fb3a6cec.jpg'
       ),
       (
           'values유통기한임박상품;쿠키 파우더 800g',
           4380,
           'https://cdn-mart.baemin.com/sellergoods/bulk/20201102-231612/15856-main-01.jpg'
       ),
       (
           '화미 조개다시 1KG',
           7500,
           'https://cdn-mart.baemin.com/sellergoods/main/2f4cd396-8148-40e1-bb75-9576a7dceea3.jpg'
       )
        ,
       (
           '백설 갈색설탕 3kg',
           5680,
           'https://cdn-mart.baemin.com/sellergoods/main/e0ae135c-0964-4b86-8d4a-99959b8ed340.jpg'
       ),
       (
           '백설 갈색설탕 15kg',
           23660,
           'https://cdn-mart.baemin.com/goods/46/S71-RM-23353_%EB%B0%B1%EC%84%A4_%EA%B0%88%EC%83%89%EC%84%A4%ED%83%95(15kg)_%EB%A9%94%EC%9D%B8_%EC%8D%B8%EB%84%A4%EC%9D%BC.jpg'
       )
        ,
       (
           '굿프랜즈 고추튀김 1kg',
           14440,
           'https://cdn-mart.baemin.com/sellergoods/main/2418f046-2087-40f2-a7dd-76279f9b9fc2.jpg'
       ),
       (
           'WESTVLEES 벨기에산 돈육 암퇘지삼겹',
           9890,
           'https://cdn-mart.baemin.com/sellergoods/main/453582ec-c3e7-496b-b54f-babc387301b4.jpg'
       ),
       (
           'EXCEL 냉장 미국산 살치 CH values13시 마감;',
           24900,
           'https://cdn-mart.baemin.com/sellergoods/main/70b8f657-0720-4bfd-89a5-29c74bc5efa4.jpg'
       ),
       (
           'EXCEL 냉장 미국산 부채 CH values13시 마감;',
           17900,
           'https://cdn-mart.baemin.com/sellergoods/main/57066369-5da9-4893-8d10-a6ae89b01088.jpg'
       ),
       (
           '굿프랜즈 고기손만두 2800g',
           14320,
           'https://cdn-mart.baemin.com/sellergoods/main/fa557d48-9a2e-47c7-8308-62be35dac1e5.jpg'
       ),
       (
           'EXCEL 냉장 미국산 척아이롤 PR values13시 마감;',
           15900,
           'https://cdn-mart.baemin.com/sellergoods/main/9eb4772e-3564-4fde-9562-7edbec036a62.jpg'
       ),
       (
           '해든나라 냉면김치 3kg',
           12420,
           'https://cdn-mart.baemin.com/sellergoods/main/a562cf22-3c66-456b-883a-210f9620fd27.jpg'
       ),
       (
           '굿프랜즈 김치손만두 2800g',
           15050,
           'https://cdn-mart.baemin.com/sellergoods/main/8847c3bf-4932-4f69-9bd3-cdaa8bdace43.jpg'
       ),
       (
           'AGROSUPER 칠레산 돈육 뼈삼겹',
           10590,
           'https://cdn-mart.baemin.com/sellergoods/main/a50e6c1d-162d-4043-a006-d3ae06522969.jpg'
       )
        ,
       (
           '생칵테일새우 두절탈각 31/40 IQF 900g 10개',
           160000,
           'https://cdn-mart.baemin.com/sellergoods/main/ac02b79f-7463-4323-8d45-efbffcf18377.jpg'
       ),
       (
           '수리 타이 쓰리라차 칠리소스 435ml 6개',
           24030,
           'https://cdn-mart.baemin.com/goods/15/%EC%88%98%EB%A6%AC_%ED%83%80%EC%9D%B4_%EC%93%B0%EB%A6%AC%EB%9D%BC%EC%B0%A8_%EC%B9%A0%EB%A6%AC%EC%86%8C%EC%8A%A4_435ml_%EC%8D%B8%EB%84%A4%EC%9D%BC.jpg'
       ),
       (
           'values첫구매특가;일심 포장용 찬방치킨무 230g 40개',
           4900,
           'https://cdn-mart.baemin.com/goods/70/1554091631784m0.jpg'
       ),
       (
           'TEYS 호주산 곡물 곱창',
           11700,
           'https://cdn-mart.baemin.com/sellergoods/main/d6d65273-2337-4890-bc5c-d8a76a69d7f4.jpg'
       ),
       (
           'WESTVLEES 벨기에산 돈육 미박삼겹살',
           9590,
           'https://cdn-mart.baemin.com/sellergoods/main/b4aa2591-61ef-40d1-a116-797f6fcccccb.jpg'
       ),
       (
           '생칵테일새우 두절탈각 41/50 IQF 900g 10개',
           150000,
           'https://cdn-mart.baemin.com/sellergoods/main/851e61a5-c9b0-44a8-93bb-7d3d543e53a8.jpg'
       ),
       (
           '생칵테일새우 두절탈각 21/25 IQF 900g 10개',
           182000,
           'https://cdn-mart.baemin.com/sellergoods/main/7afc0290-024e-4daf-a66a-a7cc57b4473f.jpg'
       ),
       (
           '생칵테일새우 두절탈각 16/20 IQF 900g 10개',
           192000,
           'https://cdn-mart.baemin.com/sellergoods/main/e73e4a45-6d0b-44ce-a690-f4a796869e05.jpg'
       ),
       (
           '생칵테일새우 두절탈각 26/30 IQF 900g 10개',
           172000,
           'https://cdn-mart.baemin.com/sellergoods/main/cfcd1d8a-662f-4691-bd18-c2b32d8b7876.jpg'
       ),
       (
           '옛날맛 강정 소스 10kg',
           19200,
           'https://cdn-mart.baemin.com/sellergoods/bulk/20200731-110944/14453-main-01.jpg'
       ),
       (
           '오뚜기 짜장 1kg 10개',
           85710,
           'https://cdn-mart.baemin.com/goods/82/%EC%98%A4%EB%9A%9C%EA%B8%B0%EC%A7%9C%EC%9E%A5%EA%B0%80%EB%A3%A81KG%C3%9710%EA%B0%9C.jpg'
       ),
       (
           'values유통기한임박상품;옛날맛 강정 소스 10kg',
           22250,
           'https://cdn-mart.baemin.com/sellergoods/bulk/20200731-110944/14453-main-01.jpg'
       )
        ,
       (
           'values유통기한임박상품;오뚜기 제주식멜젓소스 40G (200EA) / BOX',
           44200,
           'https://cdn-mart.baemin.com/sellergoods/main/767e0435-78ce-4ee4-bbf6-a43593fc5f80.jpg'
       ),
       (
           '중국산 냉동 다진마늘 1kg',
           2410,
           'https://cdn-mart.baemin.com/goods/custom/20200714-MARTENG-8721/13778-main-01.jpg'
       ),
       (
           '페르디가오 브라질 닭정육 (냉동) 2kg 6개',
           65900,
           'https://cdn-mart.baemin.com/goods/27/1554117970000_m.jpg'
       ),
       (
           'AGROSUPER 칠레산 돈삼겹살',
           9990,
           'https://cdn-mart.baemin.com/sellergoods/main/4bbff852-4ca1-4065-83c5-772a49573609.jpg'
       ),
       (
           '덴마크 클래식우유 1L 15개 values13시마감 / 전국배송;',
           27750,
           'https://cdn-mart.baemin.com/sellergoods/main/99d3369c-5e84-496b-9be9-7866353492e2.jpg'
       ),
       (
           '두메코 네덜란드산 돈삼겹살',
           10590,
           'https://cdn-mart.baemin.com/sellergoods/main/59670c65-d4a1-4f9b-80d9-a3f86ced628f.jpg'
       ),
       (
           '면사랑 쟁반막국수 2kg 6개',
           24780,
           'https://cdn-mart.baemin.com/goods/14/%EB%A9%B4%EC%82%AC%EB%9E%91-%EC%9F%81%EB%B0%98%EB%A7%89%EA%B5%AD%EC%88%98(2kg)_%EB%A9%94%EC%9D%B8_%EC%8D%B8%EB%84%A4%EC%9D%BC_.jpg'
       ),
       (
           '일심 찬방치킨무 5.5kg',
           4950,
           'https://cdn-mart.baemin.com/goods/69/1550116043426m0.jpg'
       ),
       (
           '일심 포장용 찬방치킨무 230g 40개',
           12700,
           'https://cdn-mart.baemin.com/goods/70/1554091631784m0.jpg'
       ),
       (
           'High Mountain 오스트리아산 돈삼겹살',
           9690,
           'https://cdn-mart.baemin.com/sellergoods/main/90b3c7d9-59c3-4a17-842f-4267693352f9.jpg'
       )
        ,
       (
           '닭껍질 튀김 1kg 8개',
           42190,
           'https://cdn-mart.baemin.com/sellergoods/main/78b190d9-3d15-497a-bb38-d2c016c68e02.png'
       ),
       (
           '노르웨이 생연어필렛 trim E 2~2.2kg',
           96800,
           'https://cdn-mart.baemin.com/goods/34/%EC%97%B0%EC%96%B4_02_%EC%8D%B8%EB%84%A4%EC%9D%BC.jpg'
       ),
       (
           'VANROOI 네덜란드산 돈육 삼겹살',
           9990,
           'https://cdn-mart.baemin.com/sellergoods/main/4fed36d3-a007-4618-811a-b090e7f1eeb0.jpg'
       ),
       (
           '두리 하나애 슬라이스김치 중국산 10kg',
           17250,
           'https://cdn-mart.baemin.com/sellergoods/main/2f6e9eb7-81b8-4eb5-95dd-343949b37bd8.jpg'
       )
        ,
       (
           '코다노 DMC-F 2.5kg',
           16200,
           'https://cdn-mart.baemin.com/goods/69/00_S185-RM-54115-%E1%84%8F%E1%85%A9%E1%84%83%E1%85%A1%E1%84%82%E1%85%A9-DMC-F-2.5kg.jpg'
       ),
       (
           '사세 순살치킨 가라게 1kg 10개',
           96490,
           'https://cdn-mart.baemin.com/sellergoods/bulk/20211222-131703/4345-main-01.jpg'
       )
        ,
       (
           'SMITHFIELD 미국산 돈목살 320M',
           6990,
           'https://cdn-mart.baemin.com/sellergoods/main/82638efd-6b8c-464a-82be-7bb73720b705.jpg'
       ),
       (
           'SMITHFIELD 미국산 돈육 삼겹살',
           8590,
           'https://cdn-mart.baemin.com/sellergoods/main/bea05ef7-2dfa-4f86-a858-29ee0ab2c50b.jpg'
       ),
       (
           '코다노 조흥 피자치즈 레이 2.5kg',
           16200,
           'https://cdn-mart.baemin.com/sellergoods/main/700c2d24-ef92-4a28-9697-4dadbccf4873.jpg'
       ),
       (
           'HANDLBAUER 오스트리아산 돈육 삼겹살',
           8790,
           'https://cdn-mart.baemin.com/sellergoods/main/5028137c-5e3f-4fd4-95e2-56105de65460.jpg'
       ),
       (
           '한우물 야채볶음밥 3kg',
           11900,
           'https://cdn-mart.baemin.com/sellergoods/main/829b2641-eae4-42d3-9109-0398fd3ca486.jpg'
       ),
       (
           '아비코 스킨온프라이 레귤러컷 2kg 6개',
           40120,
           'https://cdn-mart.baemin.com/goods/34/1563411656393m0.jpg'
       ),
       (
           '절단 주꾸미 L 원스킨 500g 6개',
           36700,
           'https://cdn-mart.baemin.com/sellergoods/main/6a1b1da2-e800-4446-bf78-1936f72c7640.jpg'
       ),
       (
           '행복한맛남 골든치킨가라아게 1kg',
           7700,
           'https://cdn-mart.baemin.com/goods/12/main-12112.jpg'
       ),
       (
           'DANISHCROWN 덴마크산 돈육 삼겹살(진공)',
           8690,
           'https://cdn-mart.baemin.com/sellergoods/main/b58f431a-e477-4567-a7d3-f96d24d99cc2.jpg'
       ),
       (
           '두리 시골사랑 맛김치 중국산 10kg',
           16650,
           'https://cdn-mart.baemin.com/sellergoods/main/1cae45f7-d766-4715-943d-d38745dff2b0.jpg'
       ),
       (
           '펩시 콜라 500ml 20개',
           14000,
           'https://cdn-mart.baemin.com/goods/93/500.jpg'
       );

insert into cart_item (customer_id, product_id, quantity)
values (1, 1, 5),
       (1, 2, 7),
       (1, 3, 9);
