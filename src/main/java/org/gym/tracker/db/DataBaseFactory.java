package org.gym.tracker.db;

import org.gym.tracker.config.GymAppConfig;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.NoSuchElementException;

public class DataBaseFactory {

    /**
     * Decides what db to instantiate based on config arg
     */
    public static IDataBase getDataBase(String dbType, String url, GymAppConfig dbConfig) throws SQLException, FileNotFoundException {
        switch (dbType) {
            case "sqlite":
                return new sqliteDb(url, dbConfig);
            default:
                throw new NoSuchElementException(String.format("Could not find matching db type to %s", dbType));
        }
    }
}
