SET SESSION FOREIGN_KEY_CHECKS=0;

/* Drop Tables */

DROP TABLE IF EXISTS likes;
DROP TABLE IF EXISTS nReply;
DROP TABLE IF EXISTS reply;
DROP TABLE IF EXISTS board;
DROP TABLE IF EXISTS users;




/* Create Tables */

CREATE TABLE board
(
	bid int NOT NULL AUTO_INCREMENT,
	title varchar(256) NOT NULL,
	content varchar(4000),
	uid varchar(12) NOT NULL,
	modTime datetime DEFAULT NOW(), SYSDATE(),
	isDeleted int DEFAULT 0,
	viewCount int DEFAULT 0,
	likeCount int DEFAULT 0,
	titleImage varchar(512),
	category varchar(30) NOT NULL,
	foodName varchar(30),
	openClosed varchar(20),
	address varchar(250),
	phoneNumber varchar(15),
	replyCount int DEFAULT 0,
	reviewStar int DEFAULT 1
	PRIMARY KEY (bid)
);


CREATE TABLE likes
(
	lid int NOT NULL AUTO_INCREMENT,
	uid varchar(12) NOT NULL,
	bid int NOT NULL,
	value int DEFAULT 0,
	PRIMARY KEY (lid)
);


CREATE TABLE nReply
(
	rid int NOT NULL AUTO_INCREMENT,
	comment varchar(256) NOT NULL,
	regTime datetime DEFAULT NOW(), SYSDATE(),
	uid varchar(12) NOT NULL,
	bid int NOT NULL,
	rid int NOT NULL,
	isMine int DEFAULT 0,
	isDeleted int(10) DEFAULT 0,
	PRIMARY KEY (rid)
);


CREATE TABLE reply
(
	rid int NOT NULL AUTO_INCREMENT,
	comment varchar(256) NOT NULL,
	regTime datetime DEFAULT NOW(), SYSDATE(),
	uid varchar(12) NOT NULL,
	bid int NOT NULL,
	isMine int DEFAULT 0,
	isDeleted int(10) DEFAULT 0,
	PRIMARY KEY (rid)
);


CREATE TABLE users
(
	uid varchar(12) NOT NULL,
	pwd char(60) NOT NULL,
	uname varchar(16) NOT NULL,
	email varchar(32),
	regDate date DEFAULT (CURRENT_DATE),
	isDeleted int DEFAULT 0,
	PRIMARY KEY (uid)
);



/* Create Foreign Keys */

ALTER TABLE likes
	ADD FOREIGN KEY (bid)
	REFERENCES board (bid)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE nReply
	ADD FOREIGN KEY (bid)
	REFERENCES board (bid)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE reply
	ADD FOREIGN KEY (bid)
	REFERENCES board (bid)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE nReply
	ADD FOREIGN KEY (rid)
	REFERENCES reply (rid)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE board
	ADD FOREIGN KEY (uid)
	REFERENCES users (uid)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE likes
	ADD FOREIGN KEY (uid)
	REFERENCES users (uid)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE nReply
	ADD FOREIGN KEY (uid)
	REFERENCES users (uid)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE reply
	ADD FOREIGN KEY (uid)
	REFERENCES users (uid)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;

CREATE TRIGGER update_nreply_isdeleted
AFTER UPDATE ON Reply
FOR EACH ROW
BEGIN
    IF NEW.isDeleted = 1 THEN
        UPDATE NReply SET isDeleted = 1 WHERE rid = NEW.rid;
    END IF;
END;

