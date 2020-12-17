DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS authorities;

CREATE TABLE IF NOT EXISTS authorities (
   id serial primary key,
   authority VARCHAR(50) NOT NULL unique
);

CREATE TABLE IF NOT EXISTS users (
     id serial primary key,
     username VARCHAR(50) NOT NULL unique,
     password VARCHAR(100) NOT NULL,
     enabled boolean default true,
     authority_id int not null references authorities(id)
);

insert into authorities (authority) values ('ROLE_USER');
insert into authorities (authority) values ('ROLE_ADMIN');

insert into users (username, password, enabled, authority_id)
values ('root', '$2a$10$nDAKuVuO7ivNGMUoc8oQ8ew0oneDEddo5f3FOkz.RZkoAR4f.ZENy', true,
        (select id from authorities where authority = 'ROLE_ADMIN'));

SELECT * FROM users;

SELECT * FROM authorities;

