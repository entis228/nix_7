package com.entis.dao;

import com.entis.entity.Location;
import com.entis.entity.Problem;
import com.entis.entity.Route;
import com.entis.entity.Solution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ConnectionDao implements Closeable {

    private List<Location> locations;
    private List<Problem> problems;
    private List<Route> routes;
    private final Connection connection;

    private static final Logger log = LoggerFactory.getLogger(ConnectionDao.class);

    private static Properties loadProperties() {

        Properties props = new Properties();

        try (InputStream input = ConnectionDao.class.getResourceAsStream("/jdbc.properties")) {
            props.load(input);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }

        return props;
    }

    public ConnectionDao() {
        Properties props = loadProperties();

        String url = props.getProperty("url");
        log.info("Connecting to {}", url);
        try {
            connection = DriverManager.getConnection(url, props);
            updateAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateAll() {
        if (connection != null) {
            locations = new ArrayList<>();
            problems = new ArrayList<>();
            routes = new ArrayList<>();
            try {
                connection.setAutoCommit(false);
                try (Statement getLocations = connection.createStatement()) {
                    ResultSet resultSet = getLocations.executeQuery("SELECT * from locations");
                    while (resultSet.next()) {
                        int id = resultSet.getInt(1);
                        String name = resultSet.getString(2);
                        locations.add(new Location(id, name));
                    }
                }
                try (Statement getRoutes = connection.createStatement()) {
                    ResultSet resultSet = getRoutes.executeQuery("SELECT * from routes");
                    while (resultSet.next()) {
                        int id = resultSet.getInt(1);
                        int from_id = resultSet.getInt(2);
                        int to_id = resultSet.getInt(3);
                        int cost = resultSet.getInt(4);
                        routes.add(new Route(id, from_id, to_id, cost));
                    }
                }
                try (Statement getProblems = connection.createStatement()) {
                    ResultSet resultSet = getProblems.executeQuery("SELECT * from problems");
                    while (resultSet.next()) {
                        int id = resultSet.getInt(1);
                        int from_id = resultSet.getInt(2);
                        int to_id = resultSet.getInt(3);
                        problems.add(new Problem(id, from_id, to_id));
                    }
                }
                log.info("Successful got items");
                connection.commit();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void writeSolution(List<Solution> solutions) {
        try {
            connection.setAutoCommit(false);
            try (Statement clearSolutions = connection.createStatement()) {
                clearSolutions.executeUpdate("DELETE FROM solutions");
            }
            try (PreparedStatement insertSolutions = connection.prepareStatement(
                    "INSERT INTO solutions (problem_id, cost) VALUES (?, ?)"
            )) {
                for (Solution s : solutions) {
                    insertSolutions.setInt(1, s.problem_id());
                    insertSolutions.setInt(2, s.cost());
                    insertSolutions.addBatch();
                }
                insertSolutions.executeBatch();
                ResultSet generatedKeys = insertSolutions.getGeneratedKeys();

                while (generatedKeys.next()) {
                    log.info("inserted new solution. ID : {}", generatedKeys.getLong("problem_id"));
                }
                connection.commit();
            }
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);
        }
    }

    public List<Location> getLocations() {
        return locations;
    }

    public List<Problem> getProblems() {
        return problems;
    }

    public List<Route> getRoutes() {
        return routes;
    }
}
