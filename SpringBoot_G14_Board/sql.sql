DROP TABLE Member CASCADE CONSTRAINTS;

create table member(
	name varchar2(30),
	userid varchar2(30),
	pwd varchar2(30),
	email varchar2(30),
	phone varchar2(15),
	primary key(userid)
);

insert into member values('홍길동', 'scott' , '1234', 'scott@abc.com', '010-1234-1234' );

alter table member add provider varchar2(30);

select * from member;
select * from board order by num desc;

update member set provider='board' where userid='scott'

commit

create table board(
	num number(5) primary key,
	pass varchar2(30),   
	userid varchar2(30),
	email varchar2(30),
	title varchar2(50),
	content varchar2(1000),
	readcount number(4) default 0,
	writedate date default sysdate,
	imgfilename varchar2(100)
);

create sequence board_seq start with 1 increment by 1;


insert into board(num, userid, email, pass, title, content)
values( board_seq.nextVal, 'hong', 'abc@naver.com', '1234', '첫방문입니다', 
	'반갑습니다. 앞으로 많으 격려와 지도편달 부탁드립니다.' );
insert into board(num, userid, email, pass, title, content)
values(board_seq.nextVal, 'somi', 'adddnaver.com', '1234', '게시판 개설',
	'축하드립니다.  무궁한 발전을 기원할께요');
insert into board(num, userid, email, pass, title, content)
values(board_seq.nextVal, 'light', 'bnbn@naver.com', '1234', '돼지골마을',
	'돼지 삼겹살이 맛있습니다');
insert into board(num, userid, email, pass, title, content)
values(board_seq.nextVal, 'hana', 'hana@daum.net', '1234', '2022년 겨울' , 
	'몸시 추울꺼 같아요... 다들 건강 유의 하세요....');
insert into board(num, userid, email, pass, title, content)
values(board_seq.nextVal, 'hana', 'hana@daum.net', '1234', '코로나바이러스' , 
	'사회적 거리두기가 끝나갑니다 .... 일상으로 복귀 등등등');
insert into board(num, userid, email, pass, title, content)
values( board_seq.nextVal, 'hong', 'abc@naver.com', '1234', '첫방문입니다', 
	'반갑습니다. 앞으로 많으 격려와 지도편달 부탁드립니다.' );
insert into board(num, userid, email, pass, title, content)
values(board_seq.nextVal, 'somi', 'adddnaver.com', '1234', '게시판 개설',
	'축하드립니다.  무궁한 발전을 기원할께요');
insert into board(num, userid, email, pass, title, content)
values(board_seq.nextVal, 'light', 'bnbn@naver.com', '1234', '돼지골마을',
	'돼지 삼겹살이 맛있습니다');
insert into board(num, userid, email, pass, title, content)
values(board_seq.nextVal, 'hana', 'hana@daum.net', '1234', '2022년 여름' , 
	'몸시 추울꺼 같아요... 다들 건강 유의 하세요....');


	
	
	
create table reply(
	replynum number(7) primary key,    -- 댓글 순번
	boardnum number(5),                --  댓글의 해당 게시물 번호
	userid varchar2(20),                   -- 댓굴 쓰니
	writedate date default sysdate, -- 작성일
	content varchar2(1000)               -- 작성 내용
);

create sequence reply_seq start with 1 increment by 1;


insert into reply values( reply_seq.nextVal, 1, 'somi', sysdate, '게시판 개설을 축하드립니다');
insert into reply values( reply_seq.nextVal, 2, 'light', sysdate, '첫게시글 작성 감사합니다');
insert into reply values( reply_seq.nextVal, 3, 'scott', sysdate, '맛있게 보입니다');










