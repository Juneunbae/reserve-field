alter table image
    drop column user_id;

alter table image
    modify name varchar(200) charset utf8 null;

alter table image
    add path nvarchar(200) null after name;