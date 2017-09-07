# --- !Ups

-- 小学校
CREATE TABLE Elementary_School (
  id int NOT NULL AUTO_INCREMENT,  -- ID
  name varchar(255) NOT NULL,      -- 名前
  PRIMARY KEY (id)
);
INSERT INTO Elementary_School VALUES (1, '那珂南小学校');
INSERT INTO Elementary_School VALUES (2, '御供所小学校');
INSERT INTO Elementary_School VALUES (3, '大浜小学校');
INSERT INTO Elementary_School VALUES (4, '奈良屋小学校');
INSERT INTO Elementary_School VALUES (5, '千代小学校');
INSERT INTO Elementary_School VALUES (6, '板付小学校');
INSERT INTO Elementary_School VALUES (7, '美野島小学校');
INSERT INTO Elementary_School VALUES (8, '住吉小学校');
INSERT INTO Elementary_School VALUES (9, '東光小学校');
INSERT INTO Elementary_School VALUES (10, '月隈小学校');
INSERT INTO Elementary_School VALUES (11, '東月隈小学校');
INSERT INTO Elementary_School VALUES (12, '東吉塚小学校');
INSERT INTO Elementary_School VALUES (13, '吉塚小学校');


-- 中学校
CREATE TABLE Junior_High_School (
  id int NOT NULL AUTO_INCREMENT,  -- ID
  name varchar(255) NOT NULL,      -- 名前
  PRIMARY KEY (id)
);
INSERT INTO Junior_High_School VALUES (1, '三筑中学校');
INSERT INTO Junior_High_School VALUES (2, '博多中学校');
INSERT INTO Junior_High_School VALUES (3, '千代中学校');
INSERT INTO Junior_High_School VALUES (4, '板付中学校');
INSERT INTO Junior_High_School VALUES (5, '住吉中学校');
INSERT INTO Junior_High_School VALUES (6, '東光中学校');
INSERT INTO Junior_High_School VALUES (7, '席田中学校');
INSERT INTO Junior_High_School VALUES (8, '吉塚中学校');


-- チーム
CREATE TABLE Team (
  id int NOT NULL AUTO_INCREMENT,  -- ID
  name varchar(255) NOT NULL,      -- 名前
  PRIMARY KEY (id)
);


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


-- クイズ分類
CREATE TABLE Quiz_Category (
  id int NOT NULL AUTO_INCREMENT,  -- ID
  seq int NOT NULL,                -- 出題順番
  name varchar(255) NOT NULL,      -- 名前
  PRIMARY KEY (id)
);
INSERT INTO Quiz_Category VALUES (1, 1, '⭕️❌クイズ');
INSERT INTO Quiz_Category VALUES (2, 2, '三連馬跳び+クイズ');
INSERT INTO Quiz_Category VALUES (3, 3, 'フリースロー+クイズ');
INSERT INTO Quiz_Category VALUES (4, 4, 'リレー伝言ゲーム');
INSERT INTO Quiz_Category VALUES (5, 5, '運命のジャンケン');
INSERT INTO Quiz_Category VALUES (6, 6, 'ファイナルステージ');


-- クイズ
CREATE TABLE Quiz (
  id int NOT NULL AUTO_INCREMENT,  -- ID
  quiz_category_id int NOT NULL,   -- クイズ分類ID
  seq int NOT NULL,                -- 出題順番
  question varchar(255) NOT NULL,  -- 問題
  answer varchar(255) NOT NULL,    -- 答え
  PRIMARY KEY (id)
);
INSERT INTO Quiz VALUES (1, 1, 1, '2015年7月発売のドリカムのベストアルバム『私のドリカム』のDisc3の6番目の収録曲は『何度でも』である。' , '◯');
INSERT INTO Quiz VALUES (2, 1, 2, '長崎県対馬の方言で、『ぱる』とは『走る』という意味である。' , '×');
INSERT INTO Quiz VALUES (3, 1, 3, '2016年3月14日は金曜日である。' , '×');
INSERT INTO Quiz VALUES (4, 1, 4, '「モモ」「チャウミン」「セクワ」「サモサ」は、ベトナム料理の名前である。' , '×');
INSERT INTO Quiz VALUES (5, 1, 5, '現在、日本で製造・発行されている50円硬貨の表にデザインされている菊の花は、3輪である。' , '◯');
INSERT INTO Quiz VALUES (6, 1, 6, '2015年時点で西日本鉄道が保有しているバスの台数は日本一である。' , '×');
INSERT INTO Quiz VALUES (7, 1, 7, '福岡市博多区の人口は福岡市中央区の人口よりも多い。' , '◯');
INSERT INTO Quiz VALUES (8, 1, 8, '福岡市博多区と中央区のオフィスビルは、半分以上のオフィスビルが満室である。' , '◯');
INSERT INTO Quiz VALUES (9, 1, 9, '福岡市の公立の小中一貫校は現在6校ありますが、博多区は住吉小中学校のみである。' , '◯');
INSERT INTO Quiz VALUES (10, 1, 10, '博多弁の「しろしか～」は、「きれいである」の意味である。' , '×');
INSERT INTO Quiz VALUES (11, 1, 11, '「漢委奴国王（かんのわのなのこくおう）」と刻まれている金印は、江戸時代に発見された。' , '◯');
INSERT INTO Quiz VALUES (12, 1, 12, '福岡大空襲は昭和20年6月18日におこった。' , '×');
INSERT INTO Quiz VALUES (13, 1, 13, '福岡藩初代藩主は黒田官兵衛である。' , '×');
INSERT INTO Quiz VALUES (14, 1, 14, '象の鼻の穴は、1つである。' , '×');
INSERT INTO Quiz VALUES (15, 1, 15, '象は一生のうち、6回歯が生え変わる。' , '◯');


-- 結果
CREATE TABLE Quiz_Result (
  team_id int NOT NULL,           -- チームID
  quiz_id int NOT NULL,           -- クイズID
  answer varchar(255) NOT NULL,   -- 回答
  point int NOT NULL,             -- 獲得ポイント
  PRIMARY KEY (team_id, quiz_id)
);


# --- !Downs

DROP TABLE Quiz_Result;
DROP TABLE Quiz;
DROP TABLE Quiz_Category;
DROP TABLE Team_Member;
DROP TABLE Team;
DROP TABLE Junior_High_School;
DROP TABLE Elementary_School;

