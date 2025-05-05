package org.gym.tracker;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.sql.SQLException;

public class GymApp {
    private static final Logger logger = LogManager.getLogger(GymApp.class);

    /**
     * Main entrypoint for application. Action application takes depends on first argument
     * @param args - Should be one of "read-excel", "calc-exercises", "export"
     */
    public static void main(String[] args) throws IOException, SQLException {
        final String READ_EXCEL = "read-excel";
        final String CALC_EXERCISES = "calc-exercises";
        final String EXPORT = "export";

        switch (args[0]) {
            case READ_EXCEL: Jobs.readExcel();
            case CALC_EXERCISES: Jobs.calcExercises();
            case EXPORT: Jobs.exportData();
            default: logger.warn("No valid argument provided. Must be one of {}, {}, {}",
                    READ_EXCEL, CALC_EXERCISES, EXPORT);
        }
    }
}