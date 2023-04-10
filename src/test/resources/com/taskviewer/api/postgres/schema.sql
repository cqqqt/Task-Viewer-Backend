drop table if exists login cascade;
drop table if exists task cascade;

create table if not exists login(
    id bigserial primary key ,
    username character varying(32) unique not null,
    firstname character varying(32) not null,
    lastname character varying(32) not null,
    password character varying(512) not null,
    role character varying(16) not null,
    email character varying(256) unique not null,
    created timestamptz not null default now()
);

create table if not exists task (
    id bigserial primary key,
    title character varying(128) not null,
    status character varying(32) not null,
    assigne bigint references login(id),
    priority int,
    about character varying(512),
    estimate timestamptz not null,
    tracked timestamptz ,
    created timestamptz not null default now(),
    updated timestamptz not null default now()
);
