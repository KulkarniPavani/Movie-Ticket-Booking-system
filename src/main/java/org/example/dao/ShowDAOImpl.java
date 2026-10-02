package org.example.dao;

import org.example.model.Movie;
import org.example.model.Show;
import org.example.model.Theatre;
import org.example.util.DBConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ShowDAOImpl implements ShowDAO {

    private static final Logger logger =
            LoggerFactory.getLogger(ShowDAOImpl.class);

    private static final String INSERT_SHOW =
            "INSERT INTO shows " +
                    "(theatre_id, movie_id, show_date, start_time, end_time) " +
                    "VALUES (?, ?, ?, ?, ?)";

    private static final String FETCH_SHOW =
            "SELECT * FROM shows WHERE show_id = ?";

    private static final String ADJUST_SHOW =
            "UPDATE shows SET theatre_id = ?, movie_id = ?, " +
                    "show_date = ?, start_time = ?, end_time = ? " +
                    "WHERE show_id = ?";

    private static final String REMOVE_SHOW =
            "DELETE FROM shows WHERE show_id = ?";

    private static final String GET_ALL_SHOWS =
            "SELECT * FROM shows";

    @Override
    public boolean insertShow(Show show) {

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(INSERT_SHOW)) {

            statement.setInt(
                    1,
                    show.getTheatre().getTheatreId()
            );

            statement.setInt(
                    2,
                    show.getMovie().getMovieId()
            );

            statement.setDate(
                    3,
                    java.sql.Date.valueOf(show.getShowDate())
            );

            statement.setTime(
                    4,
                    java.sql.Time.valueOf(show.getStartTime())
            );

            statement.setTime(
                    5,
                    java.sql.Time.valueOf(show.getEndTime())
            );

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                logger.info("Show added successfully");
                return true;
            }

        } catch (SQLException e) {
            logger.error("Error while adding show", e);
        }

        return false;
    }

    @Override
    public Show fetchShow(int showId) {

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(FETCH_SHOW)) {

            statement.setInt(1, showId);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {

                Show show = new Show();

                show.setShowId(
                        resultSet.getInt("show_id")
                );

                Theatre theatre = new Theatre();

                theatre.setTheatreId(
                        resultSet.getInt("theatre_id")
                );

                show.setTheatre(theatre);

                Movie movie = new Movie();

                movie.setMovieId(
                        resultSet.getInt("movie_id")
                );

                show.setMovie(movie);

                show.setShowDate(
                        resultSet.getDate("show_date")
                                .toLocalDate()
                );

                show.setStartTime(
                        resultSet.getTime("start_time")
                                .toLocalTime()
                );

                show.setEndTime(
                        resultSet.getTime("end_time")
                                .toLocalTime()
                );

                logger.info(
                        "Show found: showId={}",
                        showId
                );

                return show;
            }

        } catch (SQLException e) {
            logger.error(
                    "Error while finding show",
                    e
            );
        }

        return null;
    }

    @Override
    public boolean adjustShow(Show show) {

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(ADJUST_SHOW)) {

            statement.setInt(
                    1,
                    show.getTheatre().getTheatreId()
            );

            statement.setInt(
                    2,
                    show.getMovie().getMovieId()
            );

            statement.setDate(
                    3,
                    java.sql.Date.valueOf(show.getShowDate())
            );

            statement.setTime(
                    4,
                    java.sql.Time.valueOf(show.getStartTime())
            );

            statement.setTime(
                    5,
                    java.sql.Time.valueOf(show.getEndTime())
            );

            statement.setInt(
                    6,
                    show.getShowId()
            );

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {

                logger.info(
                        "Show updated successfully: showId={}",
                        show.getShowId()
                );

                return true;
            }

        } catch (SQLException e) {
            logger.error(
                    "Error while updating show",
                    e
            );
        }

        return false;
    }

    @Override
    public boolean removeShow(int showId) {

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(REMOVE_SHOW)) {

            statement.setInt(1, showId);

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {

                logger.info(
                        "Show deleted successfully: showId={}",
                        showId
                );

                return true;
            }

        } catch (SQLException e) {
            logger.error(
                    "Error while deleting show",
                    e
            );
        }

        return false;
    }

    @Override
    public List<Show> getAllShows() {

        List<Show> shows = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(GET_ALL_SHOWS)) {

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                Show show = new Show();

                show.setShowId(
                        resultSet.getInt("show_id")
                );

                Theatre theatre = new Theatre();

                theatre.setTheatreId(
                        resultSet.getInt("theatre_id")
                );

                show.setTheatre(theatre);

                Movie movie = new Movie();

                movie.setMovieId(
                        resultSet.getInt("movie_id")
                );

                show.setMovie(movie);

                show.setShowDate(
                        resultSet.getDate("show_date")
                                .toLocalDate()
                );

                show.setStartTime(
                        resultSet.getTime("start_time")
                                .toLocalTime()
                );

                show.setEndTime(
                        resultSet.getTime("end_time")
                                .toLocalTime()
                );

                shows.add(show);
            }

            logger.info("All shows fetched successfully");

        } catch (SQLException e) {
            logger.error("Error while fetching all shows", e);
        }

        return shows;
    }
}