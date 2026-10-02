package org.example.dao;

import org.example.model.Movie;
import org.example.util.DBConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MovieDAOImpl implements MovieDAO {

    private static final Logger logger = LoggerFactory.getLogger(MovieDAOImpl.class);

    private static final String ADD_MOVIE =
            "INSERT INTO movies (title, language, genre, duration, release_date) VALUES (?, ?, ?, ?, ?)";

    private static final String SAVE_MOVIE =
            "INSERT INTO movies (title, language, genre, duration, release_date) VALUES (?, ?, ?, ?, ?)";

    private static final String FIND_MOVIE_BY_ID =
            "SELECT * FROM movies WHERE movie_id = ?";

    private static final String MODIFY_MOVIE =
            "UPDATE movies SET title = ?, language = ?, genre = ?, duration = ?, release_date = ? WHERE movie_id = ?";

    private static final String ERASE_MOVIE =
            "DELETE FROM movies WHERE movie_id = ?";

    private static final String GET_ALL_MOVIES =
            "SELECT * FROM movies";

    @Override
    public boolean addMovie(Movie movie) {
        if (movie == null) {
            return false;
        }

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(ADD_MOVIE)) {

            statement.setString(1, movie.getTitle());
            statement.setString(2, movie.getLanguage());
            statement.setString(3, movie.getGenre());
            statement.setInt(4, movie.getDuration());
            statement.setDate(5, movie.getReleaseDate() != null ? Date.valueOf(movie.getReleaseDate()) : null);

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                logger.info("Movie added successfully: {}", movie.getTitle());
                return true;
            }

        } catch (SQLException e) {
            logger.error("Error while adding movie", e);
        }

        return false;
    }

    @Override
    public boolean saveMovie(Movie movie) {
        return addMovie(movie);
    }

    @Override
    public Movie findMovieById(int movieId) {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_MOVIE_BY_ID)) {

            statement.setInt(1, movieId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Movie movie = new Movie();
                movie.setMovieId(resultSet.getInt("movie_id"));
                movie.setTitle(resultSet.getString("title"));
                movie.setLanguage(resultSet.getString("language"));
                movie.setGenre(resultSet.getString("genre"));
                movie.setDuration(resultSet.getInt("duration"));
                Date releaseDate = resultSet.getDate("release_date");
                if (releaseDate != null) {
                    movie.setReleaseDate(releaseDate.toLocalDate());
                }
                return movie;
            }

        } catch (SQLException e) {
            logger.error("Error while finding movie by ID: {}", movieId, e);
        }

        return null;
    }

    @Override
    public boolean modifyMovie(Movie movie) {
        if (movie == null || movie.getMovieId() <= 0) {
            return false;
        }

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(MODIFY_MOVIE)) {

            statement.setString(1, movie.getTitle());
            statement.setString(2, movie.getLanguage());
            statement.setString(3, movie.getGenre());
            statement.setInt(4, movie.getDuration());
            statement.setDate(5, movie.getReleaseDate() != null ? Date.valueOf(movie.getReleaseDate()) : null);
            statement.setInt(6, movie.getMovieId());

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            logger.error("Error while modifying movie ID: {}", movie.getMovieId(), e);
        }

        return false;
    }

    @Override
    public boolean eraseMovie(int movieId) {
        if (movieId <= 0) {
            return false;
        }

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(ERASE_MOVIE)) {

            statement.setInt(1, movieId);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            logger.error("Error while erasing movie ID: {}", movieId, e);
        }

        return false;
    }

    @Override
    public List<Movie> getAllMovies() {
        List<Movie> movies = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_ALL_MOVIES);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Movie movie = new Movie();
                movie.setMovieId(resultSet.getInt("movie_id"));
                movie.setTitle(resultSet.getString("title"));
                movie.setLanguage(resultSet.getString("language"));
                movie.setGenre(resultSet.getString("genre"));
                movie.setDuration(resultSet.getInt("duration"));
                Date releaseDate = resultSet.getDate("release_date");
                if (releaseDate != null) {
                    movie.setReleaseDate(releaseDate.toLocalDate());
                }
                movies.add(movie);
            }

        } catch (SQLException e) {
            logger.error("Error while fetching all movies", e);
        }

        return movies;
    }
}