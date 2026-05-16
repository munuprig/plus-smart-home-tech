create table if not exists address
(
    address_id uuid default gen_random_uuid() primary key,
    country varchar(100),
    city varchar(100),
    street varchar(200),
    house varchar(30),
    flat varchar(20)
);

create table if not exists deliveries
(
    delivery_id uuid default gen_random_uuid() primary key,
    from_address uuid,
    to_address uuid,
    order_id uuid,
    delivery_state varchar(50),
    delivery_cost numeric(10,2) not null,
    delivery_weight double precision,
    delivery_volume double precision,
    fragile boolean
);

create table if not exists from_address
(
    delivery_id uuid references deliveries (delivery_id),
    address_id uuid references address (address_id)
);

create table if not exists to_address
(
    delivery_id uuid references deliveries (delivery_id),
    address_id uuid references address (address_id)
);
