INSERT INTO MEMBER VALUES(SEQ_USERNO.NEXTVAL, 'XXX', 'XXX', 'XXX', 'X', XX, 'XXX', 'XX', 'XX', 'XX', SYSDATE);

INSERT INTO MEMBER VALUES(SEQ_USERNO.NEXTVAL, 'user04', 'user04', '이뫄뫄', 'F', 12, 'a@a', '01022223333', '내맘속', '잠자기!!!', SYSDATE);
COMMIT;

select * from member;

UPDATE MEMBER
SET USERNAME = '이뫄뫄'
WHERE USERNO = 3;

COMMIT;

SELECT * FROM MEMBER;

UPDATE MEMBER
    SET USERPWD = '',
        EMAIL ='',
        PHONE = '',
        ADDRESS = ''
    WHERE USERID = '';
    
    
    -- 4번 회원이름으로 검색 요청시 실행될 sql문
    SELECT * FROM MEMBER WHERE USERNAME LIKE '%?%'; --> 이렇게 했을 때는 잘 실행될까?
    
    -- 5번 회원 정보 변경 요청시 실행될 SQL문
    UPDATE MEMBER SET USERPWD = ?, EMAIL = ?, PHONE = ?, ADDRESS = ? WHERE USERID = ?;
    
    -- 6번 회원 탈퇴 요청시 실행될 SQL문
    DELETE FROM MEMBER WHERE USERID = ?;
    
    
    