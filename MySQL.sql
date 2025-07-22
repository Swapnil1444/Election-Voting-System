CREATE DATABASE IF NOT EXISTS election_db;
USE election_db;

CREATE TABLE IF NOT EXISTS candidates (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    votes INT DEFAULT 0
);

CREATE TABLE IF NOT EXISTS voters (
    voter_id VARCHAR(50) PRIMARY KEY
);
select * from voters;
select * from candidates;
TRUNCATE TABLE voters;
TRUNCATE TABLE candidates;

CREATE TABLE candidates (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100),
    age INT,
    address VARCHAR(255),
    votes INT DEFAULT 0
);

CREATE TABLE voters (
    voter_id VARCHAR(50) PRIMARY KEY
);

ALTER TABLE candidates ADD COLUMN age INT;
ALTER TABLE candidates ADD COLUMN address VARCHAR(255);




