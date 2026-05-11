create table if not exists orders
(
    order_id uuid default gen_random_uuid() primary key,
    shopping_cart_id uuid not null,
    payment_id uuid,
    delivery_id uuid,
    state varchar(50),
    delivery_weight double precision,
    delivery_volume double precision,
    fragile boolean,
    total_price double precision,
    delivery_price double precision,
    product_price double precision
);

create table if not exists order_items
(
    order_id uuid references orders(order_id) on delete cascade,
    product_id uuid not null,
    quantity integer
);
