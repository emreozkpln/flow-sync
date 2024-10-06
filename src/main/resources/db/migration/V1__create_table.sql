-- Store tablosunu oluştur
CREATE TABLE IF NOT EXISTS store (
    id SERIAL PRIMARY KEY,
    store_name VARCHAR(255),
    store_id VARCHAR(255),
    lat VARCHAR(255),
    lng VARCHAR(255)
);

-- Product tablosunu oluştur
CREATE TABLE IF NOT EXISTS product (
    id SERIAL PRIMARY KEY,
    product_id VARCHAR(255) UNIQUE,
    product_name VARCHAR(255),
    stat VARCHAR(255),
    reading_time TIMESTAMP,
    passing_time VARCHAR(255),
    battery_level VARCHAR(255),
    store_id INTEGER,
    FOREIGN KEY (store_id) REFERENCES store(id) ON UPDATE CASCADE ON DELETE CASCADE
);

-- Report tablosunu oluştur
CREATE TABLE IF NOT EXISTS report (
    id SERIAL PRIMARY KEY,
    report_name VARCHAR(255),
    description VARCHAR(255)
);

-- User tablosunu oluştur
CREATE TABLE IF NOT EXISTS user_db (
    id SERIAL PRIMARY KEY,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    username VARCHAR(255) UNIQUE,
    password VARCHAR(255),
    is_approved BOOLEAN,
    role VARCHAR(255)
);

-- Token tablosunu oluştur
CREATE TABLE IF NOT EXISTS token (
    id SERIAL PRIMARY KEY,
    token VARCHAR(255),
    is_logged_out BOOLEAN,
    user_id INTEGER,
    FOREIGN KEY (user_id) REFERENCES user_db(id) ON UPDATE CASCADE ON DELETE CASCADE
);
