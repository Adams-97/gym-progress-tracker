package org.gym.tracker.db;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.List;

public interface IDataBase {

     Logger logger = LogManager.getLogger(IDataBase.class);

    /**
     * Retrieves connection to then be used elsewhere
     */
    Connection getConn() throws SQLException;

    /**
     * Insert gym records that have been parsed from Excel. Returns number of inserted rows
     */
    default int insertGymRecords(List<GymRecord> rows, String tableName) throws SQLException {
        int numInserts = 0;

        try(Connection conn = getConn()) {
            conn.setAutoCommit(false);

            PreparedStatement stmnt = conn.prepareStatement(sqlStatements.gymRecordInsertStmnt);
            for (GymRecord record : rows) {
                stmnt.setString(1, record.exercise());
                stmnt.setInt(2, record.setNumber());
                stmnt.setInt(3, record.numReps());
                stmnt.setDate(4, Date.valueOf(record.date()));
                stmnt.setTime(5, Time.valueOf(record.time()));
                stmnt.setInt(5, record.mesocycle());
                stmnt.setInt(5, record.programWeekNo());
                numInserts += stmnt.executeUpdate();
            }
            conn.commit();
            return numInserts;
        } catch (SQLException e) {
            logger.error("Failed in insertion of gym records. Rolling back transaction");
            throw e;
        }
    }
}
