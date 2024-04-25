
-- Création de l'utilisateur "application"

CREATE USER 'application'@'localhost' IDENTIFIED BY 'password';

-- -- Création de la base de données "quest_web"

CREATE DATABASE quest_web;

-- -- Octroi des droits sur la base de données "quest_web" à l'utilisateur "application"

GRANT ALL PRIVILEGES ON quest_web.* TO 'application'@'localhost';

-- Création de la table "user" dans la base de données "quest_web"

-- USE quest_web;

-- CREATE TABLE user (
--   id INT NOT NULL AUTO_INCREMENT,
--   username VARCHAR(255) UNIQUE NOT NULL,
--   password VARCHAR(255) NOT NULL,
--   role VARCHAR(255) DEFAULT "ROLE_USER",
--   creation_date DATETIME DEFAULT NULL,
--   updated_date DATETIME DEFAULT NULL,
--   PRIMARY KEY (id)
-- );