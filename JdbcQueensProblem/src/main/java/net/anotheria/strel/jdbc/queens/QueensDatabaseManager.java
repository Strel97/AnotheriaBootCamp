package net.anotheria.strel.jdbc.queens;

import net.anotheria.strel.basic.queenproblem.QueensProblemSolution;
import net.anotheria.strel.jdbc.DatabaseConfig;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Used for managing database in order to save or select Queens problem solutions.
 * Database used - PostgreSQL.
 *
 * @author Strel97
 */
public class QueensDatabaseManager {

    private Logger log = Logger.getLogger(QueensDatabaseManager.class.getName());

    private Connection connection;
    private Statement   statement;


    public QueensDatabaseManager() {
        BasicConfigurator.configure();

        try {
            Class.forName("org.postgresql.Driver");
        }
        catch (ClassNotFoundException ex ) {
            log.error("PostgreSQL driver for java not found: " + ex.getMessage());
        }
    }

    /**
     * Tries to connect to PostgreSQL database at given address with given user name and password.
     *
     * @param address   Address of DB server
     * @param dbName    Name of database
     * @param userName  User login
     * @param password  User password
     */
    public void connect(String address, String dbName, String userName, String password) {
        try {
            connection = DriverManager.getConnection(String.format("jdbc:postgresql://%s:5432/%s", address, dbName),
                    userName, password);

            statement = connection.createStatement();
        }
        catch (SQLException ex) {
            log.error(ex.getMessage());
        }

        log.info(String.format("User %s successfully connected to database %s", userName, dbName));
    }

    public void connect(String dbName, String userName, String password) {
        connect("localhost", dbName, userName, password);
    }

    public void connect(DatabaseConfig config) {
        connect(config.getHost(), config.getDbName(), config.getUserName(), config.getPassword());
    }

    /**
     * Determines whether table with given name exists.
     * @param tableName Table to test
     * @return          Table exists in current database or not
     */
    private boolean tableExists(String tableName) {
        try {
            DatabaseMetaData meta = connection.getMetaData();
            ResultSet set = meta.getTables(null, null, tableName.toLowerCase(), null);

            return set.next();
        }
        catch (SQLException ex) {
            log.error("Can't test table for existence: " + ex.getMessage());
            return false;
        }
    }

    /**
     * Creates table in current database with given name.
     * @param tableName Table to create
     */
    private void createTable(String tableName) {
        try {
            statement.execute(String.format(
                    "CREATE TABLE %s " +
                            "(ID             SERIAL  PRIMARY KEY," +
                            " POSITIONS      TEXT    NOT NULL)",
                    tableName
            ));
        } catch (SQLException ex) {
            log.error("Can't create table: " + ex.getMessage());
        }
    }

    /**
     * Inserts new record in database, {@link QueensProblemSolution} in our case.
     * @param tableName Destination table
     * @param solution  Queens problem solution
     */
    private void insertSolution(String tableName, String solution) {
        try {
            statement.executeUpdate(String.format(
                    "INSERT INTO %s (POSITIONS) " +
                            "VALUES ('%s')",
                    tableName, solution
            ));
        } catch (SQLException ex) {
            log.error(String.format("Can't insert solution '%s' in table %s: %s",solution, tableName, ex.getMessage()));
        }
    }

    /**
     * Saves given solutions to current database in table with given name.
     * @param tableName Destination table
     * @param solutions List of {@link QueensProblemSolution}
     */
    public void saveSolutions(String tableName, List<QueensProblemSolution> solutions) {
        if (!tableExists(tableName))
            createTable(tableName);

        for (QueensProblemSolution solution : solutions) {
            insertSolution(tableName, solution.toString());
        }
    }

    /**
     * Returns list of {@link QueensProblemSolution} from current database from
     * table with given name.
     *
     * @param tableName Source table
     * @return          List of solutions
     */
    public List<QueensProblemSolution> getSolutions(String tableName) {
        if (tableExists(tableName)) {
            try {
                List<QueensProblemSolution> solutions = new ArrayList<QueensProblemSolution>();
                ResultSet resultSet = statement.executeQuery(String.format("SELECT DISTINCT ON(positions) * FROM %s;", tableName));
                while (resultSet.next()) {
                    solutions.add(new QueensProblemSolution(resultSet.getString("positions")));
                }

                return solutions;
            } catch (SQLException ex) {
                log.error(String.format("Can't select solutions from %s table: %s", tableName, ex.getMessage()));
            }
        }

        return null;
    }

    /**
     * Closes all database related resources and references.
     */
    public void closeManager() {
        try {
            statement.close();
            connection.close();
        }
        catch (SQLException ex) {
            log.error("Can't close database references: " + ex.getMessage());
        }
    }
}
