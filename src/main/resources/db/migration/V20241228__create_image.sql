create table image
(
    id           bigint auto_increment
        primary key,
    user_id      bigint       not null,
    stadium_id   bigint       not null,
    name         nvarchar(50) null,
    is_thumbnail boolean      not null,
    created_at   datetime     null,
    created_by   bigint       null,
    updated_at   datetime     null,
    updated_by   bigint       null
);