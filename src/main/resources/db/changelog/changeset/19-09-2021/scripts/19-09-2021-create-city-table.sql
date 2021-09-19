create table city
(
    id        bigserial                not null primary key,
    region_id bigint                   not null REFERENCES region (id),
    name      character varying(128)   not null,
    crt_date  TIMESTAMP with time zone NOT NULL DEFAULT NOW()
);