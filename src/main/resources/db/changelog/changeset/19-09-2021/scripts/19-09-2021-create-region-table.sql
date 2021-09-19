create table region
(
    id         bigint                   not null primary key,
    country_id bigint                   not null REFERENCES country (id),
    name       character varying(128)   not null,
    crt_date   TIMESTAMP with time zone NOT NULL DEFAULT NOW()
);