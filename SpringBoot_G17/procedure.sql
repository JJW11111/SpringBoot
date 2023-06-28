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




CREATE OR REPLACE PROCEDURE getMember(
    p_id IN member.id%type,
    p_curvar OUT SYS_REFCURSOR
)
IS
BEGIN
    OPEN p_curvar For SELECT * FROM member WHERE id=p_id;
END;



CREATE OR REPLACE PROCEDURE joinKakao(
    p_id IN member.id%TYPE,
    p_name IN member.name%TYPE,
    p_email IN member.email%TYPE,
    p_provider IN member.provider%TYPE
)
IS
BEGIN
    INSERT INTO member( id, name, email, provider)
    VALUES( p_id , p_name , p_email , p_provider);
    COMMIT;
END;






CREATE OR REPLACE PROCEDURE insertMember(
    p_id IN member.id%TYPE ,
    p_name IN member.name%TYPE ,
    p_pwd IN member.pwd%TYPE ,
    p_email IN member.email%TYPE ,
    p_phone IN member.phone%TYPE,
    p_zip_num IN member.zip_num%TYPE,
    p_address1 IN member.address1%TYPE,
    p_address2 IN member.address2%TYPE,
    p_address3 IN member.address3%TYPE
)
IS
BEGIN
    INSERT INTO member( id, pwd, name, email, phone , zip_num, address1, address2, address3 )
    VALUES( p_id, p_pwd, p_name, p_email, p_phone, p_zip_num, p_address1, p_address2, p_address3 );
    COMMIT;
END;




CREATE OR REPLACE PROCEDURE updateMember(
    p_id IN member.id%TYPE ,
    p_pwd IN member.pwd%TYPE ,
    p_name IN member.name%TYPE ,
    p_email IN member.email%TYPE ,
    p_phone IN member.phone%TYPE,
    p_zip_num IN member.zip_num%TYPE,
    p_address1 IN member.address1%TYPE,
    p_address2 IN member.address2%TYPE,
    p_address3 IN member.address3%TYPE  )
IS
BEGIN
    UPDATE member SET pwd=p_pwd, name=p_name, email=p_email, phone=p_phone,
    zip_num = p_zip_num, address1=p_address1, address2=p_address2, address3=p_address3
    WHERE id=p_id;
    COMMIT;
END;

SELECT * FROM MEMBER;


CREATE OR REPLACE PROCEDURE getKindList(
    p_kind IN product.kind%TYPE, 
    p_cur OUT SYS_REFCURSOR   )
IS
BEGIN
    OPEN p_cur FOR SELECT * FROM product where kind=p_kind;
END;



CREATE OR REPLACE PROCEDURE getProduct(
    p_pseq IN product.pseq%TYPE, 
    p_cur OUT SYS_REFCURSOR   )
IS
BEGIN
    OPEN p_cur FOR SELECT * FROM product where pseq=p_pseq;
END;





CREATE OR REPLACE PROCEDURE insertCart(
    p_id IN cart.id%TYPE,
    p_pseq  IN cart.pseq%TYPE,
    p_quantity  IN cart.quantity%TYPE )
IS
BEGIN
    INSERT INTO cart( cseq, id, pseq, quantity ) 
    VALUES( cart_seq.nextVal, p_id, p_pseq, p_quantity );
    COMMIT;    
END;



CREATE OR REPLACE PROCEDURE listCart(
    p_id IN cart.id%TYPE, 
    p_cur OUT SYS_REFCURSOR   )
IS
BEGIN
    OPEN p_cur FOR SELECT * FROM cart_view where id=p_id;
END;






CREATE OR REPLACE PROCEDURE insertOrder(
    p_id IN orders.id%TYPE,
    p_oseq OUT orders.oseq%TYPE
)
IS
    v_oseq orders.oseq%TYPE;
    cart_cur SYS_REFCURSOR;
    v_pseq cart.pseq%TYPE;
    v_quantity cart.quantity%TYPE;
    v_cseq cart.cseq%TYPE;
