CREATE TABLE user
(
    id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    login VARCHAR(70),
    password VARCHAR(70),
    active BOOLEAN,
    role VARCHAR(10),
    CONSTRAINT role_type_check CHECK(role IN ('USER', 'ADMIN'))
);

INSERT INTO user values (1,'admin','admin',true,'ADMIN');
INSERT INTO user values (2,'user','user',true,'USER');