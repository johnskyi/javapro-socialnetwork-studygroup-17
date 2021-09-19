create table country
(
    id       bigint                   not null primary key,
    name     character varying(128)   not null,
    crt_date TIMESTAMP with time zone NOT NULL DEFAULT NOW()
);
