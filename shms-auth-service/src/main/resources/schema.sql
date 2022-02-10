-- used in tests that use HSQL
create table user (
  user_id VARCHAR(10) PRIMARY KEY,
  password VARCHAR(100),
  name VARCHAR(100) not null,
  nick_name VARCHAR(100),
  email VARCHAR(100),
  created_date timestamp,
  modified_date timestamp
);

create table refresh_token (
  no INTEGER,
  user_id VARCHAR(10),
  token VARCHAR(256) not null,
  expire_date timestamp,
  created_date timestamp,
  modified_date timestamp,
  PRIMARY KEY (no, user_id)
);

create table user_roles (
  user_id VARCHAR(10) PRIMARY KEY,
  roles VARCHAR(256) not null,
  created_date timestamp,
  modified_date timestamp
);