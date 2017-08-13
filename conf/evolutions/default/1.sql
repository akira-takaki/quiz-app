# --- !Ups

-- 小学校
CREATE TABLE Elementary_School (
  id int NOT NULL AUTO_INCREMENT,  -- ID
  name varchar(255) NOT NULL,      -- 名前
  PRIMARY KEY (id)
);
INSERT INTO Elementary_School VALUES (1, '席田小学校');
INSERT INTO Elementary_School VALUES (2, '月隈小学校');
INSERT INTO Elementary_School VALUES (3, '東月隈小学校');


-- 中学校
CREATE TABLE Junior_High_School (
  id int NOT NULL AUTO_INCREMENT,  -- ID
  name varchar(255) NOT NULL,      -- 名前
  PRIMARY KEY (id)
);
INSERT INTO Junior_High_School VALUES (1, '席田中学校');
INSERT INTO Junior_High_School VALUES (2, 'ABC中学校');
INSERT INTO Junior_High_School VALUES (3, 'XYZ中学校');


-- チーム
CREATE TABLE Team (
  id int NOT NULL AUTO_INCREMENT,  -- ID
  name varchar(255) NOT NULL,      -- 名前
  PRIMARY KEY (id)
);
INSERT INTO Team VALUES (1, '席田っ娘');
INSERT INTO Team VALUES (2, 'あああチーム');
INSERT INTO Team VALUES (3, 'いいいチーム');
INSERT INTO Team VALUES (4, 'うううチーム');


-- チームメンバー
CREATE TABLE Team_Member (
  id int NOT NULL AUTO_INCREMENT,  -- ID
  term_id int NOT NULL,            -- チームID
  name varchar(255) NOT NULL,      -- 名前
  grade int NOT NULL,              -- 学年
  junior_high_school_id int,       -- 中学校ID
  elementary_school_id int,        -- 小学校ID
  PRIMARY KEY (id)
);
INSERT INTO Team_Member VALUES (1, 1, '石原 優唯', 1, 1, 2);
INSERT INTO Team_Member VALUES (2, 1, '松村 裕香', 1, 1, 2);
INSERT INTO Team_Member VALUES (3, 1, '梅野 美優樹', 1, 1, 3);
INSERT INTO Team_Member VALUES (4, 2, '名前1', 1, 2, 2);
INSERT INTO Team_Member VALUES (5, 2, '名前2', 1, 2, 2);
INSERT INTO Team_Member VALUES (6, 2, '名前3', 1, 2, 3);
INSERT INTO Team_Member VALUES (7, 3, '名前4', 1, 3, 2);
INSERT INTO Team_Member VALUES (8, 3, '名前5', 1, 3, 2);
INSERT INTO Team_Member VALUES (9, 3, '名前6', 1, 3, 3);
INSERT INTO Team_Member VALUES (10, 4, '名前7', 1, 3, 2);
INSERT INTO Team_Member VALUES (11, 4, '名前8', 1, 3, 2);
INSERT INTO Team_Member VALUES (12, 4, '名前9', 1, 3, 3);


-- クイズ分類
CREATE TABLE Quiz_Category (
  id int NOT NULL AUTO_INCREMENT,  -- ID
  seq int NOT NULL,                -- 出題順番
  name varchar(255) NOT NULL,      -- 名前
  PRIMARY KEY (id)
);
INSERT INTO Quiz_Category VALUES (1, 1, '⭕️❌クイズ');


-- クイズ
CREATE TABLE Quiz (
  id int NOT NULL AUTO_INCREMENT,  -- ID
  quiz_category_id int NOT NULL,   -- クイズ分類ID
  seq int NOT NULL,                -- 出題順番
  question varchar(255) NOT NULL,  -- 問題
  answer varchar(255) NOT NULL,    -- 答え
  PRIMARY KEY (id)
);
INSERT INTO Quiz VALUES (1, 1, 1, '絶対零度は 摂氏 -273度 である。' , '◯');
INSERT INTO Quiz VALUES (2, 1, 2, '問題1' , '◯');
INSERT INTO Quiz VALUES (3, 1, 3, '問題2' , '×');
INSERT INTO Quiz VALUES (4, 1, 4, '問題3' , '×');
INSERT INTO Quiz VALUES (5, 1, 5, '問題4' , '◯');


-- 結果
CREATE TABLE Quiz_Result (
  team_id int NOT NULL,           -- チームID
  quiz_id int NOT NULL,           -- クイズID
  answer varchar(255) NOT NULL,   -- 回答
  point int NOT NULL,             -- 獲得ポイント
  PRIMARY KEY (team_id, quiz_id)
);


# --- !Downs

DROP TABLE Result;
DROP TABLE Quiz;
DROP TABLE Quiz_Category;
DROP TABLE Team_Member;
DROP TABLE Team;
DROP TABLE Junior_High_School;
DROP TABLE Elementary_School;

