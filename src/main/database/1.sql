CREATE TABLE users (
  id MEDIUMINT NOT NULL AUTO_INCREMENT,
  email VARCHAR(45) NOT NULL UNIQUE,
  password VARCHAR(45) NOT NULL,
  enabled TINYINT NOT NULL DEFAULT 1,
  friendcode VARCHAR(9) NOT NULL,

  PRIMARY KEY (id),
  INDEX(email)
);

CREATE TABLE user_friends (
  id MEDIUMINT NOT NULL AUTO_INCREMENT,
  user_1 MEDIUMINT NOT NULL,
  user_2 MEDIUMINT NOT NULL,

  PRIMARY KEY(id),
  INDEX(user_1, user_2),
  FOREIGN KEY(user_1) REFERENCES users(id),
  FOREIGN KEY(user_2) REFERENCES users(id)
);

CREATE TABLE user_monsters (
  id BIGINT NOT NULL AUTO_INCREMENT,
  user_id MEDIUMINT NOT NULL,
  monster_id TINYINT NOT NULL,
  seen DATETIME NOT NULL,
  updated DATETIME NOT NULL,
  updated_by_owner DATETIME NULL,
  awakenings TINYINT NOT NULL,
  plus_hp TINYINT NOT NULL,
  plus_atk TINYINT NOT NULL,
  plus_rcv TINYINT NOT NULL,

  PRIMARY KEY(id),
  INDEX(user_id),
  FOREIGN KEY(user_id) REFERENCES users(id)
);

CREATE TABLE migrations (
  id SMALLINT NOT NULL,
  applied DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO migrations(id) VALUES(1);