package org.gym.tracker.db;

import java.util.NoSuchElementException;

public class DataBaseFactory {

    public static IDataBase getDataBase(String dbType, String url) {
        switch (dbType) {
            case "sqlite":
                return new sqliteDb(url);
            default:
                throw new NoSuchElementException(String.format("Could not find matching db type to %s", dbType));
        }
    }
}
