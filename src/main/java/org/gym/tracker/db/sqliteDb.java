package org.gym.tracker.db;

import org.gym.tracker.GymRecord;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;

public class sqliteDb implements IDataBase {

    private final String url;
    public final Path filePath;

    /**
     * sqlite is an actual file on filesystem so path retrieved from url
     */
    public sqliteDb(String url) {
        this.url = url;
        this.filePath = Paths.get(url.split(":")[2]);
    }

    void createIfNotExists(Path setupSqlScript) {
        if (!Files.exists(filePath)) {
            initialiseDb(setupSqlScript);
        }
    }

    @Override
    public Connection getConn() {
        try {
            return DriverManager.getConnection(this.url);
        } catch (SQLException e) {
            logger.error("Failed getting JDBC connection");
            throw new RuntimeException(e);
        }
    }

    /**
     * sqlite has no date or time datatype so dates and times need to be passed as string
     */
    public void parseGymRecordToStatement(GymRecord row, PreparedStatement stmnt) throws SQLException {
        stmnt.setString(1, row.exercise());
        stmnt.setInt(2, row.setNumber());
        stmnt.setInt(3, row.numReps());
        stmnt.setString(4, row.date().toString());  // Default is YYYY-MM-DD
        stmnt.setString(5, row.time().toString());  // Default is HH:MM:SS
        stmnt.setInt(6, row.mesocycle());
        stmnt.setInt(7, row.programWeekNo());
    }
}
