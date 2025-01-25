create table review
(
    id              bigint auto_increment
        primary key,
    user_id         bigint        not null,
    stadium_id      bigint        not null,
    reserve_id      bigint        not null,
    reserve_date    date          not null,
    reserve_time_id bigint        not null,
    satisfaction    nvarchar(10)  not null,
    content         nvarchar(200) null,
    created_at      datetime      null
);