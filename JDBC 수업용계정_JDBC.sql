INSERT INTO MEMBER VALUES(SEQ_USERNO.NEXTVAL, 'XXX', 'XXX', 'XXX', 'X', XX, 'XXX', 'XX', 'XX', 'XX', SYSDATE);

INSERT INTO MEMBER VALUES(SEQ_USERNO.NEXTVAL, 'user04', 'user04', '�̸���', 'F', 12, 'a@a', '01022223333', '������', '���ڱ�!!!', SYSDATE);
COMMIT;

select * from member;

UPDATE MEMBER
SET USERNAME = '�̸���'
WHERE USERNO = 3;

COMMIT;

SELECT * FROM MEMBER;

UPDATE MEMBER
    SET USERPWD = '',
        EMAIL ='',
        PHONE = '',
        ADDRESS = ''
    WHERE USERID = '';
    
    
    -- 4�� ȸ���̸����� �˻� ��û�� ����� sql��
    SELECT * FROM MEMBER WHERE USERNAME LIKE '%?%'; --> �̷��� ���� ���� �� ����ɱ�?
    
    -- 5�� ȸ�� ���� ���� ��û�� ����� SQL��
    UPDATE MEMBER SET USERPWD = ?, EMAIL = ?, PHONE = ?, ADDRESS = ? WHERE USERID = ?;
    
    -- 6�� ȸ�� Ż�� ��û�� ����� SQL��
    DELETE FROM MEMBER WHERE USERID = ?;
    
    
    