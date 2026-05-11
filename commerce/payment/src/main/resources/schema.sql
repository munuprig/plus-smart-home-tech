create table if not exists payments
(
    payment_id uuid default gen_random_uuid() primary key,
    order_id uuid not null,
    products_total double precision,
    delivery_total double precision,
    total_payment double precision,
    fee_total double precision,
    status varchar(15)
);
