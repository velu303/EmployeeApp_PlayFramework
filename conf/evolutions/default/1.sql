# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table addresses (
  id                            bigint auto_increment not null,
  street_address                varchar(255),
  city                          varchar(255),
  state                         varchar(255),
  country                       varchar(255),
  zip_code                      integer,
  user_email                    varchar(255),
  constraint uq_addresses_user_email unique (user_email),
  constraint pk_addresses primary key (id)
);

create table employees (
  id                            integer auto_increment not null,
  employee_name                 varchar(255),
  employee_role                 varchar(255),
  constraint pk_employees primary key (id)
);

create table users (
  email                         varchar(255) not null,
  name                          varchar(255),
  password                      varchar(255),
  constraint pk_users primary key (email)
);

alter table addresses add constraint fk_addresses_user_email foreign key (user_email) references users (email) on delete restrict on update restrict;


# --- !Downs

alter table addresses drop constraint if exists fk_addresses_user_email;

drop table if exists addresses;

drop table if exists employees;

drop table if exists users;

