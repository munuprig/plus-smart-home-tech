create table if not exists products
(
    product_id       uuid default gen_random_uuid() primary key,
    product_name     varchar not null,
    description      varchar not null,
    image_src        varchar,
    rating           integer,
    price            double precision,
    quantity_state   varchar not null,
    product_state    varchar not null,
    product_category varchar not null,
);