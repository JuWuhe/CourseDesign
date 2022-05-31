create table login_user
(
    user_id     int auto_increment
        primary key,
    username    varchar(255) not null,
    password    varchar(25)  null,
    information varchar(255) null,
    constraint login_user_username_uindex
        unique (username)
);

