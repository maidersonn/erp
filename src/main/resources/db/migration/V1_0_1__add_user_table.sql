create table if not exists users (
    id serial NOT NULL PRIMARY KEY,
    username varchar(20) not null unique,
    password varchar(100) not null,
    roles varchar(100)[] not null
)