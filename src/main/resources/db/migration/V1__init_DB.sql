create  table if not exists message (
    id int8 not null,
    content varchar(255),
    time timestamp,
    primary key (id));