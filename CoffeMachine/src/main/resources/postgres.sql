create database coffee;

create table "user"
(
    user_id uuid not null
        constraint user_pk
            primary key
);

create table coffee_maker
(
    coffee_maker_id        uuid                     not null
        constraint coffee_maker_pkey
            primary key,
    start_time             timestamp with time zone not null,
    electric_power_supply  boolean                  not null,
    on_repair              boolean                  not null,
    end_time               timestamp with time zone,
    diff_time              interval,
    current_level_of_water double precision         not null,
    coffee_type            smallint                 not null,
    userid                 uuid
        constraint coffee_maker_user_user_id_fk
            references "user"
            on update cascade on delete cascade
);