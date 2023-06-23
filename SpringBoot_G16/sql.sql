DROP TABLE Member CASCADE CONSTRAINTS;

create table member(
	name varchar2(30),
	userid varchar2(30),
	pwd varchar2(30),
	email varchar2(30),
	phone varchar2(15),
	provider varchar2(30),
	primary key(userid)
);

insert into member values('홍길동', 'scott', '1234', 'scott@abc.com', '010-1234-1234', null);

select*from member;