/* ユーザーテーブル */
CREATE TABLE IF NOT EXISTS user(
	user_name VARCHAR(50) PRIMARY KEY,
	password VARCHAR(100),
	role VARCHAR(50)
);

/* 投稿テーブル */
CREATE TABLE IF NOT EXISTS bookmark(
	id INT PRIMARY KEY AUTO_INCREMENT,
	category VARCHAR(100),
	url VARCHAR(1000),
	comment VARCHAR(10000)
);