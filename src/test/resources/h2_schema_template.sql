create table IF NOT EXISTS USERS
(
    ID          INT auto_increment,
    FIRSTNAME   VARCHAR(30) not null,
    SECONDNAME  VARCHAR(30) not null,
    SALARY      DOUBLE      not null,
    DATEOFBIRTH DATE        not null,
    constraint USERS_PK
        primary key (ID)
);

create unique index IF NOT EXISTS USERS_ID_UINDEX
    on USERS (ID);