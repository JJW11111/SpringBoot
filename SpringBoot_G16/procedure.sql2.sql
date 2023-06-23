CREATE OR REPLACE PROCEDURE selectBoard(
    p_startNum IN NUMBER,
    p_endNum IN NUMBER,
    p_cursor OUT SYS_REFCURSOR
)
IS
    temp_cur SYS_REFCURSOR; -- �Խù� ��ȣ�� ��ȸ�� ����� ���� Ŀ������
    v_num NUMBER; -- �׵��� �Խù� ��ȣ���� �����ư��� ������ ����
    v_rownum NUMBER; -- �׵��� ���ȣ���� �����ư��� ������ ����
    v_cnt NUMBER; -- �� �Խù� ��ȣ�� ��ȸ�� ��۰����� ������ ����
BEGIN
    -- board ���̺��� startNum �� endNum ������ �Խù��� ��ȸ�ϵ�, �Խù� ��ȣ(num) ���� ���մϴ�(ROWNUM�� ����)
    -- num ������ reply ���̺��� boardnum �� num �� ���ڵ尡 ����� ������ ����
    -- num ���� ��� ������ �̿��ؼ� board ���̺��� replycnt �ʵ带 update��
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
    
    -- ��۰����� ä���� ��� �Խù��� ��ȸ�ؼ� p_cursor�� ����
    OPEN p_cursor FOR
        SELECT*FROM(
            SELECT*FROM(
                SELECT rownum as rn, b.* FROM((SELECT*FROM board ORDER BY num DESC) b)
            )WHERE rn>=v_startNum
        )WHERE rn<=v_endNum;
        
END;

SELECT * FROM BOARD;
ALTER TABLE BOARD ADD REPLYCNT NUMBER(5);