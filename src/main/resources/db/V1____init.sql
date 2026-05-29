create sequence private_id_seq;
create table users(
    id uuid primary key ,
    private_id bigint unique not null default nextval('private_id_seq'),
    user_name varchar(50) unique not null,
    password varchar(300) ,
    email varchar(100) ,
    status varchar(10) check ( status in ('ACTIVE', 'INACTIVE', 'BANNED') ) ,
    is_online bool ,
    position varchar(20) check ( position in ('AUTHOR', 'READER') ) ,
    avatar_mediaId varchar(100) ,
    created_at timestamptz default now(),
    updated_at timestamp default now()
);
create table roles(
    id uuid primary key ,
    code varchar(30) not null ,
    name varchar(30) not null ,
    description TEXT
);

create table user_roles(
    user_id uuid references users(id),
    role_id uuid references roles(id),

    constraint fk_user_roles_user foreign key (user_id) references users(id) on delete cascade,
    constraint fk_user_roles_role foreign key (role_id) references roles(id) on delete cascade
)