BEGIN
    -- orders 테이블에 레코드를 추가합니다.
    INSERT INTO orders(oseq, id) VALUES( orders_seq.nextVal, p_id);
    -- orders 테이블에 방금 추가된 레코드의  oseq 를 조회해서 변수에 담습니다.
    SELECT max(oseq) INTO v_oseq FROM orders;
    -- cart 테이블에서 주문할 상품의 상품번호, 수량을 조회해서 커서변수에 담습니다
    OPEN cart_cur FOR SELECT cseq, pseq, quantity FROM cart WHERE id=p_id;
    LOOP  -- 반복실행문으로 cart_cur 의 담긴내용들을 하나씩 꺼내서 작업합니다
        FETCH cart_cur INTO v_cseq, v_pseq, v_quantity;    -- 커서에서 레코드(cseq, pseq, quantity)하나 추출
        EXIT WHEN cart_cur%NOTFOUND;
        INSERT INTO order_detail( odseq, oseq, pseq, quantity )
        VALUES( order_detail_seq.nextVal, v_oseq, v_pseq, v_quantity );  -- order_detail에 레코드 추가
        DELETE FROM CART WHERE cseq=v_cseq;
    END LOOP;
    COMMIT;
    p_oseq := v_oseq;
EXCEPTION WHEN OTHERS THEN
    ROLLBACK;
END;






CREATE OR REPLACE PROCEDURE listOrderByOseq(
    p_oseq IN orders.oseq%TYPE, 
    p_cur OUT SYS_REFCURSOR   )
IS
BEGIN
    OPEN p_cur FOR SELECT * FROM order_view where oseq=p_oseq ORDER BY RESULT;
END;






CREATE OR REPLACE PROCEDURE insertOrderOne(
    p_id IN ORDERS.ID%TYPE ,
    p_pseq IN ORDER_DETAIL.PSEQ%TYPE ,
    p_quantity IN ORDER_DETAIL.QUANTITY%TYPE ,
    p_oseq OUT  ORDERS.OSEQ%TYPE 
)
IS
    v_oseq ORDERS.OSEQ%TYPE;
BEGIN
    INSERT INTO ORDERS(oseq, id) VALUES( orders_seq.nextVal, p_id);
    SELECT MAX(oseq) INTO v_oseq FROM ORDERS;
    INSERT INTO ORDER_DETAIL( odseq, oseq, pseq, quantity)
    VALUES( order_detail_seq.nextVal, v_oseq, p_pseq, p_quantity);
    p_oseq := v_oseq;
    COMMIT;
EXCEPTION WHEN OTHERS THEN
    ROLLBACK;
END;



CREATE OR REPLACE PROCEDURE listOrderByIdIng(
    p_id IN orders.ID%TYPE, 
    p_rc OUT SYS_REFCURSOR   )
IS
BEGIN
    OPEN p_rc FOR 
    SELECT DISTINCT oseq FROM order_view where id=p_id AND result<>'4' ORDER BY OSEQ DESC;
END;



CREATE OR REPLACE PROCEDURE listOrderByIdAll(
    p_id IN orders.ID%TYPE, 
    p_rc OUT SYS_REFCURSOR   )
IS
BEGIN
    OPEN p_rc FOR 
    SELECT DISTINCT oseq FROM order_view where id=p_id ORDER BY OSEQ DESC;
END;

CREATE OR REPLACE PROCEDURE listQna(
     p_rc OUT SYS_REFCURSOR   )
IS
BEGIN
OPEN p_rc FOR 
    SELECT*FROM QNA ORDER BY qseq DESC;
END;


CREATE OR REPLACE PROCEDURE getQNa(
     p_qseq IN QNA.qseq%TYPE,
     p_rc OUT SYS_REFCURSOR   )
IS
BEGIN
OPEN p_rc FOR 
    SELECT*FROM QNA where qseq=p_qseq;
END;



CREATE OR REPLACE PROCEDURE insertQna(
    p_id IN qna.id%TYPE ,
    p_check IN qna.passcheck%TYPE ,
    p_pass IN qna.pass%TYPE ,
    p_subject IN qna.subject%TYPE ,
    p_content IN qna.content%TYPE 
)
IS
BEGIN
    INSERT INTO qna( qseq, id, passcheck, pass, subject, content )
    VALUES( qna_seq.nextVal, p_id, p_check, p_pass, p_subject, p_content );
    COMMIT;
END;


