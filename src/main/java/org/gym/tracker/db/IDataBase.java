package org.gym.tracker.db;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.gym.tracker.GymRecord;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public interface IDataBase {

     Logger logger = LogManager.getLogger(IDataBase.class);

    /**
     * Retrieves connection to then be used elsewhere
     */
    Connection getConn() throws SQLException;

    /**
     * Insert gym records that have been parsed from Excel. Returns number of inserted rows
     */
    default void insertGymRecords(List<GymRecord> rows, String insertQuery) throws SQLException {
        try(Connection conn = getConn()) {
            conn.setAutoCommit(false);

            PreparedStatement stmnt = conn.prepareStatement(insertQuery);
            for (GymRecord record : rows) {
                stmnt.setString(1, record.exercise());
                stmnt.setInt(2, record.setNumber());
                stmnt.setInt(3, record.numReps());
                stmnt.setDate(4, Date.valueOf(record.date()));
                stmnt.setTime(5, Time.valueOf(record.time()));
                stmnt.setInt(5, record.mesocycle());
                stmnt.setInt(5, record.programWeekNo());
                stmnt.executeUpdate();
            }
            conn.commit();
        } catch (SQLException e) {
            logger.error("Failed in insertion of gym records. Rolling back transaction");
            throw e;
        }
    }

     default void initialiseDb(String sqlFile) throws SQLException, FileNotFoundException {
        List<String> sqlQueries = readSqlScript(sqlFile);

        // getConn will initialise the sqlite db file
        try(Connection conn = getConn()) {
            conn.setAutoCommit(false);

            for (String sqlQuery : sqlQueries) {
                Statement stmnt = conn.createStatement();
                stmnt.executeUpdate(sqlQuery);
            }
        }
    }

    private List<String> readSqlScript(String filePath) throws FileNotFoundException {
        List<String> sqlQueries = new ArrayList<>();

        try(Scanner sqlFile = new Scanner(new FileInputStream(filePath))) {
            sqlFile.useDelimiter(";");
            while (sqlFile.hasNext()) {
                sqlQueries.add(sqlFile.next());
            }
            return sqlQueries;

        } catch (FileNotFoundException e) {
            logger.error("Cannot find setup sql script: {}", filePath);
            throw e;
        }
    }
}
