CREATE TABLE user_roles
(
    id   INTEGER PRIMARY KEY,
    role VARCHAR NOT NULL
);

CREATE TABLE user_table
(
    id         SERIAL PRIMARY KEY,
    role_id    INTEGER NOT NULL,
    first_name VARCHAR NOT NULL,
    last_name  VARCHAR NOT NULL,
    nickname   VARCHAR NOT NULL,
    email      VARCHAR NOT NULL,
    password   VARCHAR NOT NULL,
    FOREIGN KEY (role_id) REFERENCES user_roles (id) ON DELETE CASCADE
);

INSERT INTO user_roles VALUES (1, 'ROLE_ADMIN'), (2, 'ROLE_USER')