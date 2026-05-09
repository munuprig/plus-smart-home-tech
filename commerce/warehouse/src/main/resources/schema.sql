drop table if exists shopping_cart, shopping_cart_items;

create table if not exists shopping_cart
(
    shopping_cart_id uuid default gen_random_uuid() primary key,
    username varchar(255) not null,
    active boolean not null
);

create table if not exists shopping_cart_items
(
    product_id uuid not null,
    quantity integer,
    cart_id uuid references shopping_cart (shopping_cart_id) on delete cascade
);


