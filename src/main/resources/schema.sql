-- WORKSPACE Table Create SQL
CREATE TABLE MEMBER
(
    `MEMBER_ID`        BIGINT          NOT NULL    AUTO_INCREMENT,
    `CREATE_TIME`      TIMESTAMP       NOT NULL    DEFAULT current_timestamp,
    `UPDATE_TIME`      TIMESTAMP       NOT NULL    DEFAULT current_timestamp,
    `EMAIL`            VARCHAR(100)    NOT NULL,
    `INTRODUCTION`     VARCHAR(500)    NOT NULL,
    `MBTI`             VARCHAR(10)     NOT NULL,
    `MEMBER_TYPE`      VARCHAR(10)     NOT NULL,
    `NICKNAME`         VARCHAR(20)     NOT NULL,
    `PASSWORD`         VARCHAR(200)    NOT NULL,
    `PROFILE_URL`      VARCHAR(200)    NOT NULL,
    `IS_DELETED`       BOOLEAN(1)      NOT NULL,
    PRIMARY KEY (MEMBER_ID)
);