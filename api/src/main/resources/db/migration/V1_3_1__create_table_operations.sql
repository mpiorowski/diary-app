-- operations table
create table operations
(
    operation_name     varchar(200) not null,
    operation_priority int         not null,

    id                 serial primary key unique,
    uid                uuid                 default uuid_generate_v4() unique,
    version            int                  default 1,
    active             boolean              default true,
    deleted            boolean              default false,
    created_at         timestamptz not null default NOW(),
    updated_at         timestamptz not null default NOW()
);

-- triggers and audits
create trigger set_timestamp
    before update
    on operations
    for each row
EXECUTE procedure trigger_set_timestamp();

select enable_audit_on_table('operations');
