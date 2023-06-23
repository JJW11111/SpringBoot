create table bannerTest(
	bseq number(5),
	subject varchar2(30),
	ORDER_SEQ number(3) default 1,
	useyn char(1) default 'Y',
	indate date default sysdate,
	image varchar2(30),
	PRIMARY KEY (bseq)
);



create sequence bannerTest_seq start with 1;