-- operations table
create table notifications
(
    notification_type       varchar(40)    not null,
    notification_values     varchar(200)[] not null,
    fk_user_id              integer        not null REFERENCES sys_users (id) ON delete RESTRICT,
    is_read                 boolean        not null default false,

    id                      serial primary key unique,
    uid                     uuid                    default uuid_generate_v4() unique,
    version                 int                     default 1,
    active                  boolean                 default true,
    deleted                 boolean                 default false,
    created_at              timestamptz    not null default NOW(),
    updated_at              timestamptz    not null default NOW()
);

-- triggers and audits
create trigger set_timestamp
    before update
    on notifications
    for each row
EXECUTE procedure trigger_set_timestamp();

select enable_audit_on_table('notifications');
