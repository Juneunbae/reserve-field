ㅈcreate table reserve
(
    id           bigint auto_increment
        primary key,
    stadium_id   bigint       null,
    user_id      bigint       null,
    reserve_date date     not null,
    reserve_time nvarchar(20) not null,
    available    boolean      not null,
    status       nvarchar(10) null,
    created_at   datetime     null,
    created_by   bigint       null,
    updated_at   datetime     null,
    updated_by   bigint       null
);