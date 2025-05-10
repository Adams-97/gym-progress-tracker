package org.gym.tracker.db;

import org.gym.tracker.config.GymAppConfig;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.NoSuchElementException;

import static org.gym.tracker.config.ConfigKeys.*;

public class DataBaseFactory {

    /**
     * Decides what db to instantiate based on config arg
     */
    public static IDataBase getDataBase(GymAppConfig dbConfig) {
        String dbType = String.valueOf(dbConfig.getValue(DB_TYPE));
        String url = String.valueOf(dbConfig.getValue(DB_URL));
        Path setupSqlScript = Paths.get(String.valueOf(dbConfig.getValue(SETUP_SQL_SCRIPT)));

        switch (dbType) {
            case "sqlite":
                sqliteDb db = new sqliteDb(url);
                db.createIfNotExists(setupSqlScript);
                return db;
            default:
                throw new NoSuchElementException(String.format("Could not find matching db type to %s", dbType));
        }
    }
}
