CREATE TABLE users (
  id MEDIUMINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  email VARCHAR(50) NULL UNIQUE,
  name VARCHAR(50) NOT NULL UNIQUE,
  password VARCHAR(64) NOT NULL,
  friendcode VARCHAR(12) NOT NULL UNIQUE,
  enabled TINYINT NOT NULL DEFAULT 1,

  INDEX(email),
  INDEX(friendcode)
);

CREATE TABLE user_authorities (
  id MEDIUMINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  user_id MEDIUMINT NOT NULL,
  authority VARCHAR(50) NOT NULL,
  FOREIGN KEY (user_id) REFERENCES users(id),
  UNIQUE INDEX ix_auth_user(user_id, authority)
);

CREATE TABLE user_friends (
  id MEDIUMINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  user_1 MEDIUMINT NOT NULL,
  user_2 MEDIUMINT NOT NULL,
  bestfriend BOOLEAN NOT NULL DEFAULT 0,

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
  FOREIGN KEY(user_id) REFERENCES users(id),
  UNIQUE INDEX ix_user_monster(user_id, monster_id)
);

CREATE TABLE migrations (
  id SMALLINT NOT NULL UNIQUE,
  applied DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO migrations(id) VALUES(1);