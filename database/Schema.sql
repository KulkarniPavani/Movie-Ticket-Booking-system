CREATE DATABASE movie_ticket_system;
use movie_ticket_system;
CREATE TABLE theatres (
                          theatre_id INT PRIMARY KEY,
                          name VARCHAR(255),
                          city VARCHAR(255),
                          address VARCHAR(255),
                          total_seats INT
);
CREATE TABLE movies (
                        movie_id INT PRIMARY KEY,
                        title VARCHAR(255),
                        language VARCHAR(255),
                        genre VARCHAR(255),
                        duration INT,
                        release_date DATE
);
CREATE TABLE users (
                       user_id INT PRIMARY KEY,
                       name VARCHAR(255),
                       email VARCHAR(255) UNIQUE,
                       phone VARCHAR(255),
                       password VARCHAR(255),
                       role VARCHAR(255)
);
CREATE TABLE seats (
                       seat_id INT PRIMARY KEY,
                       theatre_id INT,
                       seat_number VARCHAR(255),
                       seat_type VARCHAR(255),
                       price DECIMAL
);
ALTER TABLE seats
    ADD CONSTRAINT fk_seats_theatre
        FOREIGN KEY (theatre_id)
            REFERENCES theatres(theatre_id);
CREATE TABLE shows (
                       show_id INT PRIMARY KEY,
                       theatre_id INT,
                       movie_id INT,
                       show_date DATE,
                       start_time TIME,
                       end_time TIME,

                       CONSTRAINT fk_shows_theatre
                           FOREIGN KEY (theatre_id)
                               REFERENCES theatres(theatre_id),

                       CONSTRAINT fk_shows_movie
                           FOREIGN KEY (movie_id)
                               REFERENCES movies(movie_id)
);
CREATE TABLE bookings (
                          booking_id INT PRIMARY KEY,
                          show_id INT,
                          user_id INT,
                          booking_date DATETIME,
                          total_amount DECIMAL,
                          booking_status VARCHAR(255),

                          CONSTRAINT fk_bookings_show
                              FOREIGN KEY (show_id)
                                  REFERENCES shows(show_id),

                          CONSTRAINT fk_bookings_user
                              FOREIGN KEY (user_id)
                                  REFERENCES users(user_id)
);
CREATE TABLE booked_seats (
                              booked_seat_id INT PRIMARY KEY,
                              seat_id INT,
                              booking_id INT,

                              CONSTRAINT fk_booked_seats_seat
                                  FOREIGN KEY (seat_id)
                                      REFERENCES seats(seat_id),

                              CONSTRAINT fk_booked_seats_booking
                                  FOREIGN KEY (booking_id)
                                      REFERENCES bookings(booking_id)
);
CREATE TABLE payments (
                          payment_id INT PRIMARY KEY,
                          booking_id INT UNIQUE,
                          amount DECIMAL,
                          payment_method VARCHAR(255),
                          payment_status VARCHAR(255),
                          payment_date DATETIME,

                          CONSTRAINT fk_payments_booking
                              FOREIGN KEY (booking_id)
                                  REFERENCES bookings(booking_id)
);