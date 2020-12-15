DROP TABLE IF EXISTS accidents_rules;
DROP TABLE IF EXISTS accidents;
DROP TABLE IF EXISTS rules;
DROP TABLE IF EXISTS types;

CREATE TABLE types (
  id serial primary key,
  name varchar(2000)
);

CREATE TABLE rules (
  id serial primary key,
  name varchar(2000)
);

CREATE TABLE accidents (
  id serial primary key,
  name varchar(2000),
  text text,
  address text,
  type_id integer
);

CREATE TABLE accidents_rules (
  accident_id integer references accidents(id),
  rule_id integer references  rules(id)
);

INSERT INTO types(name) VALUES('Две машины');
INSERT INTO types(name) VALUES('Машина и человек');
INSERT INTO types(name) VALUES('Машина и велосипед');

INSERT INTO rules(name) VALUES('Статья. 1');
INSERT INTO rules(name) VALUES('Статья. 2');
INSERT INTO rules(name) VALUES('Статья. 3');

-- SELECT * FROM accidents;
--
-- SELECT * FROM types;
--
-- SELECT * FROM rules;
--
-- SELECT * FROM accidents_rules;