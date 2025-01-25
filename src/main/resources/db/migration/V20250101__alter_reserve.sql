alter table reserve
    drop column available;

alter table reserve
    change reserve_time reserve_time_id bigint not null;