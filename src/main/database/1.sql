CREATE TABLE users (
  id MEDIUMINT NOT NULL AUTO_INCREMENT,
  email VARCHAR(45) NOT NULL UNIQUE,
  password VARCHAR(45) NOT NULL,
  enabled TINYINT NOT NULL DEFAULT 1,
  PRIMARY KEY (id),
  INDEX(email)
);

CREATE TABLE migrations (
  id SMALLINT NOT NULL,
  applied DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO migrations(id) VALUES(1);