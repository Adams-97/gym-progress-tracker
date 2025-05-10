package org.gym.tracker.db;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.gym.tracker.GymRecord;

import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.gym.tracker.GymRecord.getGymRecordFromDBResult;
import static org.gym.tracker.db.sqlStatements.getGymRecordSelectAll;
import static org.gym.tracker.db.sqlStatements.getGymRecordSelectAllNonIndex;

public interface IDataBase {

     Logger logger = LogManager.getLogger(IDataBase.class);

    /**
     * Retrieves connection to then be used elsewhere
     */
    Connection getConn();

    /**
     * Mutate a given PreparedStatement with values from a GymRecord
     */
    void parseGymRecordToStatement(GymRecord row, PreparedStatement stmnt) throws SQLException;

    /**
     * Insert gym records that have been parsed from Excel. Returns number of inserted rows
     */
    default void insertGymRecords(List<GymRecord> rows, String insertQuery) {
        try(Connection conn = getConn(); PreparedStatement stmnt = conn.prepareStatement(insertQuery)) {
            conn.setAutoCommit(false);

            for (GymRecord record : rows) {
                parseGymRecordToStatement(record, stmnt);   // Amends state of stmnt
                stmnt.executeUpdate();
            }
            conn.commit();
        } catch (SQLException e) {
            logger.error("Failed in insertion of gym records. Rolling back transaction batch");
            throw new RuntimeException(e);
        }
    }

    /**
     * Retrieves all GymRecords rows from db
     */
    default List<GymRecord> extractAllGymRecords(String tableName) {
        List<GymRecord> gymRecords = new ArrayList<>();

        try(Connection conn = getConn()) {
            Statement stmnt = conn.createStatement();
            ResultSet rs = stmnt.executeQuery(getGymRecordSelectAllNonIndex(tableName));

            while (rs.next()) {
                gymRecords.add(getGymRecordFromDBResult(rs));
            }
            return gymRecords;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Takes a path with initial DDL needed for setting up Database
     */
     default void initialiseDb(Path setupDdlSqlFile) {
        List<String> sqlQueries = readSqlScript(setupDdlSqlFile);

        try(Connection conn = getConn()) {
            conn.setAutoCommit(false);

            for (String sqlQuery : sqlQueries) {
                Statement stmnt = conn.createStatement();
                stmnt.execute(sqlQuery);
            }
            conn.commit();
        } catch (SQLException e) {
            logger.error("Read sql script but couldn't execute DDL on startup");
            throw new RuntimeException(e);
         }
    }

    /**
     * Reads a sql file and parses out the queries
     */
    default List<String> readSqlScript(Path filePath) {
        List<String> sqlQueries = new ArrayList<>();

        try (Scanner sqlFile = new Scanner(filePath)) {
            sqlFile.useDelimiter(";");
            while (sqlFile.hasNext()) {
                sqlQueries.add(sqlFile.next());
            }
            return sqlQueries.stream().map(q -> q + ";").toList();

        } catch (NoSuchFileException e) {
            logger.error("Cannot find setup sql script: {}", filePath);
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
