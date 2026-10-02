 Movie Ticket Booking System

A console-based Movie Ticket Booking Application built in Java using JDBC and MySQL Database. 
The project follows a structured Layered Architecture (MVC / DAO Pattern) to handle user authentication, 
movie cataloging, show scheduling, visual seat selection, and transaction management.


 🌟 Key Features

 Admin Module
-Add Movie: Add new movie details including title, genre, language, duration, and release date.
-View All Movies: Browse the complete list of available movies in the system.
-Add Show: Schedule showtimes by linking movies to specific theatres, screens, dates, and times.
-View All Shows: View all currently scheduled shows across theatres.

👤 Customer Module 
-User Authentication: Secure Login and Registration system with role validation (`ADMIN` / `USER`).
-Search Movie by Name: Search and select movies directly by entering the movie title (case-insensitive).
-Interactive Seat Matrix: Visual screen layout (`[A1]`, `[A2]`, etc.) displaying available seats and marking booked seats as `[X]`.
-Seat Validation: Prevents double-booking and invalid seat selection using custom exception handling.
-Booking & Billing: Real-time calculation of total bill with payment method selection and instant booking confirmation.


🛠️ Tech Stack & Architecture

-Programming Language: Java (JDK 17+)
-Database: MySQL
-Database Connectivity: JDBC (`mysql-connector-j`)
-Logging: SLF4J / Logback 
-Build Tool: Maven

📁 Application Architecture

The application is modularized into distinct layers for separation of concerns:

-controller: Handles CLI inputs, user navigation, and response menus (`MainController`).
-service: Implements core business logic and validation rules. 
-dao: Manages direct database interactions using JDBC queries.
-model: Contains entity classes representing domain objects (`User`, `Movie`, `Show`, `Booking`, etc.).
-exception: Custom error handling for seamless execution (`SeatAlreadyBookedException`, `MovieNotFoundException`, etc.).