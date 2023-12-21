-- CREATE DATABASE tour;

-- drop DATABASE tour;

-- CREATE table tour(
--  tour_id INTEGER PRIMARY KEY,
--  tour_name varchar(255) NOT NULL,
--  tour_duration date NOT NULL,
--  tour_type varchar(255) NOT NULL,
--  tour_max_number_of_people INTEGER NOT NULL,
--  tour_description text NOT NULL    
-- );

-- CREATE table users(
-- user_id INTEGER PRIMARY KEY,
-- user_name varchar(255) NOT NULL,
-- user_second_name varchar(255) NOT NULL,
-- user_phone_number varchar(255) NOT NULL,
-- user_password varchar(255) NOT NULL,
-- user_login varchar(255) NOT NULL,
-- users_role varchar(255),
-- users_email varchar(255) Not null,
-- user_currency_currency FLOAT
-- );

-- CREATE TABLE credit_card (
--     card_id INTEGER PRIMARY KEY,
--     user_id INTEGER NOT NULL REFERENCES users(user_id),
--     card_number VARCHAR(255) NOT NULL,
--     card_holder_name VARCHAR(255) NOT NULL,
--     expiration_date DATE NOT NULL,
--     cvv INTEGER NOT NULL
-- );

-- CREATE table booking(
-- booking_id INTEGER PRIMARY KEY,
-- user_id INTEGER NOT NULL REFERENCES users(user_id),
-- tour_id INTEGER NOT NULL REFERENCES tour(tour_id),
-- booking_date date NOT NULL,
-- booking_status varchar(255) NOT NULL,
-- card_id INTEGER REFERENCES credit_card(card_id)
-- );

-- CREATE TABLE stats (
--     stats_id SERIAL PRIMARY KEY,
--     tour_id INTEGER NOT NULL REFERENCES tour(tour_id),
--     stats_sales INTEGER NOT NULL,
--     stats_losses FLOAT NOT NULL,
--     stats_profit FLOAT GENERATED ALWAYS AS (stats_sales - stats_losses) STORED
-- );


-- CREATE TABLE tour_images (
--     image_id INTEGER PRIMARY KEY,
--     tour_id INTEGER NOT NULL REFERENCES tour(tour_id),
--     image bytea 
-- );

-- CREATE TABLE tour_ratings (
--     rating_id SERIAL PRIMARY KEY,
--     tour_id INTEGER NOT NULL REFERENCES tour(tour_id),
--     user_id INTEGER NOT NULL REFERENCES users(user_id),
--     rating INTEGER NOT NULL,
--     comment TEXT,
--     rating_date DATE NOT NULL DEFAULT CURRENT_DATE,
--     CONSTRAINT check_rating_value CHECK (rating >= 1 AND rating <= 5)
-- );


-- ALTER TABLE users
-- ADD user_email varchar(255) Not null;

-- ALTER TABLE users
-- DROP COLUMN users_email;

-- UPDATE tour
-- set tour_current_number_of_people = 0
-- WHERE tour_id = 7;


select * from user_tours;
