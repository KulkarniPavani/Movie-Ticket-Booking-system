package org.example.dao;

import org.example.model.Theatre;
import org.example.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TheatreDAOImpl implements TheatreDAO {
    @Override
    public boolean addTheatre(Theatre theatre) {
        String sql = "INSERT INTO theatres (name, city, address, total_seats) VALUES (?, ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, theatre.getName());
            statement.setString(2, theatre.getCity());
            statement.setString(3, theatre.getAddress());
            statement.setInt(4, theatre.getTotalSeats());

            int rowsAffected = statement.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();

            return false;
        }
        }

        @Override
        public Theatre findTheatreById ( int theatreId){
            String sql = "SELECT * FROM theatres WHERE theatre_id = ?";

            try (Connection connection = DBConnection.getConnection();
                 PreparedStatement statement = connection.prepareStatement(sql)) {

                statement.setInt(1, theatreId);

                var resultSet = statement.executeQuery();

                if (resultSet.next()) {

                    Theatre theatre = new Theatre();

                    theatre.setTheatreId(resultSet.getInt("theatre_id"));
                    theatre.setName(resultSet.getString("name"));
                    theatre.setCity(resultSet.getString("city"));
                    theatre.setAddress(resultSet.getString("address"));
                    theatre.setTotalSeats(resultSet.getInt("total_seats"));

                    return theatre;
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        public boolean reviseTheatre (Theatre theatre) {
            String sql = "UPDATE theatres SET name = ?, city = ?, address = ?, total_seats = ? WHERE theatre_id = ?";

            try (Connection connection = DBConnection.getConnection();
                 PreparedStatement statement = connection.prepareStatement(sql)) {

                statement.setString(1, theatre.getName());
                statement.setString(2, theatre.getCity());
                statement.setString(3, theatre.getAddress());
                statement.setInt(4, theatre.getTotalSeats());
                statement.setInt(5, theatre.getTheatreId());

                int rowsAffected = statement.executeUpdate();

                return rowsAffected > 0;

            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }
        @Override
        public boolean removeTheatre (int theatreId){
                String sql = "DELETE FROM theatres WHERE theatre_id = ?";

                try (Connection connection = DBConnection.getConnection();
                     PreparedStatement statement = connection.prepareStatement(sql)) {

                    statement.setInt(1, theatreId);

                    int rowsAffected = statement.executeUpdate();

                    return rowsAffected > 0;

                } catch (SQLException e) {
                    e.printStackTrace();
            }
            return false;
        }
    }
