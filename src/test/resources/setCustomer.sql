alter table customer alter column id restart with 1;

insert into customer (username, password, nickname, withdrawal)
values ('jo@naver.com', 'abcd1234!', 'jojogreen', false);
