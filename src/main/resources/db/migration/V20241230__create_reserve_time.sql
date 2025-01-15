create table reserve_time
(
    id         bigint auto_increment
        primary key,
    name       nvarchar(20) not null,
    description nvarchar(20) not null,
    created_at datetime     null default CURRENT_TIMESTAMP
);