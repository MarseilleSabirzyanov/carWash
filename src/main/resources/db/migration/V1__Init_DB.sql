create sequence hibernate_sequence start 1 increment 1;

create table history (
    id int8 not null,
    date timestamp,
    op varchar(255),
    total int8,
    admin_id int8,
    user_id int8,
    primary key (id)
);

create table user_role (
    user_id int8 not null,
    roles varchar(255)
);

create table usr (
    id int8 not null,
    activation_code varchar(255),
    active boolean not null,
    email varchar(255),
    name varchar(255),
    surname varchar(255),
    phone varchar(255),
    password varchar(255) not null,
    score int8,
    username varchar(255) not null,
    primary key (id)
);

alter table if exists customer
    add constraint customer_usr_fk
    foreign key (user_id) references usr;

alter table if exists history
    add constraint history_admin_fk
    foreign key (admin_id) references usr;

alter table if exists history
    add constraint history_usr_fk
    foreign key (user_id) references usr;

alter table if exists user_role
    add constraint usr_role_fk
    foreign key (user_id) references usr;

