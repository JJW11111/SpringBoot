DROP TABLE Member CASCADE CONSTRAINTS;

CREATE TABLE Member(
id varchar2(20) NOT NULL,
pwd varchar2(20),
name varchar2(20),
email varchar2(40) NOT NULL,
zip_num varchar2(10),
address1 varchar2(50),
address2 varchar2(50),
address3 varchar2(50),
phone varchar2(20),
indate date DEFAULT SYSDATE,
useyn char(1) DEFAULT 'Y',
provider varchar2(30),
PRIMARY KEY(id)
)

insert into member(id, pwd, name, zip_num, address1, address2, phone, email) values
('one', '1111', '김나리', '133-110', '서울시 성동구 성숭동1가', '1번지 21호', '017-7777-7777', 'ac@acca.com');
insert into member(id, pwd, name, zip_num, address1, address2, phone, email) values
('two', '2222', '알바', '133-110', '서울시 성동구 성숭동1가', '1번지 21호', '017-7777-7777', 'ac@acca.com');
