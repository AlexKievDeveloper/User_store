create table users
(
    id          INT auto_increment,
    firstName   VARCHAR(30) not null,
    secondName  VARCHAR(30) not null,
    salary      DOUBLE      not null,
    dateOfBirth DATE        not null,
    constraint USERS_PK
        primary key (id)
);

create unique index USERS_ID_UINDEX
    on users (id);

INSERT INTO users (firstName, dateOfBirth, salary, secondName) VALUES ('Kirill', '1993-06-23', 2000.0, 'Mavrody');
INSERT INTO users (firstName, dateOfBirth, salary, secondName) VALUES ('Kirill', '1993-06-23', 2000.0, 'Mavrody');
INSERT INTO users (firstName, dateOfBirth, salary, secondName) VALUES ('Kirill', '1993-06-23', 2000.0, 'Mavrody');
INSERT INTO users (firstName, dateOfBirth, salary, secondName) VALUES ('Kirill', '1993-06-23', 2000.0, 'Mavrody');
INSERT INTO users (firstName, dateOfBirth, salary, secondName) VALUES ('Kirill', '1993-06-23', 2000.0, 'Mavrody');
