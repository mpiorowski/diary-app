-- users table
create table sys_users
(
  user_name     varchar(60) not null unique,
  user_email    varchar(60) not null,
  user_password varchar(60) not null,
  user_role     varchar(60) not null,

  id            serial primary key unique,
  uid           uuid                 default uuid_generate_v4() unique,
  version       int                  default 1,
  active        boolean              default true,
  deleted       boolean              default false,
  created_at    timestamp   not null default now(),
  updated_at    timestamp   not null default now()
);

create trigger set_timestamp
  before update
  on sys_users
  for each row
EXECUTE procedure trigger_set_timestamp();

select enable_audit_on_table('sys_users');
