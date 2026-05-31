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
    avatar_media_Id varchar(100) ,
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
);
create table categories(
    id uuid primary key ,
    name varchar(100) unique not null ,
    slug varchar(200) unique not null ,
    description text ,
    parent_id uuid,
    created_at timestamptz default now(),
    updated_at timestamptz default now() ,
    constraint fk_categories_parent foreign key (parent_id) references categories(id)
);

create table tags (
    id uuid primary key ,
    name varchar(100) unique not null ,
    slug varchar(200) unique not null ,
    created_at timestamptz default now()
);

create table medias(
    id uuid primary key ,
    uploader_id uuid not null ,
    filename varchar(300) not null ,
    storage_key varchar(300) not null ,
    mime_type varchar(50) ,
    size int ,
    width bigserial ,
    height bigserial ,
    alt_text varchar(100) ,
    created_at timestamptz default now(),
    constraint fk_medias_uploader foreign key (uploader_id) references users(id)
);
create table posts (
    id uuid primary key ,
    author_id uuid not null ,
    category_id uuid not null ,
    title varchar(300) not null ,
    slug varchar(200) unique not null ,
    excerpt text ,
    content text ,
    content_html text ,
    featured_image_id uuid,
    status varchar(20) check ( status in ('DRAFT', 'PENDING_REVIEW', 'PUBLISHED', 'SCHEDULED', 'ARCHIVED') ),
    visibility varchar(20) check ( visibility in ('PUBLIC', 'PRIVATE', 'PASSWORD_PROTECTED') ),
    published_at timestamptz,
    scheduled_at timestamptz,
    created_at timestamptz default now(),
    updated_at timestamptz default now(),
    deleted_at timestamptz ,
    view_count int,

    constraint fk_posts_author foreign key (author_id) references users(id),
    constraint fk_posts_category foreign key (category_id) references categories(id),
    constraint fk_posts_image foreign key (featured_image_id) references medias(id)
);
