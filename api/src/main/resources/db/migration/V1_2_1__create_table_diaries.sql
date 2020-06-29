-- diaries table
create table diaries
(
    diary_type        varchar(200)      not null,
    diary_description varchar(1000)    not null,
    diary_like        double precision not null,
    diary_amount      int              not null,
    diary_date        DATE             not null default NOW(),
    fk_user_id        integer          not null REFERENCES sys_users (id) ON delete RESTRICT,
    question          varchar(1000),
    answer            varchar(1000),

    id                serial primary key unique,
    uid               uuid                      default uuid_generate_v4() unique,
    version           int                       default 1,
    active            boolean                   default true,
    deleted           boolean                   default false,
    created_at        timestamptz      not null default NOW(),
    updated_at        timestamptz      not null default NOW()
);

-- triggers and audits
create trigger set_timestamp
    before update
    on diaries
    for each row
EXECUTE procedure trigger_set_timestamp();

select enable_audit_on_table('diaries');
