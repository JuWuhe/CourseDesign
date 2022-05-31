create table bfz_record
(
    record_id int auto_increment
        primary key,
    user_id   int          not null,
    record    varchar(255) null,
    timestamp mediumtext   null
);

