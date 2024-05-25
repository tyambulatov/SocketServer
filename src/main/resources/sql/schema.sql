create table if not exists account (
    id bigserial primary key,
    login text not null,
    password text not null
);