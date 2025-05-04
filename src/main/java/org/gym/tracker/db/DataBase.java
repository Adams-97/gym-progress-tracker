package org.gym.tracker.db;

import javax.annotation.Nonnull;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public abstract class DataBase {
    public String url;

    public Map<String, String> dbConf;

    @Nonnull
    public static DataBase InstantiateDatabase(String dbType) {
        return null;
    }

    /**
     * Retrieves connection to then be used elsewhere
     */
    public abstract Connection getConn(String url) throws SQLException;

    /**
     * Insert gym records that have been parsed from Excel. Returns number of inserted rows
     */
    abstract int insertGymRecords(List<GymRecord> rows, String tableName) throws SQLException;

}
