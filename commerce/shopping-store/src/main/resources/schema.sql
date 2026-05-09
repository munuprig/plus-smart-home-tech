drop table if exists products;

create table if not exists products(
    product_id uuid default gen_random_uuid() primary key,
    product_name varchar(255) not null,
    description varchar(255) not null,
    image_src varchar(255),
    rating integer,
    price double precision,
    quantity_state varchar(100) not null,
    product_state varchar(100) not null,
    product_category varchar(100) not null
);
