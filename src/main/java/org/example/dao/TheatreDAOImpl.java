package org.example.dao;

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

public class TheatreDAOImpl implements TheatreDAO {

    private static final Logger logger =
            LoggerFactory.getLogger(TheatreDAOImpl.class);

    private static final String ADD_THEATRE =
            "INSERT INTO theatres " +
                    "(name, city, address, total_seats) " +
                    "VALUES (?, ?, ?, ?)";

    private static final String FIND_THEATRE =
            "SELECT * FROM theatres WHERE theatre_id = ?";

    private static final String REVISE_THEATRE =
            "UPDATE theatres SET name = ?, city = ?, " +
                    "address = ?, total_seats = ? " +
                    "WHERE theatre_id = ?";

    private static final String DELETE_THEATRE =
            "DELETE FROM theatres WHERE theatre_id = ?";

    private static final String GET_ALL_THEATRES =
            "SELECT * FROM theatres";

    @Override
    public boolean addTheatre(Theatre theatre) {

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(ADD_THEATRE)) {

            statement.setString(1, theatre.getName());
            statement.setString(2, theatre.getCity());
            statement.setString(3, theatre.getAddress());
            statement.setInt(4, theatre.getTotalSeats());

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                logger.info("Theatre added successfully");
                return true;
            }

        } catch (SQLException e) {
            logger.error("Error while adding theatre", e);
        }

        return false;
    }

    @Override
    public Theatre findTheatreById(int theatreId) {

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(FIND_THEATRE)) {

            statement.setInt(1, theatreId);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {

                Theatre theatre = new Theatre();

                theatre.setTheatreId(
                        resultSet.getInt("theatre_id")
                );

                theatre.setName(
                        resultSet.getString("name")
                );

                theatre.setCity(
                        resultSet.getString("city")
                );

                theatre.setAddress(
                        resultSet.getString("address")
                );

                theatre.setTotalSeats(
                        resultSet.getInt("total_seats")
                );

                logger.info(
                        "Theatre found: theatreId={}",
                        theatreId
                );

                return theatre;
            }

        } catch (SQLException e) {
            logger.error("Error while finding theatre", e);
        }

        return null;
    }

    @Override
    public boolean reviseTheatre(Theatre theatre) {

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(REVISE_THEATRE)) {

            statement.setString(1, theatre.getName());
            statement.setString(2, theatre.getCity());
            statement.setString(3, theatre.getAddress());
            statement.setInt(4, theatre.getTotalSeats());
            statement.setInt(5, theatre.getTheatreId());

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                logger.info(
                        "Theatre updated successfully: theatreId={}",
                        theatre.getTheatreId()
                );
                return true;
            }

        } catch (SQLException e) {
            logger.error("Error while updating theatre", e);
        }

        return false;
    }

    @Override
    public boolean removeTheatre(int theatreId) {

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(DELETE_THEATRE)) {

            statement.setInt(1, theatreId);

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                logger.info(
                        "Theatre deleted successfully: theatreId={}",
                        theatreId
                );
                return true;
            }

        } catch (SQLException e) {
            logger.error(
                    "Error while deleting theatre",
                    e
            );
        }

        return false;
    }

    @Override
    public List<Theatre> viewAllTheatres() {
        List<Theatre> theatres = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_ALL_THEATRES);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Theatre theatre = new Theatre();
                theatre.setTheatreId(resultSet.getInt("theatre_id"));
                theatre.setName(resultSet.getString("name"));
                theatre.setCity(resultSet.getString("city"));
                theatre.setAddress(resultSet.getString("address"));
                theatre.setTotalSeats(resultSet.getInt("total_seats"));
                theatres.add(theatre);
            }

            logger.info("Fetched {} theatres", theatres.size());

        } catch (SQLException e) {
            logger.error("Error while fetching all theatres", e);
        }

        return theatres;
    }
}