drop table if exists shopping_cart,shopping_cart_items,warehouse_product;

create table if not exists warehouse_product(
    product_id uuid primary key,
    quantity integer,
    fragile boolean,
    width double precision,
    height double precision,
    depth double precision,
    weight double precision
);

create table if not exists bookings(
    shopping_cart_id uuid primary key,
    delivery_weight double precision not null,
    delivery_volume double precision not null,
    fragile boolean not null,
    order_id uuid
);

create table if not exists booking_products(
    shopping_cart_id uuid references bookings(shopping_cart_id) on delete cascade primary key,
    product_id uuid not null,
    quantity integer
);
