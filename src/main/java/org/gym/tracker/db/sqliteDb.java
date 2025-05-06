package org.gym.tracker.db;

import org.gym.tracker.config.GymAppConfig;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.gym.tracker.config.ConfigKeys.SETUP_SQL_SCRIPT;

public class sqliteDb implements IDataBase {

    private final String url;

    public sqliteDb(String url, GymAppConfig dbConfig) throws SQLException, FileNotFoundException {
        this.url = url;
        String filePath = url.split(":")[2];

        if (!new File(filePath).exists()) {
            initialiseDb((String) dbConfig.getValue(SETUP_SQL_SCRIPT));
        }
    }

    @Override
    public Connection getConn() throws SQLException {return DriverManager.getConnection(this.url);}
}
