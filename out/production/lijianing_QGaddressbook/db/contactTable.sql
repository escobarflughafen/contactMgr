DROP TABLE
IF
	EXISTS 't_user';
CREATE TABLE 't_user' (
	username VARCHAR ( 40 ) NOT NULL UNIQUE COMMENT '用户名',
	PASSWORD VARCHAR ( 40 ) NOT NULL COMMENT '密码'
);
DROP TABLE
IF
	EXISTS 't_members';
CREATE TABLE 't_members' (
	id CHAR ( 10 ) NOT NULL PRIMARY KEY UNIQUE COMMENT 'ID',
	NAME CHAR ( 10 ) NOT NULL COMMENT '姓名',
	GROUP CHAR ( 10 ) NOT NULL COMMENT '方向',
	grade CHAR ( 10 ) NOT NULL COMMENT '年级',
	class CHAR ( 10 ) NOT NULL COMMENT '班级',
	phoneNum CHAR ( 20 ) NOT NULL COMMENT '电话号码',
	email CHAR ( 30 ) NOT NULL COMMENT '电邮地址',
	dormitory CHAR ( 10 ) NOT NULL COMMENT '宿舍',
	address CHAR ( 40 ) NOT NULL COMMENT '住址'
);
INSERT INTO 't_members'
VALUES
	( '1', 'lee', 'ml', '01', '02', '110', 'lee@lee.com', '351', '4-351' );

INSERT INTO 't_user'
VALUES
    ('admin','admin');