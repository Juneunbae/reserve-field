CREATE TABLE user
(
    id                  BIGINT auto_increment
        primary key,
    email               VARCHAR(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci not null,
    password            VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci not null,
    name                VARCHAR(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci null,
    phone               VARCHAR(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  not null,
    role                VARCHAR(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  not null default 'PLAYER',
    status              VARCHAR(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  not null default 'WAITING',
    logged_in_at        DATETIME null,
    login_fail_count    INT null default 0,
    password_changed_at DATETIME null,
    created_at          DATETIME null,
    updated_at          DATETIME null
);