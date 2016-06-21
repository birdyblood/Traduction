create schema if not exists foa authorization sa;
create table if not exists foa_key (id_key varchar(10) primary key, key_property varchar(500) unique, type_file varchar(3));
create unique index if not exists uniqueKey on foa_key (key_property); 