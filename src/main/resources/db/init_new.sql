create table user_roles
(
    id   integer not null
        constraint user_roles_pkey
            primary key,
    role varchar not null
);

create table user_table
(
    id         serial  not null
        constraint user_pkey
            primary key,
    role_id    integer not null
        constraint user_role_id_fkey
            references user_roles
            on delete cascade,
    first_name varchar not null,
    last_name  varchar not null,
    nickname   varchar not null,
    email      varchar not null,
    password   varchar not null
);

create unique index user_table_email_uindex
    on user_table (email);

create table exhibition
(
    id   integer not null
        constraint exhibition_pkey
            primary key,
    name varchar not null
);

create table file_type
(
    id   integer not null
        constraint file_type_pkey
            primary key,
    name varchar not null
);

create table file
(
    id      integer not null
        constraint file_pkey
            primary key,
    type_id integer not null
        constraint file_type_id_fkey
            references file_type,
    path    varchar not null
);

create table action_type
(
    id   integer not null
        constraint action_type_pkey
            primary key,
    name varchar not null
);

create table user_action
(
    id             serial    not null
        constraint user_action_pkey
            primary key,
    user_id        integer   not null
        constraint user_action_user_id_fkey
            references user_table,
    file_id        integer   not null
        constraint user_action_file_id_fkey
            references file,
    action_type_id integer   not null
        constraint user_action_action_type_id_fkey
            references action_type,
    action_time    timestamp not null
);

create table bundle
(
    id   serial  not null
        constraint bundle_pk
            primary key,
    path varchar not null
);

create table stand_model
(
    id        integer not null
        constraint stand_model_pkey
            primary key,
    bundle_id integer not null
        constraint boundle_id
            references bundle,
    name      varchar not null
);

create table stand
(
    id            integer          not null
        constraint stand_pkey
            primary key,
    model_id      integer          not null
        constraint stand_model_id_fkey
            references stand_model,
    exhibition_id integer          not null
        constraint stand_exhibition_id_fkey
            references exhibition,
    owner_id      integer          not null
        constraint stand_owner_id_fkey
            references user_table,
    name          varchar          not null,
    coordinate_x  double precision not null,
    coordinate_y  double precision not null,
    coordinate_z  double precision not null,
    rotation_x    double precision not null,
    rotation_y    double precision not null,
    rotation_z    double precision not null,
    scale_x       double precision not null,
    scale_y       double precision not null,
    scale_z       double precision not null
);

create table object_model
(
    id        integer not null
        constraint object_model_pkey
            primary key,
    name      varchar not null,
    bundle_id integer not null
        constraint bundle_id
            references bundle
);

create unique index object_model_name_uindex
    on object_model (name);

create table exhibition_object
(
    id            integer          not null
        constraint exhibition_object_pkey
            primary key,
    model_id      integer          not null
        constraint exhibition_object_model_id_fkey
            references object_model,
    exhibition_id integer          not null
        constraint exhibition_object_exhibition_id_fkey
            references exhibition,
    name          varchar          not null,
    coordinate_x  double precision not null,
    coordinate_y  double precision not null,
    coordinate_z  double precision not null,
    rotation_x    double precision not null,
    rotation_y    double precision not null,
    rotation_z    double precision not null,
    scale_x       double precision not null,
    scale_y       double precision not null,
    scale_z       double precision not null
);

create table stand_object
(
    id           integer          not null
        constraint stand_object_pkey
            primary key,
    model_id     integer          not null
        constraint stand_object_model_id_fkey
            references object_model,
    stand_id     integer          not null
        constraint stand_object_stand_id_fkey
            references stand,
    name         varchar          not null,
    coordinate_x double precision not null,
    coordinate_y double precision not null,
    coordinate_z double precision not null,
    rotation_x   double precision not null,
    rotation_y   double precision not null,
    rotation_z   double precision not null,
    scale_x      double precision not null,
    scale_y      double precision not null,
    scale_z      double precision not null
);

create table file_exhibition_object
(
    file_id         integer not null
        constraint file_exhibition_object_file_id_fkey
            references file,
    exhib_object_id integer not null
        constraint file_exhibition_object_exhib_object_id_fkey
            references exhibition_object
);

create table file_stand_object
(
    file_id         integer not null
        constraint file_stand_object_file_id_fkey
            references file,
    stand_object_id integer not null
        constraint file_stand_object_stand_object_id_fkey
            references stand_object
);

create unique index bundle_id_uindex
    on bundle (id);