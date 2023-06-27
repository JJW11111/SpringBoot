CREATE OR REPLACE PROCEDURE getBestNewBannerList(
    p_cur1 OUT SYS_REFCURSOR,
    p_cur2 OUT SYS_REFCURSOR,
    p_cur3 OUT SYS_REFCURSOR
)
IS
BEGIN
    OPEN p_cur1 FOR
        SELECT * FROM new_pro_view;
    OPEN p_cur2 FOR
        SELECT * FROM best_pro_view;
    OPEN p_cur3 FOR
        SELECT * FROM banner WHERE order_seq<=5 ORDER BY order_seq;
END;

select*from banner;
delete from banner;

create sequence banner_seq increment by 1 start with 1;





CREATE OR REPLACE PROCEDURE getMember(
    p_id IN member.id%type,
    p_curvar OUT SYS_REFCURSOR
)
IS
BEGIN
    OPEN p_curvar For SELECT*FROM member WHERE id=p_id;
END;


膏呕包府
膏呕包府
100%
11
A17:C20

create or replace PROCEDURE joinKakao(
    p_id member.id%type,
    p_email member.email%type,
    p_name member.name%type,
    p_provider member.provider%type
)
IS
BEGIN
    INSERT INTO member (userid, name, email, provider)
    VALUES(p_userid,p_email,p_name,p_provider);
    COMMIT;
END;
 
 
 	
create or replace PROCEDURE joinKakao(
    p_id IN member.id%type,
    p_email IN member.email%type,
    p_name IN member.name%type,
    p_provider IN member.provider%type
)
IS
BEGIN
    INSERT INTO member (id, name, email, provider)
    VALUES(p_id,p_name,p_email,p_provider);
    COMMIT;
END;
