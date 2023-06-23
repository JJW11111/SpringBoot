CREATE OR REPLACE PROCEDURE selectBoard(
    p_startNum IN NUMBER,
    p_endNum IN NUMBER,
    p_cursor OUT SYS_REFCURSOR
)
IS
    temp_cur SYS_REFCURSOR; -- 게시물 번호만 조회한 결과를 담을 커서변수
    v_num NUMBER; -- 그들의 게시물 번호들을 번갈아가며 저장할 변수
    v_rownum NUMBER; -- 그들의 행번호들을 번갈아가며 저장할 변수
    v_cnt NUMBER; -- 각 게시물 번호로 조회한 댓글갯수를 저장할 변수
BEGIN
    -- board 테이블에서 startNum 과 endNum 사이의 게시물을 조회하되, 게시물 번호(num) 값만 취합니다(ROWNUM도 같이)
    -- num 값으로 reply 테이블에서 boardnum 이 num 인 레코드가 몇개인지 갯수를 구함
    -- num 값과 댓글 갯수를 이용해서 board 테이블의 replycnt 필드를 update함
    OPEN temp_cur FOR
        SELECT*FROM(
            SELECT*FROM(
                SELECT rownum as rn, b.* FROM( (SELECT*FROM board ORDER BY num DESC) b)
            )WHERE rn>=v_startNum
        )WHERE rn<=v_endNum;
    LOOP
        FETCH temp_cur INTO v_rownum, v_num;
        EXIT WHEN temp_cur%NOTFOUND;
        SELECT count(*) INTO v_cnt FROM reply WHERE boardnum = v_num;
        UPDATE board SET replycnt = v_cnt WHERE num=v_num;
    END LOOP;
    COMMIT;
    
    -- 댓글갯수가 채워진 대상 게시물을 조회해서 p_cursor에 담음
    OPEN p_cursor FOR
        SELECT*FROM(
            SELECT*FROM(
                SELECT rownum as rn, b.* FROM((SELECT*FROM board ORDER BY num DESC) b)
            )WHERE rn>=v_startNum
        )WHERE rn<=v_endNum;
        
END;

SELECT * FROM BOARD;
ALTER TABLE BOARD ADD REPLYCNT NUMBER(5);