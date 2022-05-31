create table score_record
(
    record_id int auto_increment
        primary key,
    user_id   int        not null,
    count     int        null,
    timestamp mediumtext not null,
    type      int        null
);

create index score_record_user_id_index
    on score_record (user_id);

