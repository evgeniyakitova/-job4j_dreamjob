CREATE TABLE post (
    id SERIAL PRIMARY KEY,
    name TEXT,
    description TEXT,
    created TIMESTAMP DEFAULT NOW(),
    visible BOOLEAN,
    city_id INT
);

CREATE TABLE candidate (
    id SERIAL PRIMARY KEY,
    name TEXT,
    description TEXT,
    created TIMESTAMP DEFAULT NOW(),
    photo bytea
);

CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    name TEXT,
    email VARCHAR UNIQUE,
    password TEXT
);