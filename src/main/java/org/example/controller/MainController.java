package org.example.controller;

import org.example.dao.*;
import org.example.exception.*;
import org.example.model.*;
import org.example.service.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class MainController {

    private static final Scanner scanner = new Scanner(System.in);
    private static final UserDAO userDAO = new UserDAOImpl();
    private static final MovieDAO movieDAO = new MovieDAOImpl();
    private static final ShowDAO showDAO = new ShowDAOImpl();
    private static final BookingDAO bookingDAO = new BookingDAOImpl();

    private static final UserService userService = new UserServiceImpl(userDAO);
    private static final MovieService movieService = new MovieServiceImpl(movieDAO);
    private static final ShowService showService = new ShowServiceImpl(showDAO);

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n=================================");
            System.out.println("   MOVIE TICKET BOOKING SYSTEM   ");
            System.out.println("=================================");
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

            int choice = readIntInput();

            switch (choice) {
                case 1 -> login();
                case 2 -> register();
                case 3 -> {
                    System.out.println("Thank you for using Movie Ticket Booking System. Goodbye!");
                    System.exit(0);
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void login() {
        System.out.println("\n----------- LOGIN -----------");
        System.out.println("Select Role:");
        System.out.println("1. CUSTOMER");
        System.out.println("2. ADMIN");
        System.out.print("Enter role choice: ");

        int roleChoice = readIntInput();
        String role;

        if (roleChoice == 1) {
            role = "USER";
        } else if (roleChoice == 2) {
            role = "ADMIN";
        } else {
            System.out.println("Invalid role choice.");
            return;
        }

        System.out.print("Enter email: ");
        String email = scanner.nextLine().trim();

        System.out.print("Enter password: ");
        String password = scanner.nextLine().trim();

        try {
            User loggedInUser = userService.loginUser(email, password, role);

            if (loggedInUser == null) {
                throw new UserNotFoundException("Invalid credentials or role mismatch for email: " + email);
            }

            System.out.println("\nLogin Successful! Welcome, " + loggedInUser.getName() + "!");

            if ("ADMIN".equalsIgnoreCase(loggedInUser.getRole())) {
                showAdminMenu(loggedInUser);
            } else {
                showCustomerMenu(loggedInUser);
            }

        } catch (UserNotFoundException e) {
            System.out.println("\nError: " + e.getMessage() + ". Please try again.");
        }
    }

    private static void register() {
        System.out.println("\n----------- REGISTER -----------");
        System.out.print("Enter Name: ");
        String name = scanner.nextLine().trim();

        System.out.print("Enter Email: ");
        String email = scanner.nextLine().trim();

        System.out.print("Enter Password: ");
        String password = scanner.nextLine().trim();

        System.out.print("Enter Mobile Number: ");
        String phone = scanner.nextLine().trim();

        User newUser = new User();
        newUser.setName(name);
        newUser.setEmail(email);
        newUser.setPassword(password);
        newUser.setPhone(phone);
        newUser.setRole("USER");

        boolean success = userService.addUser(newUser);
        if (success) {
            System.out.println("Registration successful! You can now log in.");
        } else {
            System.out.println("Registration failed. Email might already exist.");
        }
    }

    private static void showAdminMenu(User adminUser) {
        while (true) {
            System.out.println("\n=================================");
            System.out.println("          ADMIN DASHBOARD        ");
            System.out.println("=================================");
            System.out.println("1. Add Movie");
            System.out.println("2. View All Movies");
            System.out.println("3. Add Show");
            System.out.println("4. View All Shows");
            System.out.println("5. Logout");
            System.out.print("Enter choice: ");

            int choice = readIntInput();

            switch (choice) {
                case 1 -> addMovie();
                case 2 -> viewAllMovies();
                case 3 -> addShow();
                case 4 -> viewAllShows();
                case 5 -> {
                    System.out.println("Logged out successfully.");
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void showCustomerMenu(User customer) {
        while (true) {
            System.out.println("\n=================================");
            System.out.println("        CUSTOMER DASHBOARD       ");
            System.out.println("=================================");
            System.out.println("1. Book Movie Ticket");
            System.out.println("2. View All Movies");
            System.out.println("3. View All Shows");
            System.out.println("4. Logout");
            System.out.print("Enter choice: ");

            int choice = readIntInput();

            switch (choice) {
                case 1 -> startTicketBookingFlow(customer);
                case 2 -> viewAllMovies();
                case 3 -> viewAllShows();
                case 4 -> {
                    System.out.println("Logged out successfully.");
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void addMovie() {
        System.out.println("\n----------- ADD MOVIE -----------");
        System.out.print("Enter Title: ");
        String title = scanner.nextLine().trim();

        System.out.print("Enter Genre: ");
        String genre = scanner.nextLine().trim();

        System.out.print("Enter Language: ");
        String language = scanner.nextLine().trim();

        System.out.print("Enter Duration (mins): ");
        int duration = readIntInput();

        System.out.print("Enter Release Date (YYYY-MM-DD): ");
        String releaseDateStr = scanner.nextLine().trim();

        LocalDate releaseDate;
        try {
            releaseDate = LocalDate.parse(releaseDateStr);
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format. Please use YYYY-MM-DD.");
            return;
        }

        Movie movie = new Movie();
        movie.setTitle(title);
        movie.setGenre(genre);
        movie.setLanguage(language);
        movie.setDuration(duration);
        movie.setReleaseDate(releaseDate);

        boolean success = movieService.addMovie(movie);
        if (success) {
            System.out.println("Movie added successfully!");
        } else {
            System.out.println("Failed to add movie.");
        }
    }

    private static void addShow() {
        System.out.println("\n----------- ADD SHOW -----------");

        List<Movie> existingMovies = movieService.getAllMovies();
        if (existingMovies == null || existingMovies.isEmpty()) {
            System.out.println("No movies found in database! Please add a movie first before adding a show.");
            return;
        }

        System.out.println("Available Movies:");
        for (Movie m : existingMovies) {
            System.out.printf("  ID: %d -> %s (%s)%n", m.getMovieId(), m.getTitle(), m.getLanguage());
        }

        System.out.print("\nEnter Movie ID from above list: ");
        int movieId = readIntInput();

        boolean movieExists = existingMovies.stream().anyMatch(m -> m.getMovieId() == movieId);
        if (!movieExists) {
            System.out.println("Error: Movie ID " + movieId + " does not exist. Please select a valid Movie ID from the list.");
            return;
        }

        System.out.print("Enter Theatre ID: ");
        int theatreId = readIntInput();

        System.out.print("Enter Screen ID (e.g. 1, 2, 3): ");
        int screenId = readIntInput();

        if (screenId <= 0) {
            System.out.println("Invalid Screen ID. Setting default Screen ID to 1.");
            screenId = 1;
        }

        System.out.print("Enter Show Date (YYYY-MM-DD): ");
        String dateStr = scanner.nextLine().trim();

        System.out.print("Enter Start Time (HH:MM): ");
        String startTimeStr = scanner.nextLine().trim();

        System.out.print("Enter End Time (HH:MM): ");
        String endTimeStr = scanner.nextLine().trim();

        try {
            Movie movie = new Movie();
            movie.setMovieId(movieId);

            Theatre theatre = new Theatre();
            theatre.setTheatreId(theatreId);

            Show show = new Show();
            show.setMovie(movie);
            show.setTheatre(theatre);
            show.setScreenId(screenId);
            show.setShowDate(LocalDate.parse(dateStr));
            show.setStartTime(parseLocalTime(startTimeStr));
            show.setEndTime(parseLocalTime(endTimeStr));

            boolean success = showService.insertShow(show);
            if (success) {
                System.out.println("Show added successfully!");
            } else {
                System.out.println("Failed to add show. Check if Theatre ID " + theatreId + " exists in the database.");
            }
        } catch (DateTimeParseException e) {
            System.out.println("Error adding show. Check date/time format (YYYY-MM-DD and HH:MM).");
        } catch (Exception e) {
            System.out.println("Error adding show: " + e.getMessage());
        }
    }

    private static void startTicketBookingFlow(User customer) {
        System.out.println("\n=================================");
        System.out.println("      TICKET BOOKING PROCESS     ");
        System.out.println("=================================");

        try {
            List<Movie> movies = movieService.getAllMovies();
            if (movies == null || movies.isEmpty()) {
                System.out.println("No movies currently available for booking.");
                return;
            }

            System.out.println("\n--- Available Movies ---");
            for (Movie m : movies) {
                System.out.printf("Title: %s | Genre: %s | Language: %s | Duration: %d mins%n",
                        m.getTitle(), m.getGenre(), m.getLanguage(), m.getDuration());
            }

            // Prompt user to enter Movie Name instead of Movie ID
            System.out.print("\nEnter Movie Name to search/select: ");
            String movieNameInput = scanner.nextLine().trim();

            if (movieNameInput.isEmpty()) {
                System.out.println("Movie name cannot be empty. Aborting booking.");
                return;
            }


            Movie selectedMovie = movies.stream()
                    .filter(m -> m.getTitle().equalsIgnoreCase(movieNameInput))
                    .findFirst()
                    .orElse(null);

            if (selectedMovie == null) {
                throw new MovieNotFoundException("Movie with name '" + movieNameInput + "' was not found or is currently not available.");
            }

            // Fetch available shows for the selected movie using the movie's ID behind the scenes
            List<Show> allShows = showService.getAllShows();
            List<Show> shows = new ArrayList<>();
            if (allShows != null) {
                for (Show s : allShows) {
                    if (s.getMovie() != null && s.getMovie().getMovieId() == selectedMovie.getMovieId()) {
                        shows.add(s);
                    }
                }
            }

            if (shows.isEmpty()) {
                System.out.println("No shows available for the movie: " + selectedMovie.getTitle());
                return;
            }

            System.out.println("\n--- Available Shows for " + selectedMovie.getTitle() + " ---");
            for (Show s : shows) {
                int displayScreenId = s.getScreenId() > 0 ? s.getScreenId() : 1;
                System.out.printf("Show ID: %d | Screen ID: %d | Date: %s | Time: %s - %s%n",
                        s.getShowId(), displayScreenId, s.getShowDate(), s.getStartTime(), s.getEndTime());
            }

            System.out.print("\nEnter Show ID to select: ");
            int selectedShowId = readIntInput();
            Show selectedShow = shows.stream()
                    .filter(s -> s.getShowId() == selectedShowId)
                    .findFirst()
                    .orElse(null);

            if (selectedShow == null) {
                System.out.println("Invalid Show ID selected. Aborting booking.");
                return;
            }

            List<String> bookedSeats = bookingDAO.getBookedSeatsForShow(selectedShowId);

            System.out.println("\n--- Seat Layout & Availability ---");
            System.out.println("           [ SCREEN THIS WAY ]           ");
            System.out.println("=========================================");

            String[] rows = {"A", "B", "C"};
            int totalCols = 5;

            for (String row : rows) {
                System.out.print("Row " + row + ":  ");
                for (int c = 1; c <= totalCols; c++) {
                    String seatCode = row + c;
                    if (bookedSeats != null && bookedSeats.contains(seatCode)) {
                        System.out.print("[X] ");
                    } else {
                        System.out.printf("[%s] ", seatCode);
                    }
                }
                System.out.println();
            }
            System.out.println("=========================================");

            System.out.print("\nHow many seats do you want to book (1-10)? ");
            int numSeats = readIntInput();

            if (numSeats < 1 || numSeats > 10) {
                System.out.println("Invalid number of seats. You can select between 1 and 10 seats.");
                return;
            }

            List<String> selectedSeats = new ArrayList<>();
            System.out.println("\nEnter seat codes one by one (e.g., A1, B3):");
            for (int i = 1; i <= numSeats; i++) {
                System.out.print("Select Seat #" + i + ": ");
                String seatChoice = scanner.nextLine().trim().toUpperCase();

                if (seatChoice.isEmpty()) {
                    System.out.println("Invalid seat format. Aborting booking.");
                    return;
                }

                if (bookedSeats != null && bookedSeats.contains(seatChoice)) {
                    throw new SeatAlreadyBookedException("Seat " + seatChoice + " is already booked by another customer!");
                }

                if (selectedSeats.contains(seatChoice)) {
                    throw new SeatAlreadyBookedException("Seat " + seatChoice + " has already been selected in this session!");
                }

                selectedSeats.add(seatChoice);
            }

            double ticketPrice = 150.00;
            double totalAmount = numSeats * ticketPrice;

            System.out.println("\n---------------------------------");
            System.out.println("Selected Seats : " + String.join(", ", selectedSeats));
            System.out.printf("Total Amount to Pay (%d seats @ ₹%.2f): ₹%.2f%n", numSeats, ticketPrice, totalAmount);
            System.out.println("---------------------------------");
            System.out.println("Select Payment Method:");
            System.out.println("1. UPI");
            System.out.println("2. CARD");
            System.out.println("3. CASH");
            System.out.print("Enter choice (1-3): ");

            int paymentChoice = readIntInput();
            String paymentMethod;

            switch (paymentChoice) {
                case 1 -> paymentMethod = "UPI";
                case 2 -> paymentMethod = "CARD";
                case 3 -> paymentMethod = "CASH";
                default -> {
                    System.out.println("Invalid payment method selected. Booking cancelled.");
                    return;
                }
            }

            System.out.print("Confirm payment of ₹" + totalAmount + " using " + paymentMethod + "? (Y/N): ");
            String confirm = scanner.nextLine().trim();

            if (!"Y".equalsIgnoreCase(confirm)) {
                System.out.println("Payment cancelled by user.");
                return;
            }

            Booking booking = new Booking();
            booking.setUser(customer);
            booking.setShow(selectedShow);
            booking.setTotalAmount(BigDecimal.valueOf(totalAmount));
            booking.setBookingStatus("CONFIRMED");
            booking.setBookingDate(LocalDateTime.now());

            boolean saved = ((BookingDAOImpl) bookingDAO).saveBookingWithSeats(booking, selectedSeats);

            if (!saved) {
                System.out.println("Failed to persist booking in database.");
                return;
            }

            System.out.println("\n=================================");
            System.out.println("      BOOKING CONFIRMED!         ");
            System.out.println("=================================");
            System.out.println("Customer Name  : " + customer.getName());
            System.out.println("Movie Title    : " + selectedMovie.getTitle());
            System.out.println("Show Date/Time : " + selectedShow.getShowDate() + " " + selectedShow.getStartTime());
            System.out.println("Selected Seats : " + String.join(", ", selectedSeats));
            System.out.println("Number of Seats: " + numSeats);
            System.out.println("Payment Method : " + paymentMethod);
            System.out.printf("Total Paid     : ₹%.2f%n", totalAmount);
            System.out.println("Status         : CONFIRMED");
            System.out.println("=================================");

        } catch (SeatAlreadyBookedException e) {
            System.out.println("\n[BOOKING ERROR]: " + e.getMessage());
            System.out.println("Booking aborted. Please try again.");
        } catch (MovieNotFoundException e) {
            System.out.println("\n[BOOKING ERROR]: " + e.getMessage());
            System.out.println("Booking aborted. Please try again.");
        }
    }

    private static void viewAllMovies() {
        System.out.println("\n----------- ALL MOVIES -----------");
        List<Movie> movies = movieService.getAllMovies();
        if (movies == null || movies.isEmpty()) {
            System.out.println("No movies available.");
            return;
        }

        for (Movie m : movies) {
            System.out.printf("ID: %d | Title: %s | Genre: %s | Language: %s | Duration: %d mins | Release Date: %s%n",
                    m.getMovieId(), m.getTitle(), m.getGenre(), m.getLanguage(), m.getDuration(), m.getReleaseDate());
        }
    }

    private static void viewAllShows() {
        System.out.println("\n----------- ALL SHOWS -----------");
        List<Show> shows = showService.getAllShows();
        if (shows == null || shows.isEmpty()) {
            System.out.println("No shows available.");
            return;
        }


        List<Movie> allMovies = movieService.getAllMovies();

        for (Show s : shows) {
            String movieTitle = "Unknown Movie";

            if (s.getMovie() != null) {
                if (s.getMovie().getTitle() != null && !s.getMovie().getTitle().isEmpty()) {
                    movieTitle = s.getMovie().getTitle();
                } else if (allMovies != null) {
                    int targetMovieId = s.getMovie().getMovieId();
                    movieTitle = allMovies.stream()
                            .filter(m -> m.getMovieId() == targetMovieId)
                            .map(Movie::getTitle)
                            .findFirst()
                            .orElse("Movie ID: " + targetMovieId);
                }
            }


            int displayScreenId = s.getScreenId() > 0 ? s.getScreenId() : 1;

            System.out.printf("Show ID: %d | Movie Name: %s | Screen ID: %d | Date: %s | Time: %s - %s%n",
                    s.getShowId(), movieTitle, displayScreenId, s.getShowDate(), s.getStartTime(), s.getEndTime());
        }
    }

    private static LocalTime parseLocalTime(String timeStr) {
        if (timeStr.length() == 5) {
            return LocalTime.parse(timeStr + ":00");
        }
        return LocalTime.parse(timeStr);
    }

    private static int readIntInput() {
        try {
            String line = scanner.nextLine().trim();
            if (line.isEmpty()) {
                return -1;
            }
            return Integer.parseInt(line);
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}