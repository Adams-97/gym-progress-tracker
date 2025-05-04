package org.gym.tracker.db;

import javax.annotation.Nonnull;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class sqlliteDb extends DataBase {

    public sqlliteDb(String url) {

    }
    public static void main(String[] args) {
        String url = "jdbc:sqlite:my.db";

        try (var conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                var meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created.");
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Nonnull
    @Override
    public Connection getConn(String url) throws SQLException {
        return null;
    }

    @Override
    public int insertGymRecords(List<GymRecord> rows, String tableName) throws SQLException {
        return 0;
    }
}
