-- TABLE DROP
DROP TABLE MEMBER;
-- SEQUENCE DROP
DROP SEQUENCE SEQ_USERNO;

CREATE TABLE MEMBER (
    USERNO NUMBER PRIMARY KEY,                  -- ȸ����ȣ
    USERID VARCHAR2(15) NOT NULL UNIQUE,        -- ȸ�����̵�
    USERPWD VARCHAR2(15) NOT NULL,              -- ȸ�����
    USERNAME VARCHAR2(20) NOT NULL,             -- ȸ���̸�
    GENDER CHAR(1) CHECK(GENDER IN ('M', 'F')), -- ����
    AGE NUMBER,                                 -- ����
    EMAIL VARCHAR2(30),                         -- �̸���
    PHONE CHAR(11), -- ¦�����°�               -- ����
    ADDRESS VARCHAR2(100),                      -- �ּ�
    HOBBY VARCHAR2(50),                         -- ���
    ENROLLDATE DATE DEFAULT SYSDATE NOT NULL    -- ȸ��������
);

SELECT * FROM MEMBER;

CREATE SEQUENCE SEQ_USERNO
NOCACHE;

INSERT INTO MEMBER
VALUES(SEQ_USERNO.NEXTVAL, 'admin', '1234', '������', 'M', 45, 'admin@iei.or.kr', '01012345555', '����', NULL, '2021/07/07');

INSERT INTO MEMBER
VALUES(SEQ_USERNO.NEXTVAL, 'user01', 'pass01', 'ȫ�浿', null, 23, 'user01@iei.or.kr', '01022221111', '�λ�', '���, ��ȭ����', '2023/03/02');

COMMIT;

CREATE TABLE TEST(
    TNO NUMBER,
    TNAME VARCHAR2(20),
    TDATE DATE
);

SELECT * FROM TEST;

















