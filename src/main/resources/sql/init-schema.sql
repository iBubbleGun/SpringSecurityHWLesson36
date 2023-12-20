DROP TABLE IF EXISTS products CASCADE;
CREATE TABLE products
(
    id   BIGINT AUTO_INCREMENT NOT NULL,
    name VARCHAR(255)          NULL,
    cost DOUBLE                NULL,
    CONSTRAINT pk_products PRIMARY KEY (id)
);

DROP TABLE IF EXISTS user_role CASCADE;
CREATE TABLE user_role
(
    role_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL
);

DROP TABLE IF EXISTS users CASCADE;
CREATE TABLE users
(
    id       BIGINT AUTO_INCREMENT NOT NULL,
    name     VARCHAR(255)          NULL,
    password VARCHAR(255)          NULL,
    CONSTRAINT pk_users PRIMARY KEY (id)
);

DROP TABLE IF EXISTS roles CASCADE;
CREATE TABLE roles
(
    id   BIGINT AUTO_INCREMENT NOT NULL,
    name VARCHAR(255)          NULL,
    CONSTRAINT pk_roles PRIMARY KEY (id)
);

ALTER TABLE user_role
    ADD CONSTRAINT uc_user_role_role UNIQUE (role_id);

ALTER TABLE user_role
    ADD CONSTRAINT fk_user_role_on_role FOREIGN KEY (role_id) REFERENCES roles (id);

ALTER TABLE user_role
    ADD CONSTRAINT fk_user_role_on_user FOREIGN KEY (user_id) REFERENCES users (id);
