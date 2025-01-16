create table reserve_history
(
    id                       bigint auto_increment
        primary key,
    user_id                  bigint       not null,
    user_name                nvarchar(50) not null,
    stadium_id               bigint       not null,
    stadium_name             nvarchar(30) not null,
    reserve_time_id          bigint       not null,
    reserve_time_description nvarchar(20) not null,
    reserve_date             date         not null,
    status                   nvarchar(10) not null,
    created_at               datetime     null
);