create table blood_sugar_record
(
    record_id int auto_increment
        primary key,
    user_id   int         not null,
    record    varchar(36) not null,
    timestamp mediumtext  null
);

create index blood_sugar_record_user_id_index
    on blood_sugar_record (user_id);

